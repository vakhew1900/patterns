package graphics.model.shapes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.*;

/**
 * Created by Matija on 16 Jun 17.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Rectangle extends Shape  {
    @Setter
    private Point endPoint;
    private Point startPoint;

    public Rectangle(Point startPoint, Point endPoint, int thick, Color color) {
        super(thick, color);
        // Podesavamo tacke
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Rectangle(ArrayList<Point> points, int thick, Color color){
        super(thick, color);

        if(points != null) {
            this.startPoint = points.get(0);
            this.endPoint = points.get(1);
        }
    }

    @Override
    public void setPoints(ArrayList<Point> newPoints){ // Postavi nove tacke
        if (newPoints != null) {
            this.startPoint = (Point) newPoints.get(0).clone();
            this.endPoint = (Point) newPoints.get(1).clone();
        }
    }

    @Override
    public ArrayList<Point> getPoints(){
        ArrayList<Point> retList = new ArrayList<>();
        retList.add((Point) startPoint.clone());
        retList.add((Point) endPoint.clone());

        return retList;
    }


    @Override
    public void resize(Point point) {
        setEndPoint(point);
    }

    @Override
    public void paint(Graphics2D g) {
        if(figureSelected){
            g.setColor(selectionColor);
            g.setStroke(new BasicStroke(lineThick * 3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            iscrtajLinije(g);
        }

        g.setColor(lineColor);
        g.setStroke(new BasicStroke(lineThick));

        iscrtajLinije(g);

    }

    public void iscrtajLinije(Graphics2D g){
        g.drawLine(startPoint.x, startPoint.y, endPoint.x, startPoint.y);
        g.drawLine(startPoint.x, startPoint.y, startPoint.x, endPoint.y);
        g.drawLine(endPoint.x, endPoint.y, endPoint.x, startPoint.y);
        g.drawLine(endPoint.x, endPoint.y, startPoint.x, endPoint.y);
    }

    @Override
    public boolean selected(Point coord) {
        Line gornja, donja, leva, desna; // Sve duzi
        gornja = new Line(startPoint, new Point(endPoint.x, startPoint.y), lineThick, lineColor);
        donja = new Line(endPoint, new Point(startPoint.x, endPoint.y), lineThick, lineColor);
        leva = new Line(startPoint, new Point(startPoint.x, endPoint.y), lineThick, lineColor);
        desna = new Line(endPoint, new Point(endPoint.x, startPoint.y), lineThick, lineColor);

        return gornja.selected(coord) || donja.selected(coord) || leva.selected(coord) || desna.selected(coord);
    }

    @Override
    public void move(Point deltaPoint) {
        startPoint.translate(deltaPoint.x, deltaPoint.y);
        endPoint.translate(deltaPoint.x, deltaPoint.y);
    }

    @Override
    public void moveNew(Point newPos) {
        Point pointNewStart = new Point(newPos.x + delta.x, newPos.y + delta.y);
        Point newEnd = new Point(pointNewStart.x - startPoint.x + endPoint.x, pointNewStart.y - startPoint.y + endPoint.y);

        startPoint = pointNewStart;
        endPoint = newEnd;
    }

    @Override
    public void setNewCatch(Point catchPos) {
        delta = new Point(startPoint.x - catchPos.x, startPoint.y - catchPos.y);
    }

    @Override
    public Rectangle clone() {
        return new Rectangle((Point) startPoint.clone(), (Point) endPoint.clone(), lineThick, new Color(lineColor.getRGB()));
    }
}
