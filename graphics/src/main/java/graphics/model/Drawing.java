package graphics.model;

import graphics.model.shapes.Shape;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.*;
import java.util.List;

@AllArgsConstructor
public class Drawing implements Iterable<Shape> {
    private final List<Shape> shapes;

    public Drawing(Shape... figure){
        shapes = new ArrayList<>();

        Collections.addAll(shapes, figure);
    }

    public Shape selectFigure(Point coord){

        int indexFigure = shapes.size() - 1;

        while(indexFigure >= 0){
            Shape tempFigura = shapes.get(indexFigure);

            if(tempFigura.selected(coord))
                return tempFigura;

            indexFigure --;
        }

        return null;
    }
    public void deleteFigura(Shape toDelete){
        int indexFigure = shapes.size() - 1;

        // Pronalazimo
        while (indexFigure >= 0 && shapes.get(indexFigure) != toDelete){
            indexFigure --;
        }

        if(indexFigure != -1) { // Ako smo pronasli
            // Ubacujemo na stek
            //undoFigures.push(lines.get(indexFigure));

            // Brisemo je
            shapes.remove(indexFigure);
        }
    }

    public void deleteAll(){
        shapes.clear();
    }

    @Override
    public Iterator<Shape> iterator() {
        return shapes.iterator();
    }

}
