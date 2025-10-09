package graphics.support.io.shapes;

import graphics.model.shapes.Line;

public class LineConverter implements Converter<Line> {
    @Override
    public String serialize(Line line) {
        StringBuilder retString = new StringBuilder();

//        retString.append(super.saveFormat());
//
//        retString.append(startPoint.x).append(",").append(startPoint.y).append(";"); // prva tacka
//        retString.append(endPoint.x).append(",").append(endPoint.y).append(";"); // druga tacka
        return retString.toString();
    }

    @Override
    public Line deserialize(String string) {
        return null;
    }
}
