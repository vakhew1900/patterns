package graphics.tools;

import graphics.*;
import graphics.model.shapes.Line;

import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static graphics.MainForm.*;

/**
 * Created by Matija on 14 Jun 17.
 */
public class DrawLineTool extends Tool {
    private Line newLine;
    private Point start, end;

    public DrawLineTool(){
        super();
    }

    @Override
    public void mousePressed(MouseEvent e){

        end = start = e.getPoint(); // Pocetna tacka ujedno i krajnja

        // nova linija
        assert lineThick.getSelectedItem() != null;
        newLine = new Line(start,end, (int) lineThick.getSelectedItem(), lineColor.getSelectedColor());
        WorkPanel.drawing.add(newLine); // dodajemo je odmah
    }

    @Override
    public void mouseReleased(MouseEvent e){
        mouseDrag(e); // pozivamo drag da bi se zavrsio
    }

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseDrag(MouseEvent e){
        end = e.getPoint();
        newLine.setNewEnd(end);

        StringBuilder rightLabelTxt = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        if(end != null)
            rightLabelTxt.append("Distance: ").append(df.format(Point.distance(start.x, start.y, end.x, end.y)));

        assert end != null;
        rightLabelTxt.append(" X: ").append(end.x).append(" Y: ").append(end.y);

        rightLabel.setText(rightLabelTxt.toString());
    }
}
