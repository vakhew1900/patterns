package graphics.render;

import java.awt.*;

public abstract class ShapeRender {

    public abstract void setColor(Color c);
    public abstract void drawLine(int x1, int y1, int x2, int y2);
    public abstract void setStroke(Stroke s);
    public abstract void drawOval(int x, int y, int width, int height);
}
