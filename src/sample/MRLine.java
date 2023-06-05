package sample;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.util.ArrayList;


public class MRLine {
    public static ArrayList<MRLine> lines = new ArrayList<>();
    public Line darstellung;

    public Line berechnung;
    private double pixelPerMeter = 100;

    public MRLine(double startX, double startY, double endX, double endY) {
        darstellung = new Line(startX, startY, endX, endY);
        berechnung = new Line(startX / pixelPerMeter, 8 - startY / pixelPerMeter, endX / pixelPerMeter, 8 - endY / pixelPerMeter);
        MRLine.lines.add(this);
    }

//    public MRLine(double startX, double startY, double endX, double endY, String name) {
//        darstellung = new Line(startX, startY, endX, endY);
//        berechnung = new Line(startX / pixelPerMeter, startY / pixelPerMeter, endX / pixelPerMeter, endY / pixelPerMeter);
//        this.name = name;
//        MRLine.lines.add(this);
//    }

    public MRLine(Point2D startvektor, Point2D endvektor) {
        berechnung = new Line(startvektor.getX(), startvektor.getY(), endvektor.getX(), endvektor.getY());
    };

    public Point2D getStartPoint() {
        Point2D startPoint = new Point2D(berechnung.getStartX(), berechnung.getStartY() );
        return startPoint;
    }

    public Point2D getEndPoint() {
        Point2D endPoint = new Point2D(berechnung.getEndX(), berechnung.getEndY());
        return endPoint;
    }

}

