package graphics.model.shapes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ShapeEnum {
    LINE(Line.class),
    RECTANGLE(Rectangle.class),
    CIRCLE(Circle.class);

    @Getter
    private transient Class<? extends Shape> clazz;
}
