package graphics.support.io.shapes;

public interface  Converter<T> {

    String serialize(T object);
    T deserialize(String string);
}
