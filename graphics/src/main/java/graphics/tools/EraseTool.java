package graphics.tools;

import graphics.*;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.function.Supplier;

/**
 * Created by Matija on 16 Jun 17.
 */
public class EraseTool extends Tool {


    private final Stack<Shape> deletedFigures = new Stack<>();
    private final Stack<Shape> undeletedFigures = new Stack<>();

    public EraseTool(Supplier<Drawing> supplier) {
        super(supplier);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        Point currPoint = e.getPoint();

        Shape toDelete = getDrawing().select(currPoint);
        if(toDelete != null){
            deletedFigures.push(toDelete);
            getDrawing().delete(toDelete);
        }
    }

    @Override
    public void mouseDrag(MouseEvent e) {}

    @Override
    public void undo() {
        if(!deletedFigures.empty()){
            Shape popFigura = deletedFigures.pop();
            undeletedFigures.push(popFigura);
            getDrawing().add(popFigura);
        }
    }

    @Override
    public void redo() {
        if(!undeletedFigures.empty()) {
            Shape deleteAgain = undeletedFigures.pop();
            deletedFigures.push(deleteAgain);
            getDrawing().delete(deleteAgain);
        }
    }
}
