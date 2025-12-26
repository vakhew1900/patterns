package graphics.model.shapes;

import graphics.render.ShapeRender;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Circle extends Shape {
    private Point center;
    private int radius;

    private final ShapeEnum type = ShapeEnum.CIRCLE;

    @Override
    public ShapeEnum getType() {
        return type;
    }


    public Circle(Point center, int radius, int thick, Color color) {
        super(thick, color);
        this.center = center;
        this.radius = radius;
    }

    public Circle(Point center, Point pointOnCircle, int thick, Color color) {
        super(thick, color);
        this.center = center;
        this.radius = calculateRadius(center, pointOnCircle);
    }

    public Circle(ArrayList<Point> points, int thick, Color color) {
        super(thick, color);
        if (points != null && points.size() >= 2) {
            this.center = points.get(0);
            Point pointOnCircle = points.get(1);
            this.radius = calculateRadius(center, pointOnCircle);
        }
    }

    private int calculateRadius(Point center, Point pointOnCircle) {
        return (int) Math.sqrt(
                Math.pow(pointOnCircle.x - center.x, 2) +
                        Math.pow(pointOnCircle.y - center.y, 2)
        );
    }

    private Point getPointOnCircle() {
        return new Point(center.x + radius, center.y);
    }

    @Override
    public void setPoints(ArrayList<Point> newPoints) {
        if (newPoints != null && newPoints.size() >= 2) {
            this.center = (Point) newPoints.get(0).clone();
            Point pointOnCircle = newPoints.get(1);
            this.radius = calculateRadius(center, pointOnCircle);
        }
    }

    @Override
    public ArrayList<Point> getPoints() {
        ArrayList<Point> retList = new ArrayList<>();
        retList.add((Point) center.clone());
        retList.add(getPointOnCircle());
        return retList;
    }

    @Override
    public void resize(Point point) {
        this.radius = calculateRadius(center, point);
    }

    @Override
    public void paint(ShapeRender g) {
        int diameter = radius * 2;

        if (figureSelected) {
            g.setColor(selectionColor);
            g.setStroke(new BasicStroke(lineThick * 3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawOval(center.x - radius, center.y - radius, diameter, diameter);
        }

        g.setColor(lineColor);
        g.setStroke(new BasicStroke(lineThick));
        g.drawOval(center.x - radius, center.y - radius, diameter, diameter);
    }

    @Override
    public boolean selected(Point coord) {
        double distance = Math.sqrt(
                Math.pow(coord.x - center.x, 2) +
                        Math.pow(coord.y - center.y, 2)
        );

        // Проверяем, находится ли точка на окружности (в пределах толщины линии)
        return Math.abs(distance - radius) <= (lineThick * catchCoef);
    }

    @Override
    public void move(Point deltaPoint) {
        center.translate(deltaPoint.x, deltaPoint.y);
    }

    @Override
    public void moveNew(Point newPos) {
        Point newCenter = new Point(newPos.x + delta.x, newPos.y + delta.y);
        center = newCenter;
    }

    @Override
    public void setNewCatch(Point catchPos) {
        delta = new Point(center.x - catchPos.x, center.y - catchPos.y);
    }

    @Override
    public Circle clone() {
        Circle circle = new Circle(
                (Point) center.clone(),
                radius,
                lineThick,
                new Color(lineColor.getRGB())
        );

        if (delta != null) {
            circle.setDelta((Point) delta.clone());
        }

        return circle;
    }


    public void setRadius(int radius) {
        this.radius = radius;
    }
}