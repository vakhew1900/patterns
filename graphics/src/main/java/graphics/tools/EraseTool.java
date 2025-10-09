package graphics.tools;

import graphics.*;
import graphics.model.shapes.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

/**
 * Created by Matija on 16 Jun 17.
 */
public class EraseTool extends Tool {

    private final Stack<Shape> deletedFigures = new Stack<>();
    private final Stack<Shape> undeletedFigures = new Stack<>();

    public EraseTool(){ super();}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        Point currPoint = e.getPoint();

        Shape toDelete = WorkPanel.drawing.selectFigure(currPoint);
        if(toDelete != null){
            deletedFigures.push(toDelete);
            WorkPanel.drawing.deleteFigura(toDelete);
        }
    }

    @Override
    public void mouseDrag(MouseEvent e) {}

    @Override
    public void undo() {
        if(!deletedFigures.empty()){
            Shape popFigura = deletedFigures.pop();
            undeletedFigures.push(popFigura);
            WorkPanel.drawing.addFigure(popFigura);
        }
    }

    @Override
    public void redo() {
        if(!undeletedFigures.empty()) {
            Shape deleteAgain = undeletedFigures.pop();
            deletedFigures.push(deleteAgain);
            WorkPanel.drawing.deleteFigura(deleteAgain);
        }
    }
}
