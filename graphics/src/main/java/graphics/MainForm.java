package graphics;

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
public class MainForm extends JFrame {

    // Toolbar elements
    private final JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private final FileService fileService = new FileService();
    public ButtonGroup toolsGroup = new ButtonGroup();
    //public static final String[] toolNames = new String[]{ "Move", "Erase", "Line", "Lines", "Closed Lines", "Rectangle"};
    public static final String[] toolNames = new String[]{ "Move", "Erase", "Line", "Rectangle"};
    public static String currentToolName = toolNames[0];
    public static final JComboBox<Integer> lineThick = new JComboBox<>(new Integer[]{1,2,3,4,5});
    public static ColorChooserButton lineColor = new ColorChooserButton(Color.BLACK);

    // Canvas elementi
    private static final WorkPanel workPanel = new WorkPanel();
    // List of tools
    public static final HashMap<String, Tool> toolsList = new HashMap<>();
    static{
        // Unosimo u hesh mapu
        toolsList.put(toolNames[0], new MoveTool());
        toolsList.put(toolNames[1], new EraseTool());
        toolsList.put(toolNames[2], new DrawLineTool());
        //toolsList.put(toolNames[3], new DrawLinesTool());
        //toolsList.put(toolNames[4], new DrawClosedLinesTool());
        toolsList.put(toolNames[3], new DrawRectangleTool());

        // Ako se promeni
        lineThick.addActionListener((e) -> updateStatus());
    }

    // Status bar
    private final JPanel statusbar = new JPanel(new GridLayout(1,2));
    public static JLabel leftLabel, rightLabel;

    private final PopupDialog aboutDialog = new PopupDialog(this, "About", "© Матија Лукић 2017 ЕТФ Београд");

    public MainForm(){
        super("Vektorska grafika");

        // Osnovna podesavanja
        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fill menu
        makeMenu();
        // Fill toolbar
        makeTools();
        // Fill canvas
        makeCanvas();
        //  Fill status bar
        makeStatus();

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
            WorkPanel.drawing.deleteAll();
            workPanel.clear();
            workPanel.repaint();
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
                    fileService.write(fileTOSave,   WorkPanel.drawing);
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
                    fileService.read(fileToLoad);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                workPanel.repaint();
            }
        });
        file.add(load);
        file.addSeparator();

        // Close this
        JMenuItem closeThis = new JMenuItem("Close This");
        closeThis.addActionListener(e -> {
            WorkPanel.drawing.deleteAll();
            workPanel.clear();
            workPanel.repaint();
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
            WorkPanel.selectedTool.undo();
            workPanel.repaint();
        });
        redo.addActionListener(e -> {
            WorkPanel.selectedTool.redo();
            workPanel.repaint();
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
    private void makeTools(){
        toolbar.setBackground(Color.WHITE);
        toolbar.add(new JLabel("Tools:"));

        // Za svako ime alata dodajemo button
        for(String toolName : toolNames){
            JButton newBtn = new JButton(toolName);
            toolsGroup.add(newBtn);
            toolbar.add(newBtn);

            // Dodavanje eventa
            newBtn.addActionListener((e) -> {
                WorkPanel.selectedTool = toolsList.get(toolName);
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
        leftLabel = new JLabel(toolNames[0] + " selected", JLabel.LEFT);
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

    public static void updateStatus(){
        Color currColor = lineColor.getSelectedColor();
        leftLabel.setText(currentToolName + " selected, color: rgb(" + currColor.getRed() + ", " + currColor.getGreen() + ", " + currColor.getBlue() + "), thickness: " +  lineThick.getSelectedItem());
    }
}
