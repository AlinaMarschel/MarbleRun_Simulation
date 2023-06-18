package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Point2D;

import java.util.ArrayList;


public class Marble extends Object {

    Vector2D vecPos;
    Vector2D geschwindigkeit;
    Vector2D wind;

    Point2D nextVecPos;
    double radius; // m
    double pixelPerMeter = 100;
    MRLine kollisionslinie;
    double schwellenwertRollen = 3;
    double reibungskoeffizient = 0.011; // Autoreifen auf Asphalt
    boolean isRolling;
    Color color;
    Circle circle;

    public static ArrayList<Marble> marbles = new ArrayList<>();
    public ArrayList<Marble> kollisionsmarble = new ArrayList<>();

    public Marble(Vector2D vecPos, double radius, Color color, Vector2D geschwindigkeit, Vector2D wind)
    {
        this.vecPos = vecPos;

        this.radius = radius;
        this.color = color;

        this.geschwindigkeit = geschwindigkeit;
        this.wind = wind;

        this.circle = new Circle(this.vecPos.x, this.vecPos.y, this.radius, this.color);
        this.boundingBox = this.circle.getBoundsInParent();

        Marble.marbles.add(this);
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
        geschwindigkeit.x += wind.x * zeit;
        geschwindigkeit.y += schwerkraft.y * zeit;

        // die Position der Kugel in X-Richtung wird aktualisiert
        this.circle.setCenterX(vecPos.x * pixelPerMeter);
        // die Position der Kugel in Y-Richtung wird aktualisiert
        this.circle.setCenterY((800 / pixelPerMeter - vecPos.y) * pixelPerMeter);
    }

    public double getMarbleAngle() {

        double ankatethe = vecPos.x;
        double gegenkathete = vecPos.y;
        double hypothenuse = Math.sqrt(Math.pow(ankatethe,2) + Math.pow(gegenkathete,2));

        double winkel = gegenkathete/hypothenuse;
        double richtungsWinkel = Math.toDegrees(Math.acos(winkel));

        return richtungsWinkel;
    }

