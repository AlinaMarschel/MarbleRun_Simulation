package sample;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.util.ArrayList;


public class MRLine {
    public static ArrayList<MRLine> lines = new ArrayList<>();
    public Line representation;
    private double pixelPerMeter = 100;

    public MRLine(double startX, double startY, double endX, double endY) {
        representation = new Line(startX, startY, endX, endY);
        MRLine.lines.add(this);
    }

    public Point2D getStartPoint() {
        Point2D startPoint = new Point2D(representation.getStartX() / pixelPerMeter, representation.getStartY() / pixelPerMeter);
        return startPoint;
    }

    public Point2D getEndPoint() {
        Point2D endPoint = new Point2D(representation.getEndX() / pixelPerMeter, representation.getEndY() / pixelPerMeter);
        return endPoint;
    }

}

