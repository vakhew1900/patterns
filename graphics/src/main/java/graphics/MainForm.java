package graphics;

import graphics.command.CommandContainer;
import graphics.controller.ToolController;
import graphics.crud.DrawingService;
import graphics.gui.ColorChooserButton;
import graphics.gui.LineThickButton;
import graphics.listener.ColorChangedListener;
import graphics.model.Drawing;
import graphics.support.io.FileService;
import graphics.tools.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Matija on 10 Jun 17.
 */
public class MainForm extends JFrame implements ColorChangedListener {

    // Toolbar elements
    private final JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final FileService fileService = new FileService();
    public ButtonGroup toolsGroup = new ButtonGroup();
    //public static final String[] toolNames = new String[]{ "Move", "Erase", "Line", "Lines", "Closed Lines", "Rectangle"};


    private final LineThickButton lineThick = new LineThickButton(new Integer[]{1,2,3,4,5});
    private final ColorChooserButton lineColor = new ColorChooserButton(Color.BLACK);

    private final WorkPanel workPanel;
    private final ToolController toolController;

    // Status bar
    private final JPanel statusbar = new JPanel(new GridLayout(1,2));
    public static JLabel leftLabel, rightLabel;

    private final PopupDialog aboutDialog = new PopupDialog(this, "About", "© Матија Лукић 2017 ЕТФ Београд");
    private String currentToolName;

    public MainForm(){
        super("Векторная графика");
        Drawing drawing = new Drawing();
        DrawingService service = new DrawingService(drawing);
        lineColor.addColorChangedListener(this);
        DrawLineTool drawLineTool = new DrawLineTool(service);
        DrawRectangleTool drawRectangleTool = new DrawRectangleTool(service);
        EraseTool eraseTool = new EraseTool(service);
        MoveTool moveTool = new MoveTool(service);

        toolController = new ToolController();
        toolController.registerTool(drawLineTool);
        toolController.registerTool(eraseTool);
        toolController.registerTool(moveTool);
        toolController.registerTool(drawRectangleTool);

        currentToolName = DrawLineTool.NAME;
        toolController.setCurrentTool(currentToolName);

        this.workPanel = new WorkPanel(service, toolController);


        // Osnovna podesavanja
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeMenu();
        makeTools(toolController.getRegisteredTools());
        makeCanvas();
        makeStatus();

        lineColor.addColorChangedListener(drawLineTool);
        lineColor.addColorChangedListener(drawRectangleTool);
        lineColor.setSelectedColor(Color.BLACK);
        lineThick.addLineThickChangedListener(drawLineTool);
        lineThick.addLineThickChangedListener(drawRectangleTool);


        setVisible(true);
    }

    private void makeMenu(){
        // Menu
        JMenuBar menuBar = new JMenuBar();

        // File meni
        JMenu file = new JMenu("File");
        menuBar.add(file);

        // Novi fajl
        JMenuItem newFile = new JMenuItem("New Drawing");
        newFile.addActionListener(e -> {
            workPanel.getDrawingService().deleteAll();
//            workPanel.clear();
//            workPanel.repaint();
        });
        file.add(newFile);
        file.addSeparator();

        // Save - zapamti
        JMenuItem save = new JMenuItem("Save");
        // Zapmti
        save.addActionListener((e) -> {
            // file chooser
            JFileChooser saveFileDialog = new JFileChooser();

            if(saveFileDialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fileTOSave = saveFileDialog.getSelectedFile();

                try {
                    fileService.write(fileTOSave, workPanel.getDrawingService().getDrawing());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        file.add(save);

        // Save as
        JMenuItem load = new JMenuItem("Load");
        load.addActionListener((e)->{
            JFileChooser loadFileDialog = new JFileChooser();

            if(loadFileDialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                File fileToLoad = loadFileDialog.getSelectedFile();

                try {
                   workPanel.openNewDrawing(fileService.read(fileToLoad));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
//                workPanel.repaint();
            }
        });
        file.add(load);
        file.addSeparator();

        // Close this
        JMenuItem closeThis = new JMenuItem("Close This");
        closeThis.addActionListener(e -> {
            workPanel.getDrawingService().deleteAll();
//            workPanel.clear();
//            workPanel.repaint();
        });
        file.add(closeThis);

        // Quit
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener((e)-> dispose());
        file.add(quit);

        // Help menu
        JMenu help = new JMenu("Help");

        // Undo i  redo
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        undo.addActionListener(e -> {
            CommandContainer.getInstance().undo();
        });
        redo.addActionListener(e -> {
            CommandContainer.getInstance().redo();
        });
        help.add(undo);
        help.add(redo);
        help.addSeparator();

        JMenuItem about = new JMenuItem("About");
        about.addActionListener((e)-> aboutDialog.setVisible(true));
        help.add(about);

        menuBar.add(help);
        setJMenuBar(menuBar);
    }

    // Fill the toolbar
    private void makeTools(Set<String> toolNames){
        toolbar.setBackground(Color.WHITE);
        toolbar.add(new JLabel("Tools:"));

        // Za svako ime alata dodajemo button
        for(String toolName : toolNames){
            JButton newBtn = new JButton(toolName);
            toolsGroup.add(newBtn);
            toolbar.add(newBtn);

            // Dodavanje eventa
            newBtn.addActionListener((e) -> {
                toolController.setCurrentTool(toolName);
                currentToolName = toolName;
                updateStatus();
            });
        }

        // Dodavanje dodavanje debljine linije
        toolbar.add(new JLabel("Line thick:"));
        toolbar.add(lineThick);

        toolbar.add(lineColor);
        add(toolbar, BorderLayout.NORTH);
    }

    private void makeStatus(){
        statusbar.setBackground(Color.LIGHT_GRAY);

        // Postavljanje labela
        leftLabel = new JLabel( currentToolName + " selected", JLabel.LEFT);
        updateStatus();
        rightLabel = new JLabel("", JLabel.RIGHT);
        statusbar.add(leftLabel);
        statusbar.add(rightLabel);

        add(statusbar, BorderLayout.SOUTH);
    }

    private void makeCanvas(){
        Drawing novi = new Drawing();

        workPanel.openNewDrawing(novi);
        add(workPanel, BorderLayout.CENTER);
    }

    public void updateStatus(){
        Color currColor = lineColor.getSelectedColor();
        leftLabel.setText(
                currentToolName +
                        " selected, color: rgb(" + currColor.getRed() + ", " + currColor.getGreen() + ", " + currColor.getBlue() + ")" +
                        ", thickness: " +  lineThick.getSelectedItem());
    }

    @Override
    public void colorChanged(Color color) {
        updateStatus();
    }
}
