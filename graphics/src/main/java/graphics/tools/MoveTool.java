package graphics.tools;

import graphics.command.CommandContainer;
import graphics.command.MoveCommand;
import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Matija on 15 Jun 17.
 */
public class MoveTool extends Tool {

    public static final String NAME = "Move";

    private Shape shape;
    private Shape prevShape;

    public MoveTool(DrawingService dao, JLabel logger) {
        super(dao, logger);
    }


    public static class ShapeMoving {
        Shape movedFigure;
        ArrayList<Point> movedPoints;
        ShapeMoving(Shape moved, ArrayList<Point> tacke){
            movedFigure = moved;
            movedPoints = tacke;
        }
    }

    private final Stack<ShapeMoving> moves = new Stack<>();

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e) {
        Point fromPos = e.getPoint();
        Shape newFigure = getService().get(fromPos);

        if (shape != null && newFigure != null) {
            getService().select(shape, false);
        }

        if (newFigure != null) {
            shape = newFigure;
            shape.setSelected(true);
            getService().select(shape, true);
        }

        getService().catchNew(shape, e.getPoint());
        prevShape = shape.clone();
//        CommandContainer.getInstance().executeCommand(new MoveCommand(getService(), shape, e.getPoint()));
    }

    @Override
    public void mouseReleased(MouseEvent e){
        shape.setPoints(prevShape.getPoints());
        CommandContainer.getInstance().executeCommand(new MoveCommand(getService(), prevShape, shape, e.getPoint()));
    }

    @Override
    public void mouseDrag(MouseEvent e){
        getService().moveNew(shape,  e.getPoint());
        logger.setText("X:" + e.getPoint().getX() + " Y:" + e.getPoint().getY());

    }

    @Override
    public String getName() {
        return NAME;
    }
}
