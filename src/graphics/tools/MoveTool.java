package graphics.tools;

import graphics.*;
import graphics.shapes.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Matija on 15 Jun 17.
 */
public class MoveTool extends Tool {
    private Shape figuraToMove;


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
    private final Stack<ShapeMoving> undoMoves = new Stack<>();

    private ArrayList<Point> currentPoints;

    private void savePoints(){ // Pamtimo u curr point kloniranu
        if(figuraToMove != null){
            currentPoints = new ArrayList<>();

            for(Point currPoint : figuraToMove.getPoints()){
                currentPoints.add((Point)currPoint.clone());
            }
        }
    }

    public MoveTool(){
    }

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e) {
        Point fromPos = e.getPoint();
        Shape newFigure = WorkPanel.drawing.selectFigure(fromPos);

        if (figuraToMove != null && newFigure != null) { // Ako postoje i stara i nova selektovana gasimo selektovanost za staru
            figuraToMove.setSelected(false); // Ponistavamo selektovani flag
        }

        if (newFigure != null) { // Ako postoji nova figura selektujemo nju
            figuraToMove = newFigure;
            figuraToMove.setSelected(true); // selektovan je novi
        }

        if (figuraToMove != null) { // Ako postoji selektovana figura hvatamo njen catch
            // Pamtimo figuru i stare pozicije na stek
            savePoints();
            moves.push(new ShapeMoving(figuraToMove, currentPoints));

            figuraToMove.setNewCatch(fromPos);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseDrag(MouseEvent e){
        Point newPoint = e.getPoint();
        System.out.println("Firua koju pomeramo : " + figuraToMove);
        if(figuraToMove != null) {
            figuraToMove.moveNew(newPoint);
        }

        MainForm.rightLabel.setText("X:" + newPoint.getX() + " Y:" + newPoint.getY());
    }

    @Override
    public void undo() {

        // Undo skida sa steka i primenjuje stare pozicije na novu
        if(!moves.empty()){

            ShapeMoving shapeMoving = moves.pop();

            // patimo pre undo-a pozicije
            currentPoints = shapeMoving.movedFigure.getPoints(); // trenutne pamtimo u stare
            undoMoves.push(new ShapeMoving(shapeMoving.movedFigure, currentPoints));

            // premestamo na stare pozicije
            shapeMoving.movedFigure.setPoints(shapeMoving.movedPoints);
        }
    }

    @Override
    public void redo() {

        if(!undoMoves.empty()){

            ShapeMoving undoShapeMoving = undoMoves.pop();

            currentPoints = undoShapeMoving.movedFigure.getPoints();
            moves.push(new ShapeMoving(undoShapeMoving.movedFigure, currentPoints));

            // premestamo na stare pozicije
            undoShapeMoving.movedFigure.setPoints(undoShapeMoving.movedPoints);
        }
    }
}
