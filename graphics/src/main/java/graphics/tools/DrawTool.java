package graphics.tools;

import graphics.command.AddShapeCommand;
import graphics.command.CommandContainer;
import graphics.command.DrawShapeCommand;
import graphics.crud.DrawingService;
import graphics.listener.ColorChangedListener;
import graphics.MainForm;
import graphics.listener.LineThickChangedListener;
import graphics.model.shapes.Shape;
import graphics.model.shapes.ShapeEnum;
import graphics.support.fabric.ShapeFabric;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class DrawTool extends Tool implements ColorChangedListener, LineThickChangedListener {
    @Getter
    private int lineThick = 1;
    @Getter
    private Color color;
    protected Shape shape;

    public DrawTool(DrawingService dao) {
        super(dao);
    }

    @Override
    public void colorChanged(Color color) {
        this.color = color;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        assert color != null;
        shape = new ShapeFabric().createShape(getType(), e.getPoint(), lineThick, color);
        System.out.println("linePressed " + e.getPoint());
        CommandContainer.getInstance().executeCommand(
                new AddShapeCommand(getService(), shape)
        );
    }

    @Override
    public void mouseDrag(MouseEvent e) {
        CommandContainer.getInstance().executeCommand(
                new DrawShapeCommand(getService(), shape, e.getPoint())
        );
        MainForm.rightLabel.setText(log());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        CommandContainer.getInstance().executeCommand(
                new DrawShapeCommand(getService(), shape, e.getPoint())
        );
        MainForm.rightLabel.setText(log());
    }

    @Override
    public void lineThickChanged(int lineThick) {
        this.lineThick = lineThick;
    }

    public abstract String log();

    public abstract ShapeEnum getType();
}
