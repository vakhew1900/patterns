package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;

import java.awt.*;

public class MoveCommand extends DrawingCommand{

    private final Shape shape;
    private final Point point;

    public MoveCommand(DrawingService drawingService, boolean isHistoryNeed, Shape shape, Point point) {
        super(drawingService, false);
        this.shape = shape;
        this.point = point;
    }

    @Override
    public void execute() {
        drawingService.moveNew(shape, point);
    }
}
