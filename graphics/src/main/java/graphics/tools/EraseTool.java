package graphics.tools;

import graphics.command.CommandContainer;
import graphics.command.EraseCommand;
import graphics.crud.DrawingService;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Matija on 16 Jun 17.
 */
public class EraseTool extends Tool {

    public static final String NAME = "Erase";

    public EraseTool(DrawingService service) {
        super(service);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (getService().get(e.getPoint()) != null) {
            CommandContainer.getInstance()
                    .executeCommand(new EraseCommand(getService(), e.getPoint()));
        }
    }

    @Override
    public void mouseDrag(MouseEvent e) {}

    @Override
    public String getName() {
        return NAME;
    }
}
