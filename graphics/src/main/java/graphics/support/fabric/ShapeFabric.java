package graphics.support.fabric;

import graphics.model.shapes.Line;
import graphics.model.shapes.Rectangle;
import graphics.model.shapes.Shape;
import graphics.model.shapes.ShapeEnum;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ShapeFabric {
    public Shape createShape(ShapeEnum type, Point point, int lineThick, Color color) {
       return switch (type) {
           case LINE -> new Line(point, point, lineThick, color);
           case RECTANGLE -> new Rectangle(point, point, lineThick, color);
        };
    }
}
