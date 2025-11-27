package graphics.tools;

import graphics.*;
import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;
import lombok.Getter;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

/**
 * Created by Matija on 14 Jun 17.
 */
public abstract class Tool {

    @Getter
    private final DrawingService service;
    protected Stack<Shape> deleteOnUndo = new Stack<>();

    public Tool(DrawingService service){
        this.service = service;
    }

    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mouseDrag(MouseEvent e);

    public void mouseMove(MouseEvent e){ // Kada se pomeri mis
        Point currPoint = e.getPoint();
        MainForm.rightLabel.setText("X:" + currPoint.getX() + " Y:" + currPoint.getY());
    }

    public abstract String getName();
}
