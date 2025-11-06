package graphics;

import graphics.crud.DrawingService;
import graphics.listener.DrawingChangedListener;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;
import graphics.tools.*;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WorkPanel extends JPanel implements DrawingChangedListener {
    @Getter
    private DrawingService controller;// Crtez
    @Getter
    private Tool selectedTool;


    public WorkPanel(DrawingService drawingService){

        this.controller = drawingService;
        controller.addListener(this);
        this.selectedTool = new MoveTool(controller);

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
            }

            @Override
            public void mouseClicked(MouseEvent e){
                super.mouseClicked(e);
                System.out.println("Clicked mouse");
                selectedTool.mouseClicked(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                selectedTool.mouseMove(e);
            }
            @Override
            public void mouseDragged(MouseEvent e){
                super.mouseDragged(e);
                selectedTool.mouseDrag(e);
            }
        });
    }

    public void openNewDrawing(Drawing newdrawing){
        controller.repaintDrawing(newdrawing);
    }

    public void changeTool(Tool noviAlat){
        selectedTool = noviAlat;
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(), getHeight()); // clear canvas

        if(controller.getDrawing() != null) {
            Graphics2D graph =  (Graphics2D)g;
            for(Shape toPaint : controller.getDrawing()){
                toPaint.paint(graph);
            }
        }
    }

//    public void clear(){
//        Graphics g = getGraphics();
//
//        g.clearRect(0,0, getWidth(), getHeight());
//    }

    @Override
    public void drawingChanged() {
        repaint();
    }
}
