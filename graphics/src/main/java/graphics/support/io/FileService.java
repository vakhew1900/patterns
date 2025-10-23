package graphics.support.io;

import graphics.model.Drawing;
import graphics.support.io.shapes.Converter;
import graphics.support.io.shapes.DrawingConverter;

import java.io.*;

public abstract class FileService {

    Converter<Drawing> drawingConverter;

    public FileService(Converter<Drawing> drawingConverter) {
        this.drawingConverter = drawingConverter;
    }

    public void write(String fileName, Drawing drawing) throws IOException {
        String string = drawingConverter.serialize(drawing);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(string);
        }
    }

    public Drawing read(String fileName) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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
