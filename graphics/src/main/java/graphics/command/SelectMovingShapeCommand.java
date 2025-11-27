package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.shapes.Shape;

import java.awt.*;

public class SelectMovingShapeCommand extends DrawingCommand{


    private final Shape shape;
    private Point coordinate;

    public SelectMovingShapeCommand(DrawingService drawingService, Shape shape, Point coordinate) {
        super(drawingService, false);
        this.shape = shape;
        this.coordinate = coordinate;
    }

    @Override
    public void execute() {

        if (shape != null) {
            isHistoryNeed = true;
            shape.setNewCatch(coordinate);
        }
    }
}
