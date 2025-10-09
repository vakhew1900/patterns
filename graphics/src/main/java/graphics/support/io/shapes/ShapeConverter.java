package graphics.support.io.shapes;

import graphics.model.shapes.Shape;

public abstract class ShapeConverter implements Converter<Shape> {
    @Override
    public String serialize(Shape object) {
        return "";
    }

    @Override
    public Shape deserialize(String string) {
        return null;
    }
}
