package graphics.model;

import graphics.model.shapes.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Drawing {
    private final ArrayList<Shape> lines;

    public Drawing(Shape... figure){
        lines = new ArrayList<>();

        Collections.addAll(lines, figure);
    }

    public Shape selectFigure(Point coord){

        int indexFigure = lines.size() - 1;

        while(indexFigure >= 0){
            Shape tempFigura = lines.get(indexFigure);

            if(tempFigura.selected(coord))
                return tempFigura;

            indexFigure --;
        }

        return null;
    }


    public void deleteFigura(Shape toDelete){
        int indexFigure = lines.size() - 1;

        // Pronalazimo
        while (indexFigure >= 0 && lines.get(indexFigure) != toDelete){
            indexFigure --;
        }

        if(indexFigure != -1) { // Ako smo pronasli
            // Ubacujemo na stek
            //undoFigures.push(lines.get(indexFigure));

            // Brisemo je
            lines.remove(indexFigure);
        }
    }

    public void deleteAll(){
        lines.clear();
    }
}
