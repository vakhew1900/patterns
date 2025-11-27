package graphics.tools;

import graphics.*;
import graphics.command.CommandContainer;
import graphics.command.MoveCommand;
import graphics.command.SelectMovingShapeCommand;
import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Matija on 15 Jun 17.
 */
public class MoveTool extends Tool {

    public static final String NAME = "Move";

    private Shape figuraToMove;

    public MoveTool(DrawingService dao) {
        super(dao);
    }


    // belezenje pomeraja
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

        if (figuraToMove != null && newFigure != null) { // Ako postoje i stara i nova selektovana gasimo selektovanost za staru
            getService().select(figuraToMove, false); // Ponistavamo selektovani flag
        }

        if (newFigure != null) { // Ako postoji nova figura selektujemo nju
            figuraToMove = newFigure;  figuraToMove.setSelected(true); // selektovan je novi
            getService().select(figuraToMove, true); // P
        }

        CommandContainer
                .getInstance()
                .executeCommand(new SelectMovingShapeCommand(getService(), figuraToMove, fromPos));
    }

    @Override
    public void mouseReleased(MouseEvent e){
        CommandContainer.getInstance()
                .executeCommand(new MoveCommand(getService(), true, figuraToMove, e.getPoint()));
    }

    @Override
    public void mouseDrag(MouseEvent e){

        System.out.println("Firua koju pomeramo : " + figuraToMove);

        CommandContainer.getInstance()
                .executeCommand(new MoveCommand(getService(), false, figuraToMove, e.getPoint()));

        MainForm.rightLabel.setText("X:" + e.getPoint().getX() + " Y:" + e.getPoint().getY());

    }

    @Override
    public String getName() {
        return NAME;
    }
}
