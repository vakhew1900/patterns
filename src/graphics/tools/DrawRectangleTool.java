package graphics.tools;

import graphics.*;
import graphics.shapes.Rectangle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Matija on 16 Jun 17.
 */
public class DrawRectangleTool extends Tool {
    private Rectangle rectangle;
    private Point start,end;
    public DrawRectangleTool(){
        super();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        end = start = e.getPoint(); // Pocetna tacka ujedno i krajnja

        // nova linija
        assert MainForm.lineThick.getSelectedItem() != null;
        rectangle = new Rectangle(start,end, (int) MainForm.lineThick.getSelectedItem(), MainForm.lineColor.getSelectedColor());
        WorkPanel.drawing.addFigure(rectangle); // dodajemo je odmah
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDrag(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseDrag(MouseEvent e) {
        end = e.getPoint();

        rectangle.setEndPoint(end);

        StringBuilder rightLabelTxt = new StringBuilder();

        int width = Math.abs(end.x - start.x);
        int height = Math.abs(end.y - start.y);

        rightLabelTxt.append("Width: ").append(width).append(" Height: ").append(height);
        rightLabelTxt.append(" X: ").append(end.x).append(" Y: ").append(end.y);

        MainForm.rightLabel.setText(rightLabelTxt.toString());
    }
}
