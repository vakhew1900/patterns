package graphics;

import graphics.shapes.Rectangle;
import graphics.shapes.Shape;
import graphics.shapes.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Matija on 12 Jun 17.
 */
public class Drawing {
    private final ArrayList<Shape> lines;
    private BufferedWriter writeToFile;
    private BufferedReader loadToFile;

    private final static Pattern pointFormat = Pattern.compile("([\\-0-9]+),([\\-0-9]+)");
    private final static Pattern lineFormat = Pattern.compile("([^\\(]+)\\(([\\-0-9]+),([\\-0-9]+)\\):((([\\-0-9]+),([\\-0-9]+);){2,})");

    public Stack<Shape> undoFigures = new Stack<>();


    public static Map<String, Shape> mapTypes = new HashMap<>();

    static{
        // Svi tipovi
        mapTypes.put("grafika.elementi.Duz", new Line(null, 1, Color.BLACK));
        //mapTypes.put("grafika.elementi.Linije", new Lines(null, 1, Color.BLACK));
        mapTypes.put("grafika.elementi.Pravugaonik", new Rectangle(null, 1, Color.BLACK));
        //mapTypes.put("grafika.elementi.Zatvorene", new ClosedLines(null, 1, Color.BLACK));
    }

    public Drawing(Shape... figure){
        lines = new ArrayList<>();

        Collections.addAll(lines, figure);
    }

    // Zapamti u fajl
    public void saveFile(File toFile) throws RuntimeException {
        try {
            writeToFile = new BufferedWriter(new FileWriter(toFile));

            for (Shape figura : lines) {
                writeToFile.write(figura.saveFormat());
                writeToFile.write("\r\n");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                writeToFile.close();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Dodavanje elementi
    public void addFigure(Shape newFigure){
        lines.add(newFigure);
    }

    // Selektovanje figure na osnovu pointa
    public Shape selectFigure(Point coord){

        int indexFigure = lines.size() - 1;

        while(indexFigure >= 0){
            Shape tempFigura = lines.get(indexFigure);

            if(tempFigura.selected(coord))
                return tempFigura;

            indexFigure --;
        }

        return null;
    }

    // Paint svih figura
    public void paintAll(Graphics g){
        Graphics2D graph =  (Graphics2D)g;
        for(Shape toPaint : lines){
            toPaint.paint(graph);
        }
    }

    // Dohvatanje poslednje
    public Shape popLast(){
        int indexFigure = lines.size() - 1;

        if(indexFigure >= 0){
            Shape toReturn = lines.get(indexFigure);
            lines.remove(indexFigure);
            return toReturn;
        }

        return null;
    }

    // Delete jedne figure
    public void deleteFigura(Shape toDelete){
        int indexFigure = lines.size() - 1;

        // Pronalazimo
        while (indexFigure >= 0 && lines.get(indexFigure) != toDelete){
            indexFigure --;
        }

        if(indexFigure != -1) { // Ako smo pronasli
            // Ubacujemo na stek
            //undoFigures.push(lines.get(indexFigure));

            // Brisemo je
            lines.remove(indexFigure);
        }
    }

    // Load file-a
    public void loadFile(File fileToLoad){
        deleteAll(); // Brisemo sve prethodne

        try {
            loadToFile = new BufferedReader(new FileReader(fileToLoad));

            // citamo sve linije
            String line;

            while((line = loadToFile.readLine()) != null){
                Matcher lineMatcher = lineFormat.matcher(line);

                if(lineMatcher.matches()){

                    String className = lineMatcher.group(1);

                    int lineThick = Integer.parseInt(lineMatcher.group(2));
                    Color lineColor = new Color(Integer.parseInt(lineMatcher.group(3)));

                    String tacke = lineMatcher.group(4);

                    Matcher pointMatcher = pointFormat.matcher(tacke);
                    System.out.println(tacke);

                    ArrayList<Point> pointsForFigura = new ArrayList<>();

                    while(pointMatcher.find()){ // Za svaku tacku
                        int x = Integer.parseInt(pointMatcher.group(1));
                        int y = Integer.parseInt(pointMatcher.group(2));
                        pointsForFigura.add(new Point(x,y)); // Ubacujemo u listu tacaka
                    }

                    Shape newFigure;
                    switch(className){
                        default:
                        case "grafika.elementi.Duz":
                            newFigure = new Line(pointsForFigura, lineThick,lineColor);
                            break;
                        case "grafika.elementi.Pravugaonik":
                            newFigure = new Rectangle(pointsForFigura, lineThick, lineColor);
                            break;
                        /*
                        case "grafika.elementi.Linije":
                            newFigure = new Lines(pointsForFigura, lineThick, lineColor);
                            break;
                        case "grafika.elementi.Zatvorene":
                            newFigure = new ClosedLines(pointsForFigura, lineThick, lineColor);
                            break;
                         */
                    }

                    // Ubaci novu figuru
                    addFigure(newFigure);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                loadToFile.close();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // Obrisi sve
    public void deleteAll(){
        lines.clear();
    }

    // undo
    public void undo(){

        // Poslednja
        if(lines.size() > 0) {
            Shape lastFigura = lines.get(lines.size() - 1);

            /*
            if(lastFigura instanceof ClosedLines){
                ((ClosedLines) lastFigura).setFinished(true); // onaj koji  se prebacuje na stack zavrsava se
            }

            // Resetujemo alate za koje radimo undo
            if(WorkPanel.selectedTool instanceof DrawClosedLinesTool)
                ((DrawClosedLinesTool) WorkPanel.selectedTool).resetTool();
            if(WorkPanel.selectedTool instanceof DrawLinesTool)
                ((DrawLinesTool)WorkPanel.selectedTool).resetTool();
            */

            undoFigures.push(lastFigura);

            // Uklanjamo je
            lines.remove(lines.size() - 1);
        }
    }

    public void redo(){
        // Ako nije prazan
        if(!undoFigures.empty()){
            // Popujemo
            Shape redoFigure = undoFigures.pop();
            // Dodajemo novu
            addFigure(redoFigure);
        }
    }
}
