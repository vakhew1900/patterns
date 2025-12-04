package graphics.command;

import graphics.crud.DrawingService;

public class UnselectCommand extends DrawingCommand {

    public UnselectCommand(DrawingService drawingService) {
        super(drawingService, false);
    }

    @Override
    public void execute() {
        drawingService.unselect();;
    }

    @Override
    public void undo() {

    }
}
