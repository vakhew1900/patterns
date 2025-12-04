package graphics.model.shapes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Matija on 12 Jun 17.
 */
@Getter
@Setter
@EqualsAndHashCode
public abstract class Shape implements Cloneable{
    protected int lineThick = 1;
    protected Color lineColor = Color.BLACK;
    protected Point delta; // rastojanje gde je mis uhvatio u odnosu na pocetak
    protected boolean figureSelected;

    protected static Color selectionColor = new Color(255,255,0,85);
    protected static int catchCoef = 2; // lineThick * catchCoef = click to select area

    public Shape(int thick, Color color) {
        lineThick = thick;
        lineColor = color;
    }

    public abstract void resize(Point point);

    public abstract void paint(Graphics2D g); // paint figure

    public abstract boolean selected(Point coord); // da li je selektovana

    public abstract void move(Point delta); // pomeri figuru

    public abstract void moveNew(Point newPos); // pomeri na novu

    public abstract void setPoints(ArrayList<Point> points); // Postavi nove tacke
    public abstract ArrayList<Point> getPoints(); // Dohvati tacke

    public abstract void setNewCatch(Point catchPos);
    // Podesava da li je selektovan
    public void setSelected(boolean select){
        figureSelected = select;
    }

    @Override
    public abstract Shape clone();
}