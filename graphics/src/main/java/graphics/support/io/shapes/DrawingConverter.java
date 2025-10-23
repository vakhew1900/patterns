package graphics.support.io.shapes;

import graphics.model.Drawing;
import graphics.model.shapes.Line;
import graphics.model.shapes.Rectangle;
import graphics.model.shapes.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import static graphics.support.io.shapes.ShapeConverter.LINE_FORMAT;

public class DrawingConverter implements Converter<Drawing> {
    public static final String SPLITTER = "\r\n";
    Map<String, ShapeConverter<?>> converters = Map.of(
            Line.class.getName(), new LineConverter(),
            Rectangle.class.getName(), new RectangleConverter()
    );

    @Override
    public String serialize(Drawing drawing) {
        StringBuilder stringBuilder = new StringBuilder();

        for(var shape :  drawing) {
            stringBuilder.append(serializeShape(shape));
            stringBuilder.append(SPLITTER);
        }

        return stringBuilder.toString();
    }

    @Override
    public Drawing deserialize(String string) {
        List<String>  lines = Arrays.stream(string.split(SPLITTER)).toList();

        List<Shape> shapes = new ArrayList<>();

        for(var line : lines) {
            Matcher lineMatcher = LINE_FORMAT.matcher(string);
            if(lineMatcher.matches()) {
                String className = lineMatcher.group(1);
                shapes.add(converters.get(className).deserialize(line));
            }
        }

        return  new Drawing(shapes);
    }

    private String serializeShape(Shape shape) {
        return serializeWithConverter(shape, converters.get(shape.getClass().getName()));
    }

    @SuppressWarnings("unchecked")
    private <T extends Shape> String serializeWithConverter(T shape, ShapeConverter<?> converter) {
        return ((ShapeConverter<T>) converter).serialize(shape);
    }
}
