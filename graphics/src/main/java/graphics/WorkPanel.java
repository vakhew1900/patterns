package graphics;

import graphics.command.CommandContainer;
import graphics.command.RepaintDrawingCommand;
import graphics.controller.ToolController;
import graphics.crud.DrawingService;
import graphics.listener.DrawingChangedListener;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;


@Getter
public class WorkPanel extends JPanel implements DrawingChangedListener {
    private final DrawingService drawingService;// Crtez

    public WorkPanel(DrawingService drawingService, ToolController toolController){
        this.drawingService = drawingService;
        this.drawingService.addListener(this);

        addMouseListener(toolController);
        addMouseMotionListener(toolController);
    }

    public void openNewDrawing(Drawing newdrawing){
        CommandContainer.getInstance().executeCommand(new RepaintDrawingCommand(drawingService, newdrawing));
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
