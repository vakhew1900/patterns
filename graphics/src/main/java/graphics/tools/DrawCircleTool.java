package graphics.tools;

import graphics.crud.DrawingService;
import graphics.model.shapes.Circle;
import graphics.model.shapes.Line;
import graphics.model.shapes.ShapeEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DrawCircleTool extends DrawTool {

    public DrawCircleTool(DrawingService dao, JLabel logger) {
        super(dao, logger);
    }

    @Override
    public String log() {
        return "";
    }

    @Override
    public ShapeEnum getType() {
        return ShapeEnum.CIRCLE;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public String getName() {
        return "Circle";
    }
}
