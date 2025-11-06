package graphics.tools;

import graphics.*;
import graphics.crud.DrawingService;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Supplier;

/**
 * Created by Matija on 15 Jun 17.
 */
public class MoveTool extends Tool {
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
        Shape newFigure = getDao().get(fromPos);

        if (figuraToMove != null && newFigure != null) { // Ako postoje i stara i nova selektovana gasimo selektovanost za staru
            getDao().select(figuraToMove, false); // Ponistavamo selektovani flag
        }

        if (newFigure != null) { // Ako postoji nova figura selektujemo nju
            figuraToMove = newFigure;  figuraToMove.setSelected(true); // selektovan je novi
            getDao().select(figuraToMove, true); // P
        }

        if (figuraToMove != null) {
            figuraToMove.setNewCatch(fromPos);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseDrag(MouseEvent e){
        Point newPoint = e.getPoint();
        System.out.println("Firua koju pomeramo : " + figuraToMove);
        getDao().moveNew(figuraToMove, newPoint);

        MainForm.rightLabel.setText("X:" + newPoint.getX() + " Y:" + newPoint.getY());
    }
}
