package graphics.gui;

import graphics.listener.LineThickChangedListener;

import javax.swing.*;
import java.util.ArrayList;

public class LineThickButton extends JComboBox<Integer> {

    private final ArrayList<LineThickChangedListener> listeners = new ArrayList<>();

    public LineThickButton(Integer[] items) {
        super(items);
        addActionListener(
            e -> {
                int lineThick = (int) this.getSelectedItem();
                listeners.forEach(
                        listener -> {
                            listener.lineThickChanged(lineThick);
                        }
                );
            }
        );
    }

    public void addLineThickChangedListener(LineThickChangedListener toAdd) {
        listeners.add(toAdd);
    }

}
