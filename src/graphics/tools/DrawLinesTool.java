package graphics.tools;

/**
 * Created by Matija on 16 Jun 17.
 */
public class DrawLinesTool /*extends Tool*/ {
/*
    private Lines izlomljena ;
    Point newPoint;

    public DrawLinesTool(){
        super();
    }

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e) {
        newPoint = e.getPoint();

        if(izlomljena == null) { // nova linija
            izlomljena = new Lines(new ArrayList<>(), (int) MainForm.lineThick.getSelectedItem() , MainForm.lineColor.getSelectedColor());
            izlomljena.addPoint(newPoint); // Prva tacka

            WorkPanel.drawing.addFigure(izlomljena); // dodajemo figuru
        }
        else {
            izlomljena.addPoint(newPoint);

            // Double click
            if (e.getClickCount() == 2) {
                // Prelazimo na novu
                resetTool();
            }
        }
    }

    public void resetTool(){
        izlomljena = null;
    }

    @Override
    public void mouseDrag(MouseEvent e) {}


    @Override
    public void undo(){
        super.undo();
        resetTool();
    }

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
