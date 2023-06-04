package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;


public class Marble extends Object {

    Vector2D vecPos;
    Vector2D geschwindigkeit;
    Vector2D wind;

    double radius; // m
    double pixelPerMeter = 100;
    Color color;
    Circle circle;

    public static Marble marble;
    public Marble(Vector2D vecPos, double radius, Color color, Vector2D geschwindigkeit, Vector2D wind)
    {
        this.vecPos = vecPos;

        this.radius = radius;
        this.color = color;

        this.geschwindigkeit = geschwindigkeit;
        this.wind = wind;

        this.circle = new Circle(this.vecPos.x, this.vecPos.y, this.radius, this.color);
        this.boundingBox = this.circle.getBoundsInParent();

        Marble.marble = this;
    }

    public String toString()
    {
        return "Position: " + this.vecPos.x + "|" + this.vecPos.y + " Radius: " + this.radius;
    }


    // Marble wird jeden Frame aktualisiert und die Werte aus der Simulation werden hier übergeben
    public void update(Vector2D vecNewPosition, Vector2D vecNeueGeschwindigkeit)
    {
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

    public void move(double zeit, Vector2D schwerkraft) {
        double pixelPerMeter = 100;

        //Berechnung der Position
        vecPos.x += geschwindigkeit.x * zeit + 0.5 * Math.pow(zeit,2);
        vecPos.y += geschwindigkeit.y * zeit + 0.5 * schwerkraft.y * Math.pow(zeit,2);

        //Berechnung der Geschwindigkeit
        geschwindigkeit.x += marble.wind.x * zeit;
        geschwindigkeit.y += schwerkraft.y * zeit;

        // die Position der Kugel in X-Richtung wird aktualisiert
        this.circle.setCenterX(vecPos.x * pixelPerMeter);
        // die Position der Kugel in Y-Richtung wird aktualisiert
        this.circle.setCenterY((800 / pixelPerMeter - vecPos.y) * pixelPerMeter);
    }

    /*
     * Berechnung des Abstandes von der Kugel zur Linie
     * Wenn der Abstand größer als der Radius ist --> Keine Kollision
     * */

    public void checkCollision(MRLine line) {

        double abstand;

        Point2D richtungsVektor = line.getEndPoint().subtract(line.getStartPoint());
        Point2D ortsVektor = line.getStartPoint();

        Point2D zwischenSchritt = ortsVektor.subtract(vecPos.x, vecPos.y);

        // Lotfußpunktverfahren Zwischenschritt
        double faktor = (-zwischenSchritt.dotProduct(richtungsVektor))/(richtungsVektor.dotProduct(richtungsVektor));

        // Wenn der Faktor kleiner 0 oder größer 1 ist, findet keine Kollision statt.
        if(faktor > 1 || faktor < 0) {
            return;
        }

        //startpunkt + (faktor * richtungsvektor) --> Lotfußpunkt
        Point2D lotfusspunkt = ortsVektor.add(richtungsVektor.multiply(faktor));

        abstand = lotfusspunkt.subtract(vecPos.x, vecPos.y).magnitude();

//        System.out.println("Faktor: " + faktor);
//        System.out.println("Abstand: " + abstand);


        if(abstand > radius/pixelPerMeter) {
            return;
        } else {
            System.out.println("Kollision");
        }
    }

}
