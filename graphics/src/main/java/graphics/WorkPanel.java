package graphics;

import graphics.model.Drawing;
import graphics.model.shapes.Shape;
import graphics.tools.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WorkPanel extends JPanel {
    public static Drawing drawing = new Drawing(); // Crtez
    public static Tool selectedTool = new MoveTool();


    public WorkPanel(){

        // Dodavanje eventova
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                selectedTool.mousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                selectedTool.mouseReleased(e);
                repaint();
            }
            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                System.out.println("Clicked mouse");
                selectedTool.mouseClicked(e);
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                // Ispisujemo kordinate
                selectedTool.mouseMove(e);
            }
            @Override
            public void mouseDragged(MouseEvent e){
                super.mouseDragged(e);
                selectedTool.mouseDrag(e);
                repaint();
            }
        });
    }
    public WorkPanel(Drawing drawing){
        WorkPanel.drawing = drawing;
    }

    // Novi crtez
    public void openNewDrawing(Drawing newdrawing){
        drawing = newdrawing;
    }

    // Promena alata
    public void changeTool(Tool noviAlat){
        selectedTool = noviAlat;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(), getHeight()); // clear canvas

        if(drawing != null) {
            Graphics2D graph =  (Graphics2D)g;
            for(Shape toPaint : drawing){
                toPaint.paint(graph);
            }
        }
    }

    public void clear(){
        Graphics g = getGraphics();

        g.clearRect(0,0, getWidth(), getHeight());
    }
}
