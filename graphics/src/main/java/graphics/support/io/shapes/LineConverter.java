package graphics.support.io.shapes;

import graphics.model.shapes.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class LineConverter extends ShapeConverter<Line> {

    @Override
    public String serialize(Line line) {
        StringBuilder retString = new StringBuilder();

        retString.append(super.serialize(line));

        retString.append(line.getStartPoint().x).append(",").append(line.getStartPoint().y).append(";"); // prva tacka
        retString.append(line.getEndPoint().x).append(",").append(line.getEndPoint().y).append(";"); // druga tacka
        return retString.toString();
    }

    @Override
    public Line deserialize(String string) {
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

            return new Line(pointsForFigura, lineThick,lineColor);
        }

        throw new RuntimeException("Illegal format " + string);
    }
}
