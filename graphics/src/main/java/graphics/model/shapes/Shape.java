package graphics.model.shapes;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Matija on 12 Jun 17.
 */
@Getter
public abstract class Shape {
    protected int lineThick = 1;
    protected Color lineColor = Color.BLACK;
    protected Point startPoint;
    protected int deltaStartX, deltaStartY; // rastojanje gde je mis uhvatio u odnosu na pocetak
    protected boolean figureSelected;

    protected static Color selectionColor = new Color(255,255,0,85);
    protected static int catchCoef = 2; // lineThick * catchCoef = click to select area

    public Shape(int thick, Color color) {
        lineThick = thick;
        lineColor = color;
    }

    public abstract void paint(Graphics2D g); // paint figure

    public abstract boolean selected(Point coord); // da li je selektovana

    public abstract void move(Point delta); // pomeri figuru

    public abstract void moveNew(Point newPos); // pomeri na novu

    public abstract void setPoints(ArrayList<Point> points); // Postavi nove tacke
    public abstract ArrayList<Point> getPoints(); // Dohvati tacke

    // Ono sto pamtimo za svaku figuru
    public String saveFormat(){

        return  getClass().getName() + "(" + lineThick + ","+ lineColor.getRGB() + "):";
    }

    public void setNewCatch(Point catchPos) { // Izracunava relativne kordinate gde ga hvata mis
        deltaStartX = startPoint.x - catchPos.x;
        deltaStartY = startPoint.y - catchPos.y;
    }

    // Podesava da li je selektovan
    public void setSelected(boolean select){
        figureSelected = select;
    }

    // Podesi debljinu
    public void setLineThick(int thick){
        lineThick = thick;
    }

    // Podesi color
    public void setLineColor(Color color){
        lineColor = color;
    }
}