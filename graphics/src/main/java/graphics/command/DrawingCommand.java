package graphics.command;

import graphics.crud.DrawingService;
import graphics.model.Drawing;
import lombok.Getter;

public abstract class DrawingCommand implements Command, isHistoryNeedable {

    protected final DrawingService drawingService;
    private final Drawing.DrawingMemento backup;
    protected boolean isHistoryNeed;

    public DrawingCommand(DrawingService drawingService, boolean isHistoryNeed) {
        this.drawingService = drawingService;
        this.backup = drawingService.snapshot();
        this.isHistoryNeed = isHistoryNeed;
    }

    @Override
    public void undo() {
        drawingService.repaintDrawing(backup.restore());
    }

    @Override
    public boolean isNeedHistory() {
        return isHistoryNeed;
    }
}
