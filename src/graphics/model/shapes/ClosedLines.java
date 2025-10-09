package graphics.model.shapes;

/**
 * Created by Matija on 16 Jun 17.
 */
public class ClosedLines /*extends Lines*/ {
    /*
    private boolean finished;

    public ClosedLines(ArrayList<Point> points, int thick, Color color) {
        super(points, thick, color);

        finished = false;
    }


    // Ozacava finished flag da se iscrta poslednja linija
    public void setFinished(boolean newFinished){
        finished = newFinished;
    }
    public boolean isFinished(){ return finished; }

    @Override
    public void iscrtajLinije(Graphics2D g){ // Samo iscrtavanje linija
        super.iscrtajLinije(g);
        int tacaka = tacke.size();

        if(finished) { // Spajamo poslednju i prvu ako je gotovo
            g.drawLine(startPoint.x, startPoint.y, tacke.get(tacaka - 1).x, tacke.get(tacaka - 1).y);

        }
    }

    @Override
    public boolean selected(Point coord) {
        // Ako su dosadasnje linije selektovane
        if(super.selected(coord))
            return true;

        int tacaka = tacke.size();

        Line tempLine = new Line(
            new Point(tacke.get(0).x,  tacke.get(0). y),
            new Point(tacke.get(tacaka - 1).x,      tacke.get(tacaka - 1).y),
            lineThick,
            lineColor
        );

        if(tempLine.selected(coord)) // poslednja duz selektovana
            return true;

        return false;
    }
     */
}
