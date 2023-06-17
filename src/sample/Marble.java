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
    MRLine kollisionslinie = new MRLine(
            0,0,1000,0
    );
    double schwellenwertRollen = 3;
    double reibungskoeffizient = 0.011; // Autoreifen auf Asphalt
    boolean isRolling;
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
        vecPos.x += geschwindigkeit.x * zeit + 0.5 * wind.x * Math.pow(zeit,2);
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

    public double getMarbleAngle() {

        double ankatethe = marble.vecPos.x;
        double gegenkathete = marble.vecPos.y;
        double hypothenuse = Math.sqrt(Math.pow(ankatethe,2) + Math.pow(gegenkathete,2));

        double winkel = gegenkathete/hypothenuse;
        double richtungsWinkel = Math.toDegrees(Math.acos(winkel));

        return richtungsWinkel;
    }

    public void roll(double zeit, Vector2D schwerkraft) {
        System.out.println("isch rolle");

        double alpha = kollisionslinie.getAngle();
        System.out.println("Alpha = " + alpha);

        double hypothenuse = schwerkraft.y;
        double gegenkathete = Math.sin(Math.toRadians(alpha)) * hypothenuse;
        double ankathete = Math.cos(Math.toRadians(alpha)) * hypothenuse;

        double neueAnkatheteX = Math.cos(Math.toRadians(alpha)) * gegenkathete;
        double neueGegenkatheteY = Math.sqrt(Math.pow(gegenkathete,2) - Math.pow(neueAnkatheteX,2));

        Point2D hangabtriebskraft = new Point2D(neueAnkatheteX,neueGegenkatheteY);

        //System.out.println(hangabtriebskraft);
        double reibungsBeschleunigung = ankathete * reibungskoeffizient;
        System.out.println("a_N * muh = " + reibungsBeschleunigung);

        if(reibungsBeschleunigung >= gegenkathete && geschwindigkeit.getLength() == 0) {
            System.out.println("Liegen Bleiben! Hände hoch");
        }

        vecPos.x += geschwindigkeit.x * zeit + 0.5 * hangabtriebskraft.getX() * Math.pow(zeit,2);
        vecPos.y += geschwindigkeit.y * zeit + 0.5 * hangabtriebskraft.getY() * Math.pow(zeit,2);

        //Berechnung der neuen Geschwindigkeit
        geschwindigkeit.x += (hangabtriebskraft.getX() + reibungsBeschleunigung) * zeit;
        geschwindigkeit.y += (hangabtriebskraft.getY() + reibungsBeschleunigung) * zeit;

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

            Lotfusspunkt lotfusspunktNextFrame = Lotfusspunkt.getLotfusspunkt(new Point2D(nextVecPos.getX(),nextVecPos.getY()),line);

            if(lotfusspunkt.abstand < lotfusspunktNextFrame.abstand) {
                return;
            }

            if(Math.abs(getMarbleAngle() - line.getAngle()) <= schwellenwertRollen) {
                isRolling = true;
                kollisionslinie = line;
                /*System.out.println("Rollen");*/
                return;
            }

            MRLine abstandsLinie = new MRLine(new Point2D(vecPos.x,vecPos.y),lotfusspunkt.lotfusspunkt);
            Point2D naechstePosition = new Point2D(vecPos.x + geschwindigkeit.x, vecPos.y + geschwindigkeit.y);

            Lotfusspunkt hilfsLotfusspunkt = Lotfusspunkt.getLotfusspunkt(naechstePosition, abstandsLinie);

            Point2D orthogonal = new Point2D(vecPos.x,vecPos.y).subtract(hilfsLotfusspunkt.lotfusspunkt);
            Point2D parallel = naechstePosition.subtract(hilfsLotfusspunkt.lotfusspunkt);

            Point2D neueGeschwindigkeit = orthogonal.add(parallel);

//            System.out.println("Geschwindigkeit vorher: (" + geschwindigkeit.x + ":" + geschwindigkeit.y + ")");

            geschwindigkeit.x = neueGeschwindigkeit.getX();
            geschwindigkeit.y = neueGeschwindigkeit.getY();

//            System.out.println("Geschwindigkeit nachher: (" + geschwindigkeit.x + ":" + geschwindigkeit.y + ")");
        }
    }


}
