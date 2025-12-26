package graphics.support.io.shapes;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;
import graphics.model.shapes.ShapeEnum;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class DrawingConverter implements Converter<Drawing> {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Shape.class, new ShapeTypeAdapter())
            .registerTypeAdapter(Color.class, new ColorTypeAdapter())
            .create();


    @Override
    public String serialize(Drawing drawing) {
       return  gson.toJson(drawing);
    }

    @Override
    public Drawing deserialize(String string) {
        return gson.fromJson(string, Drawing.class);
    }


    private class ShapeTypeAdapter implements JsonDeserializer<Shape> {


        @Override
        public Shape deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            ShapeEnum shapeType =  ShapeEnum.valueOf(jsonElement.getAsJsonObject().get("type").getAsString());
            return gson.fromJson(gson.toJson(jsonElement), shapeType.getClazz());
        }
    }


    public class ColorTypeAdapter extends TypeAdapter<Color> {

        @Override
        public void write(JsonWriter out, Color color) throws IOException {
            if (color == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            out.name("rgb");
            out.value(color.getRGB());
            out.endObject();
        }

        @Override
        public Color read(JsonReader in) throws IOException {
            in.beginObject();
            in.nextName(); // пропускаем имя поля "rgb"
            int rgb = in.nextInt();
            in.endObject();
            return new Color(rgb);
        }
    }
}
