package graphics.tools;

import graphics.*;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;
import lombok.Getter;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import java.util.function.Supplier;

/**
 * Created by Matija on 14 Jun 17.
 */
public abstract class Tool {

    private Supplier<Drawing> supplier ;
    protected Stack<Shape> deleteOnUndo = new Stack<>();

    // Konstruktor za singleton
    public Tool(Supplier<Drawing> supplier){
        this.supplier = supplier;
    }

    public Drawing getDrawing() {
        return supplier.get();
    }

    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mouseDrag(MouseEvent e);

    public void mouseMove(MouseEvent e){ // Kada se pomeri mis
        Point currPoint = e.getPoint();
        MainForm.rightLabel.setText("X:" + currPoint.getX() + " Y:" + currPoint.getY());
    }
}
