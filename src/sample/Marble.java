package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Marble extends Object {

    Vector2D vecPos;
    Vector2D geschwindigkeit;
    Vector2D wind;

    double radius; // m
    Color color;
    Circle circle;


    public Marble(Vector2D vecPos, double radius, Color color, Vector2D geschwindigkeit, Vector2D wind)
    {
        this.vecPos = vecPos;

        this.radius = radius;
        this.color = color;

        this.geschwindigkeit = geschwindigkeit;
        this.wind = wind;

        this.circle = new Circle(this.vecPos.x, this.vecPos.y, this.radius, this.color);
        this.boundingBox = this.circle.getBoundsInParent();
    }

    public String toString()
    {
        return "Position: " + this.vecPos.x + "|" + this.vecPos.y + " Radius: " + this.radius;
    }


    // Marble wird jeden Frame aktualisiert und die Werte aus der Simulation werden hier übergeben
    public void update(Vector2D vecNewPosition, Vector2D vecNeueGeschwindigkeit)
    {
        double pixelPerMeter = 100;
//
//        Vector2D vecPosToMeter = new Vector2D(vecNewPosition.getDivided(pixelPerMeter));
        this.vecPos = vecNewPosition;
        // Geschwindigkeit in X und Y Richtung wird aktualisiert
//        Vector2D vecGeschwindigkeitToMeter = new Vector2D(vecNeueGeschwindigkeit.getDivided(pixelPerMeter));
        this.geschwindigkeit = vecNeueGeschwindigkeit;
        // die Position der Kugel in X-Richtung wird aktualisiert
        this.circle.setCenterX(vecPos.x * pixelPerMeter);
        // die Position der Kugel in Y-Richtung wird aktualisiert
        this.circle.setCenterY((800 / pixelPerMeter - vecPos.y) * pixelPerMeter);

        // Hier wird die Boundingbox der Kugel aktualisiert
        this.boundingBox = this.circle.getBoundsInParent();
    }

}
