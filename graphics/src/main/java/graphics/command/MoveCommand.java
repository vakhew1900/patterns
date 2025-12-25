package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;

import java.awt.*;

public class MoveCommand extends DrawingCommand{

    private final Shape shape;
    private final Point point;
    private final Shape prevShape;

    public MoveCommand(DrawingService drawingService, Shape prevShape, Shape shape, Point point) {
        super(drawingService);
        this.shape = shape;
        this.prevShape = prevShape;
        this.point = point;
    }

    @Override
    public void execute() {
        shape.setPoints(prevShape.getPoints());
        shape.setDelta((Point) prevShape.getDelta().clone());
        drawingService.moveNew(shape, point);
    }
}
