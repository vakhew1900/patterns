package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.Drawing;

public abstract class DrawingCommand implements Command {

    protected final DrawingService drawingService;
    private final Drawing.DrawingMemento backup;

    public DrawingCommand(DrawingService drawingService) {
        this.drawingService = drawingService;
        this.backup = drawingService.snapshot();
    }

    @Override
    public void undo() {
        drawingService.repaintDrawing(backup.restore());
    }
}
