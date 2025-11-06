package graphics.model;

import graphics.model.shapes.Shape;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.*;
import java.util.List;

@AllArgsConstructor
public class Drawing implements Iterable<Shape> {
    private final List<Shape> shapes;
    private Stack<Shape> undoFigures = new Stack<>();


    public Drawing(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public Drawing(Shape... figure){
        shapes = new ArrayList<>();

        Collections.addAll(shapes, figure);
    }

    public void add(Shape newFigure){
        shapes.add(newFigure);
    }
    public Shape select(Point coord){

        int indexFigure = shapes.size() - 1;

        while(indexFigure >= 0){
            Shape tempFigura = shapes.get(indexFigure);

            if(tempFigura.selected(coord))
                return tempFigura;

            indexFigure --;
        }

        return null;
    }
    public void delete(Shape toDelete){
        shapes.remove(toDelete);
    }

    public void deleteAll(){
        shapes.clear();
    }

    @Override
    public Iterator<Shape> iterator() {
        return shapes.iterator();
    }


    public void undo(){
        if(!shapes.isEmpty()) {
            Shape lastFigura = shapes.getLast();
            undoFigures.push(lastFigura);
            shapes.removeLast();
        }
    }


    public void redo(){
        if(!undoFigures.empty()){
            Shape redoFigure = undoFigures.pop();
            add(redoFigure);
        }
    }
}
