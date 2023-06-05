package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;


public class Marble extends Object {

    Vector2D vecPos;
    Vector2D geschwindigkeit;
    Vector2D wind;

    Point2D nextVecPos;
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

    public void move(double zeit, Vector2D schwerkraft) {
        //Berechnung der Position
        vecPos.x += geschwindigkeit.x * zeit + 0.5 * Math.pow(zeit,2);
        vecPos.y += geschwindigkeit.y * zeit + 0.5 * schwerkraft.y * Math.pow(zeit,2);

        nextVecPos = new Point2D((vecPos.x + geschwindigkeit.x * zeit + 0.5 * Math.pow(zeit,2)),(vecPos.y + geschwindigkeit.y * zeit + 0.5 * schwerkraft.y * Math.pow(zeit,2)));

        //Berechnung der Geschwindigkeit
        geschwindigkeit.x += marble.wind.x * zeit;
        geschwindigkeit.y += schwerkraft.y * zeit;

        // die Position der Kugel in X-Richtung wird aktualisiert
        this.circle.setCenterX(vecPos.x * pixelPerMeter);
        // die Position der Kugel in Y-Richtung wird aktualisiert
        this.circle.setCenterY((800 / pixelPerMeter - vecPos.y) * pixelPerMeter);
    }

    public void checkAndHandleCollision(MRLine line) {

        Lotfusspunkt lotfusspunkt = Lotfusspunkt.getLotfusspunkt(new Point2D(vecPos.x,vecPos.y),line);
        /*System.out.println("Abstand: " + lotfusspunkt.abstand);
        System.out.println("Faktor: " + lotfusspunkt.faktor);*/


        // Wenn der Faktor kleiner 0 oder größer 1 ist, findet keine Kollision statt.
        if(lotfusspunkt.faktor > 1 || lotfusspunkt.faktor < 0 || lotfusspunkt.abstand > radius/pixelPerMeter) {
            /*System.out.println("no collision");*/
            return;
        } else {
            //System.out.println("Kollision");
            //Point2D abstandsVektor = lotfusspunkt.lotfusspunkt.subtract(vecPos.x,vecPos.y);
            MRLine abstandsLinie = new MRLine(new Point2D(vecPos.x,vecPos.y),lotfusspunkt.lotfusspunkt);
            Point2D naechstePosition = new Point2D(vecPos.x + geschwindigkeit.x, vecPos.y + geschwindigkeit.y);

            /*System.out.println("np" + naechstePosition);
            System.out.println("al" + abstandsLinie.representation);*/

            Lotfusspunkt hilfsLotfusspunkt = Lotfusspunkt.getLotfusspunkt(naechstePosition, abstandsLinie);

//            System.out.println(hilfsLotfusspunkt);

//            System.out.println("Kugel ("+ vecPos.x +":"+ vecPos.y +")");
//            System.out.println("HLP   ("+ hilfsLotfusspunkt.lotfusspunkt.getX() +":"+ hilfsLotfusspunkt.lotfusspunkt.getY() +")");
//            System.out.println("nP    ("+ naechstePosition.getX() +":"+ naechstePosition.getY() +")");

            /*System.out.println("Abstandslinie Start: " + abstandsLinie.representation.getStartX() + ":" + abstandsLinie.representation.getStartY());
            System.out.println("Abstandslinie Start: " + abstandsLinie.representation.getEndX() + ":" + abstandsLinie.representation.getEndY());
            System.out.println("Hilfslot: " + hilfsLotfusspunkt.lotfusspunkt.getY());
            System.out.println("nächste Pos.: " + naechstePosition.getY());*/

            Point2D orthogonal = new Point2D(vecPos.x,vecPos.y).subtract(hilfsLotfusspunkt.lotfusspunkt);
            Point2D parallel = naechstePosition.subtract(hilfsLotfusspunkt.lotfusspunkt);

//            System.out.println("orthogonal: " + orthogonal);
//            System.out.println("parallel: " + parallel);



            Point2D neueGeschwindigkeit = orthogonal.add(parallel);

//            System.out.println("Geschwindigkeit vorher: (" + geschwindigkeit.x + ":" + geschwindigkeit.y + ")");

            geschwindigkeit.x = neueGeschwindigkeit.getX();
            geschwindigkeit.y = neueGeschwindigkeit.getY();

//            System.out.println("Geschwindigkeit nachher: (" + geschwindigkeit.x + ":" + geschwindigkeit.y + ")");
        }
    }


}
