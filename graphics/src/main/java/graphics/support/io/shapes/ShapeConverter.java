package graphics.support.io.shapes;

import graphics.model.shapes.Shape;

import java.util.regex.Pattern;

public abstract class ShapeConverter<T extends Shape> implements Converter<T> {


    public  final static Pattern POINT_FORMAT = Pattern.compile("([\\-0-9]+),([\\-0-9]+)");
    public final static Pattern LINE_FORMAT = Pattern.compile("([^\\(]+)\\(([\\-0-9]+),([\\-0-9]+)\\):((([\\-0-9]+),([\\-0-9]+);){2,})");

    @Override
    public String serialize(T shape) {
        return  shape.getClass().getName() + "(" + shape.getLineThick() + ","+ shape.getLineColor().getRGB() + "):";
    }

    @Override
    public T deserialize(String string) {
        return null;
    }
}
