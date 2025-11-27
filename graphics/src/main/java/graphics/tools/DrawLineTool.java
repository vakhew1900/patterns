package graphics.tools;

import graphics.crud.DrawingService;
import graphics.model.shapes.Line;
import graphics.model.shapes.ShapeEnum;

import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Matija on 14 Jun 17.
 */
public class DrawLineTool extends DrawTool {

    public static final String NAME = "Line";

    public DrawLineTool(DrawingService dao) {
        super(dao);
    }


    @Override
    public void mouseClicked(MouseEvent e){}


    @Override
    public String log() {
        StringBuilder rightLabelTxt = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        Line line = (Line) shape;
        Point end = line.getEndPoint();
        Point start = line.getStartPoint();

        assert end != null;
        rightLabelTxt.append("Distance: ").append(df.format(Point.distance(start.x, start.y, end.x, end.y)));
        rightLabelTxt.append(" X: ").append(end.x).append(" Y: ").append(end.y);

        return rightLabelTxt.toString();
    }

    @Override
    public ShapeEnum getType() {
        return ShapeEnum.LINE;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
