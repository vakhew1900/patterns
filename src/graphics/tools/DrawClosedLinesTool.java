package graphics.tools;

/**
 * Created by Matija on 16 Jun 17.
 */
public class DrawClosedLinesTool /*extends Tool*/ {
/*
    private ClosedLines zatvorenaLinija ;
    Point newPoint;
    public DrawClosedLinesTool(){
        super();
    }

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e) {
        newPoint = e.getPoint();

        if(zatvorenaLinija == null) { // nova linija
            zatvorenaLinija = new ClosedLines(new ArrayList<>(), (int) MainForm.lineThick.getSelectedItem() , MainForm.lineColor.getSelectedColor());
            zatvorenaLinija.addPoint(newPoint); // Prva tacka

            WorkPanel.drawing.addFigure(zatvorenaLinija); // dodajemo figuru
        }
        else {
            zatvorenaLinija.addPoint(newPoint);

            // Double click
            if (e.getClickCount() == 2) {
                // Prelazimo na novu
                zatvorenaLinija.setFinished(true); // Oznacavamo da se iscrta na kraju
                resetTool();
            }
        }
    }

    @Override
    public void undo(){
        zatvorenaLinija.setFinished(true);
        super.undo();
        resetTool();
    }

    public void resetTool(){
        zatvorenaLinija = null;
    }

    @Override
    public void mouseDrag(MouseEvent e) {}


    @Override
    public void mouseMove(MouseEvent e){
        Point currPoint = e.getPoint();

        StringBuilder rightLabelTxt = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

        if(newPoint != null)
            rightLabelTxt.append("Distance: " + df.format(Point.distance(newPoint.x, newPoint.y,  currPoint.x, currPoint.y)));

        rightLabelTxt.append( " X: " + currPoint.x + " Y: " + currPoint.y);

        MainForm.rightLabel.setText(rightLabelTxt.toString());
    }
 */
}
