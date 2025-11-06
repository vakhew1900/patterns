package graphics.tools;

import graphics.crud.DrawingService;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Matija on 16 Jun 17.
 */
public class EraseTool extends Tool {

    public EraseTool(DrawingService dao) {
        super(dao);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        Point currPoint = e.getPoint();
        getDao().delete(currPoint);
    }

    @Override
    public void mouseDrag(MouseEvent e) {}
}
