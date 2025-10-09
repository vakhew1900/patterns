package graphics.support.io;

import graphics.model.Drawing;
import graphics.support.io.shapes.Converter;

public abstract class FileService {

    Converter<Drawing> drawingConverter;

    public FileService(Converter<Drawing> drawingConverter) {
        this.drawingConverter = drawingConverter;
    }

    public void write(String fileName, Drawing drawing) {

    }
}
