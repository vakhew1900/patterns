package graphics.render;

import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class SwingShapeRender extends ShapeRender {
    private final Graphics2D graphics2D;


    @Override
    public void setColor(Color c) {
        if (graphics2D != null) {
            graphics2D.setColor(c);
        }
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        if (graphics2D != null) {
            graphics2D.drawLine(x1, y1, x2, y2);
        }
    }

    @Override
    public void setStroke(Stroke s) {
        if (graphics2D != null) {
            graphics2D.setStroke(s);
        }
    }

    @Override
    public void drawOval(int x, int y, int width, int height) {
        if (graphics2D != null) {
            graphics2D.drawOval(x, y, width, height);
        }
    }
}