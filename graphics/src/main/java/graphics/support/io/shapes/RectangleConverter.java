package graphics.support.io.shapes;

import graphics.model.shapes.Line;
import graphics.model.shapes.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class RectangleConverter extends ShapeConverter<Rectangle> {

    @Override
    public String serialize(Rectangle rectangle) {
        StringBuilder retString = new StringBuilder();

        retString.append(super.serialize(rectangle));

        retString.append(rectangle.getStartPoint().x).append(",").append(rectangle.getStartPoint().y).append(";"); // prva tacka
        retString.append(rectangle.getEndPoint().x).append(",").append(rectangle.getEndPoint().y).append(";"); // druga tacka
        return retString.toString();
    }

    @Override
    public Rectangle deserialize(String string) {
        Matcher lineMatcher = LINE_FORMAT.matcher(string);

        if(lineMatcher.matches()){
            int lineThick = Integer.parseInt(lineMatcher.group(2));
            Color lineColor = new Color(Integer.parseInt(lineMatcher.group(3)));

            String tacke = lineMatcher.group(4);

            Matcher pointMatcher = POINT_FORMAT.matcher(tacke);
            System.out.println(tacke);

            ArrayList<Point> pointsForFigura = new ArrayList<>();

            while(pointMatcher.find()){ // Za svaku tacku
                int x = Integer.parseInt(pointMatcher.group(1));
                int y = Integer.parseInt(pointMatcher.group(2));
                pointsForFigura.add(new Point(x,y)); // Ubacujemo u listu tacaka
            }

            return new Rectangle(pointsForFigura, lineThick,lineColor);
        }

        throw new RuntimeException("Illegal format " + string);
    }
}
