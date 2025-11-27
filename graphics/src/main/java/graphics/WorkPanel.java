package graphics;

import graphics.controller.ToolController;
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
    private DrawingService drawingService;// Crtez
    private final ToolController toolController;


    public WorkPanel(DrawingService drawingService, ToolController toolController){

        this.drawingService = drawingService;
        this.drawingService.addListener(this);
        this.toolController = toolController;

        addMouseListener(this.toolController);
        addMouseMotionListener(this.toolController);
    }

    public void openNewDrawing(Drawing newdrawing){
        drawingService.repaintDrawing(newdrawing);
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(), getHeight()); // clear canvas

        if(drawingService.getDrawing() != null) {
            Graphics2D graph =  (Graphics2D)g;
            for(Shape toPaint : drawingService.getDrawing()){
                toPaint.paint(graph);
            }
        }
    }

    @Override
    public void drawingChanged() {
        repaint();
    }
}
