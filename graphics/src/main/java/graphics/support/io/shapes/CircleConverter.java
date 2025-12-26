package graphics.support.io.shapes;

import graphics.model.shapes.Circle;

public class CircleConverter implements Converter<Circle>{
    @Override
    public String serialize(Circle object) {
        throw new IllegalArgumentException("не реализовано");
    }

    @Override
    public Circle deserialize(String string) {
        throw new IllegalArgumentException("не реализовано");
    }
}
