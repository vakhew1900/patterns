package graphics.tools;

import graphics.crud.DrawingService;
import graphics.model.shapes.Rectangle;
import graphics.model.shapes.ShapeEnum;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Matija on 16 Jun 17.
 */
public class DrawRectangleTool extends DrawTool {

    public DrawRectangleTool(DrawingService dao) {
        super(dao);
    }

    @Override
    public String log() {

        StringBuilder rightLabelTxt = new StringBuilder();
        Rectangle rectangle = (Rectangle) shape;
        Point end = rectangle.getEndPoint();
        Point start = rectangle.getStartPoint();

        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);

        rightLabelTxt.append("Width: ").append(width).append(" Height: ").append(height);
        rightLabelTxt.append(" X: ").append(end.x).append(" Y: ").append(end.y);

        return rightLabelTxt.toString();
    }

    @Override
    public ShapeEnum getType() {
        return ShapeEnum.RECTANGLE;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDrag(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
}
