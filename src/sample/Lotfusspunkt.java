package sample;

import javafx.geometry.Point2D;

public class Lotfusspunkt {

    public Point2D lotfusspunkt;
    public double abstand;
    public double faktor;

    public Lotfusspunkt(Point2D lotfusspunkt, double abstand, double faktor) {

        this.lotfusspunkt = lotfusspunkt;
        this.abstand = abstand;
        this.faktor = faktor;

    }

    public static Lotfusspunkt getLotfusspunkt(Point2D marble, MRLine line) {

        double abstand;
        //Richtungsvektor der Linie
        Point2D richtungsVektor = line.getEndPoint().subtract(line.getStartPoint());
//        System.out.println("Richtungsvektor" + richtungsVektor);
        //Ortsvektor der Linie (Startpunkt)
        Point2D ortsVektor = new Point2D(line.berechnung.getStartX(), line.berechnung.getStartY());
//        System.out.println("Ortsvektor : " + ortsVektor);

        //Zwischenschritt zur Berechnung des Lotfußpunktes
        Point2D zwischenSchritt = ortsVektor.subtract(marble);
//        System.out.println("Zwischenschritt: " + zwischenSchritt);

        // Faktor = r
        // Wenn der Faktor r zwischen 0 und 1 liegt ist der Lotfußpunkt auf der angezeigten Linie
        double faktor = (-zwischenSchritt.dotProduct(richtungsVektor))/(richtungsVektor.dotProduct(richtungsVektor));
//        System.out.println("Faktor: " + faktor);

        // Startpunkt der Linie + (faktor * richtungsvektor) --> Lotfußpunkt
        Point2D lotfusspunkt = ortsVektor.add(richtungsVektor.multiply(faktor));

        // Abstand zwischen dem Lotfußpunkt und dem Mittelpunkt der Kugel
        // Magnitude berechnet die Länge
        abstand = lotfusspunkt.subtract(marble).magnitude();

        return new Lotfusspunkt(lotfusspunkt, abstand, faktor);
    }

//    public String toString() {
//        return "Abstand = " + abstand + "; Faktor = " + faktor + "; LFP = " + lotfusspunkt;
//    }
}
