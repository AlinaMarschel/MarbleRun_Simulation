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
        darstellung = new Line(startX, 800 - startY, endX, 800 - endY);
        berechnung = new Line(startX / pixelPerMeter, startY / pixelPerMeter, endX / pixelPerMeter, endY / pixelPerMeter);
        MRLine.lines.add(this);
    }

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

    public double getAngle() {
        double hoehe = Math.abs(darstellung.getEndY() - darstellung.getStartY());
        double breite = Math.abs(darstellung.getEndX() - darstellung.getStartX());
        double laenge = Math.sqrt(Math.pow(hoehe,2) + Math.pow(breite,2));

        double winkel = hoehe/laenge;
        double winkelEbene = Math.toDegrees(Math.sin(winkel));

        return winkelEbene;
    }

}

