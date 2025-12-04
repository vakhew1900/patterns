package graphics.crud;

import graphics.listener.DrawingChangedListener;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DrawingService {

    private final List<DrawingChangedListener> listeners = new ArrayList<>();
    @Getter
    private final Drawing drawing;

    public DrawingService(Drawing drawing) {
        this.drawing = drawing;
    }

    public void add(Shape shape){
        drawing.add(shape);
        notifyListeners();
    }

    public void delete(Point coordinates) {
        Shape toDelete = drawing.select(coordinates);
        if(toDelete != null){
            drawing.delete(toDelete);
            notifyListeners();
        }
    }

    public void deleteAll() {
        drawing.deleteAll();
        notifyListeners();
    }

    public Shape get(Point coordinates) {
        return drawing.select(coordinates);
    }

    public void update(Shape shape, Point point) {
        if (drawing.contains(shape)) {
            shape.resize(point);
            notifyListeners();
        }
    }

    public void select(Shape shape, boolean select) {
        if (drawing.contains(shape)) {
            shape.setSelected(select);
            notifyListeners();
        }
    }

    public void moveNew(Shape shape, Point newPos) {
        if (drawing.contains(shape)) {
            shape.moveNew(newPos);
            notifyListeners();
        }
    }

    public void  unselect() {
        drawing.forEach(
                shape -> {
                    shape.setSelected(false);
                }
        );
        notifyListeners();
    }

    public void repaintDrawing(Drawing drawing) {
        this.drawing.deleteAll();;
        this.drawing.addAll(drawing);
        notifyListeners();
    }

    public void catchNew(Shape shape, Point newPos) {
        if (drawing.contains(shape)) {
            shape.setNewCatch(newPos);
            notifyListeners();
        }
    }


    public void addListener(DrawingChangedListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(){
        listeners.forEach(
                DrawingChangedListener::drawingChanged
        );
    }

    public Drawing.DrawingMemento snapshot() {
        return drawing.save();
    }
}
