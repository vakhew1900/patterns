package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;

import java.awt.*;

public class DrawShapeCommand extends DrawingCommand {

    private final Shape shape;
    private final Point point;
    public DrawShapeCommand(DrawingService drawingService, Shape shape, Point point) {
        super(drawingService, false);
        this.shape = shape;
        this.point = point;
    }

    @Override
    public void execute() {
        drawingService.update(shape, point);
    }
}
