package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.Drawing;

public class RepaintDrawingCommand extends DrawingCommand{

    private final Drawing drawing;
    public RepaintDrawingCommand(DrawingService drawingService, Drawing drawing) {
        super(drawingService);
        this.drawing =drawing;
    }

    @Override
    public void execute() {
        drawingService.repaintDrawing(drawing);
    }
}
