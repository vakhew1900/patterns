package graphics.command;

import graphics.crud.DrawingService;

import java.awt.*;

public class EraseCommand extends DrawingCommand {

    private Point coordinate;
    public EraseCommand(DrawingService drawingService, Point coordinate) {
        super(drawingService, true);
        this.coordinate = coordinate;
    }

    @Override
    public void execute() {
        drawingService.delete(coordinate);
    }


}
