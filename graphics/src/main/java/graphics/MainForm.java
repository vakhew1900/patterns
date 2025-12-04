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

    private final JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final FileService fileService = new FileService();
    public ButtonGroup toolsGroup = new ButtonGroup();

    private final LineThickButton lineThick = new LineThickButton(new Integer[]{1,2,3,4,5});
    private final ColorChooserButton lineColor = new ColorChooserButton(Color.BLACK);

    private final WorkPanel workPanel;
    private final ToolController toolController;

    private final JPanel statusbar = new JPanel(new GridLayout(1,2));
    private JLabel leftLabel;
    private final JLabel rightLabel =  new JLabel("", JLabel.RIGHT);;

    private final PopupDialog aboutDialog = new PopupDialog(this, "About", "© Матија Лукић 2017 ЕТФ Београд");
    private String currentToolName;

    public MainForm(){
        super("Векторная графика");
        Drawing drawing = new Drawing();
        DrawingService service = new DrawingService(drawing);
        lineColor.addColorChangedListener(this);
        DrawLineTool drawLineTool = new DrawLineTool(service, rightLabel);
        DrawRectangleTool drawRectangleTool = new DrawRectangleTool(service, rightLabel);
        EraseTool eraseTool = new EraseTool(service, rightLabel);
        MoveTool moveTool = new MoveTool(service, rightLabel);

        toolController = new ToolController(service);
        toolController.registerTool(drawLineTool);
        toolController.registerTool(eraseTool);
        toolController.registerTool(moveTool);
        toolController.registerTool(drawRectangleTool);

        currentToolName = DrawLineTool.NAME;
        toolController.setCurrentTool(currentToolName);

        this.workPanel = new WorkPanel(service, toolController);

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
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem newFile = new JMenuItem("New Drawing");
        newFile.addActionListener(e -> {
            workPanel.getDrawingService().deleteAll();
        });
        file.add(newFile);
        file.addSeparator();

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener((e) -> {
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

        JMenuItem closeThis = new JMenuItem("Close This");
        closeThis.addActionListener(e -> {
            workPanel.getDrawingService().deleteAll();
        });
        file.add(closeThis);

        // Quit
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener((e)-> dispose());
        file.add(quit);

        JMenu help = new JMenu("Help");

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

    private void makeTools(Set<String> toolNames){
        toolbar.setBackground(Color.WHITE);
        toolbar.add(new JLabel("Tools:"));

        for(String toolName : toolNames){
            JButton newBtn = new JButton(toolName);
            toolsGroup.add(newBtn);
            toolbar.add(newBtn);

            newBtn.addActionListener((e) -> {
                toolController.setCurrentTool(toolName);
                currentToolName = toolName;
                updateStatus();
            });
        }

        toolbar.add(new JLabel("Line thick:"));
        toolbar.add(lineThick);

        toolbar.add(lineColor);
        add(toolbar, BorderLayout.NORTH);
    }

    private void makeStatus(){
        statusbar.setBackground(Color.LIGHT_GRAY);

        leftLabel = new JLabel( currentToolName + " selected", JLabel.LEFT);
        updateStatus();
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