    public void roll(double zeit, Vector2D schwerkraft) {

        double alpha = kollisionslinie.getAngle();

        double hypothenuse = Math.abs(schwerkraft.y);
        double gegenkathete = Math.sin(Math.toRadians(alpha)) * hypothenuse;
        double ankathete = Math.cos(Math.toRadians(alpha)) * hypothenuse;

        double neueAnkatheteX = Math.cos(Math.toRadians(alpha)) * gegenkathete;
        double neueGegenkatheteY = Math.sqrt(Math.pow(gegenkathete,2) - Math.pow(neueAnkatheteX,2));

        Point2D hangabtriebskraft = new Point2D(neueAnkatheteX,neueGegenkatheteY);

        double reibungsBeschleunigung = ankathete * reibungskoeffizient;

        // Reibung
        double reibungX = Math.cos(Math.toRadians(alpha)) * reibungsBeschleunigung;
        double reibungY = Math.sin(Math.toRadians(alpha)) * reibungsBeschleunigung;


        // Wind
        double ankatheteWind = Math.cos(Math.toRadians(alpha)) * wind.x;
        //double gegenkatheteWind = Math.sin(Math.toRadians(alpha)) * wind.x;

        double windX = Math.cos(Math.toRadians(alpha)) * ankatheteWind;
        double windY = Math.sin(Math.toRadians(alpha)) * ankatheteWind;

        Point2D windBeschleunigung = new Point2D(windX,windY);

        // nach rechts oben
        if(vecPos.x <= nextVecPos.getX() && vecPos.y <= nextVecPos.getY()) {
            // Reibung wirkt nach links unten
            reibungX = 0 - Math.abs(reibungX);
            reibungY = 0 - Math.abs(reibungY);
        }
        // nach rechts unten
        if(vecPos.x <= nextVecPos.getX() && vecPos.y > nextVecPos.getY()) {
            // Reibung wirkt nach links oben
            reibungX = 0 - Math.abs(reibungX);
            reibungY = Math.abs(reibungY);
        }
        // nach links oben
        if(vecPos.x > nextVecPos.getX() && vecPos.y <= nextVecPos.getY()) {
            // Reibung wirkt nach rechts unten
            reibungX = Math.abs(reibungX);
            reibungY = 0 - Math.abs(reibungY);
        }
        // nach links unten
        if(vecPos.x > nextVecPos.getX() && vecPos.y > nextVecPos.getY()) {
            // Reibung wirkt nach rechts oben
            reibungX = Math.abs(reibungX);
            reibungY = Math.abs(reibungY);
        }

        Point2D reibung = new Point2D(reibungX,reibungY);
        Point2D gesamtBeschleunigung = hangabtriebskraft.add(reibung).add(windBeschleunigung);

        vecPos.x += geschwindigkeit.x * zeit + 0.5 * gesamtBeschleunigung.getX() * Math.pow(zeit,2);
        vecPos.y += geschwindigkeit.y * zeit + 0.5 * gesamtBeschleunigung.getY() * Math.pow(zeit,2);


        // Kugel fällt nicht durch horizontale Linien
        if(kollisionslinie.getStartPoint().getY() == kollisionslinie.getEndPoint().getY()) {
            vecPos.y = kollisionslinie.getStartPoint().getY() + radius / 100;
        }

        // Kugel bleibt bei geringer Geschwindigkeit liegen
        if (geschwindigkeit.x <= 0.03) {
            geschwindigkeit.x = 0;
        }
        if (geschwindigkeit.y <= 0.03) {
            geschwindigkeit.y = 0;
        }

        Lotfusspunkt lotfusspunkt = Lotfusspunkt.getLotfusspunkt(new Point2D(vecPos.x, vecPos.y),kollisionslinie);

        if(lotfusspunkt.faktor > 1 || lotfusspunkt.faktor < 0 || lotfusspunkt.abstand > radius/pixelPerMeter) {
            isRolling = false;
            return;
        }


        //Berechnung der neuen Geschwindigkeit
        geschwindigkeit.x += gesamtBeschleunigung.getX() * zeit;
        geschwindigkeit.y += gesamtBeschleunigung.getY() * zeit;

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
        // Todo: Schwellenwert anpassen, damit die Kugel nicht durch die Ebene glitscht. hinter px pro meter
        if(lotfusspunkt.faktor > 1 || lotfusspunkt.faktor < 0 || lotfusspunkt.abstand > radius/pixelPerMeter) {
            /*System.out.println("no collision");*/
            return;
        } else {
            Lotfusspunkt lotfusspunktNextFrame = Lotfusspunkt.getLotfusspunkt(new Point2D(nextVecPos.getX(),nextVecPos.getY()),line);

            if(lotfusspunkt.abstand < lotfusspunktNextFrame.abstand) {
                return;
            }

            if(Math.abs(getMarbleAngle() - line.getAngle()) <= schwellenwertRollen || geschwindigkeit.getLength() <= 0.04) {
                isRolling = true;
                kollisionslinie = line;
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

    public void checkAndHandleCollision(Marble marble) {

        Vector2D beruehrungsnormale = Vector2D.subtract(marble.vecPos,vecPos);
        double abstand = beruehrungsnormale.getLength();

        if (abstand <= marble.radius/pixelPerMeter + radius/pixelPerMeter) {

            Point2D beruehrungsnormaleNextFrame = marble.nextVecPos.subtract(nextVecPos);
            double abstandNextFrame = beruehrungsnormaleNextFrame.magnitude();

            if(abstand < abstandNextFrame) {
               return;
            }
            marble.kollisionsmarble.add(this);
            kollisionsmarble.add(marble);

            MRLine beruehrungslinie = new MRLine(new Point2D(vecPos.x,vecPos.y), new Point2D(marble.vecPos.x, marble.vecPos.y));

            // A = Position der Kugel
            // B = Zentrum der Kugel + Geschwindigkeit
            Point2D nextVecPosKugel = new Point2D(vecPos.x + geschwindigkeit.x, vecPos.y + geschwindigkeit.y);
            Point2D nextVecPosKollisionsKugel = new Point2D(marble.vecPos.x + marble.geschwindigkeit.x, marble.vecPos.y + marble.geschwindigkeit.y);

            // C = Lotfußpunkt
            Lotfusspunkt lotfusspunktKugel = Lotfusspunkt.getLotfusspunkt(nextVecPosKugel, beruehrungslinie);
            Lotfusspunkt lotfusspunktKollisionsKugel = Lotfusspunkt.getLotfusspunkt(nextVecPosKollisionsKugel, beruehrungslinie);

            // AB neu = CB + CA
            // Vektor AC wurde umgekehrt in CA, weil dieser parallel zur Berührnormalen ist
            Point2D neueGeschwindigkeit = nextVecPosKugel.subtract(lotfusspunktKugel.lotfusspunkt).add(new Point2D(vecPos.x, vecPos.y).subtract(lotfusspunktKugel.lotfusspunkt));
            geschwindigkeit.x = neueGeschwindigkeit.getX();
            geschwindigkeit.y = neueGeschwindigkeit.getY();

            // AB neu = CB + CA
            Point2D neueGeschwindigkeitKollisionsKugel = nextVecPosKollisionsKugel.subtract(lotfusspunktKollisionsKugel.lotfusspunkt).add(new Point2D(marble.vecPos.x, marble.vecPos.y).subtract(lotfusspunktKollisionsKugel.lotfusspunkt));
            marble.geschwindigkeit.x = neueGeschwindigkeitKollisionsKugel.getX();
            marble.geschwindigkeit.y = neueGeschwindigkeitKollisionsKugel.getY();

        }

    }


}
