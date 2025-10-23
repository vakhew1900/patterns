package graphics.support.io;

import graphics.model.Drawing;
import graphics.support.io.shapes.Converter;
import graphics.support.io.shapes.DrawingConverter;
import lombok.NoArgsConstructor;

import java.io.*;

@NoArgsConstructor
public class FileService {

    Converter<Drawing> drawingConverter = new DrawingConverter();

    public void write(File file, Drawing drawing) throws IOException {
        String string = drawingConverter.serialize(drawing);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(string);
        }
    }

    public Drawing read(File file) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(DrawingConverter.SPLITTER);
            }

            return drawingConverter.deserialize(stringBuilder.toString());
        }
    }
}
