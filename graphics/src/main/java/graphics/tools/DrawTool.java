package graphics.tools;

import graphics.listener.ColorChangedListener;
import graphics.MainForm;
import graphics.WorkPanel;
import graphics.listener.LineThickChangedListener;
import graphics.model.Drawing;
import graphics.model.shapes.Shape;
import graphics.model.shapes.ShapeEnum;
import graphics.support.fabric.ShapeFabric;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

public abstract class DrawTool extends Tool implements ColorChangedListener, LineThickChangedListener {
    @Getter
    private int lineThick = 1;
    @Getter
    private Color color;
    protected Shape shape;

    public DrawTool(Supplier<Drawing> supplier) {
        super(supplier);
    }


    @Override
    public void colorChanged(Color color) {
        this.color = color;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        assert color != null;
        shape = new ShapeFabric().createShape(getType(), e.getPoint(), lineThick, color);
        System.out.println(shape);
        getDrawing().add(shape); // dodajemo je odmah
    }

    @Override
    public void mouseDrag(MouseEvent e) {
        shape.update(e.getPoint());

        MainForm.rightLabel.setText(log());
    }

    @Override
    public void lineThickChanged(int lineThick) {
        this.lineThick = lineThick;
    }

    public abstract String log();

    public abstract ShapeEnum getType();
}
