package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;

public class AddShapeCommand extends DrawingCommand {

    private final Shape shape;

    public AddShapeCommand(
            DrawingService drawingService,
            Shape shape
    ) {
        super(drawingService);
        this.shape = shape;
    }

    @Override
    public void execute() {
        drawingService.add(shape);
    }
}
