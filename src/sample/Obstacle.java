package sample;


import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Object {

    double positionX;
    double positionY;
    double angleOfBox;

    double width;
    double height;

    double cornerTopLeft;
    double cornerTopRight;
    double cornerBottomLeft;
    double cornerBottomRight;

    Color color;
    Rectangle box;
    Line line;

    public Obstacle(double positionX, double positionY, double width, double height, double angleOfBox) {

        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
        this.angleOfBox = angleOfBox;

        this.color = Color.ALICEBLUE;
        isStatic = true;

        this.box = new Rectangle(this.positionX, this.positionY, this.width, this.height);
        this.box.setRotate(this.angleOfBox);

        this.cornerTopLeft = (double) this.box.getBoundsInParent().getMinX();
        this.cornerTopRight = (double) this.box.getBoundsInParent().getMaxX();
        this.cornerBottomLeft = (double) this.box.getBoundsInParent().getMinY() + this.height;
        this.cornerBottomRight = (double) this.box.getBoundsInParent().getMaxY();

        double x1 = this.box.getBoundsInParent().getMinX();
        double x2 = x1 + Math.sin(Math.toRadians(this.angleOfBox)) * this.height; // Länge der Gegenkathete
        double x4 = this.box.getBoundsInParent().getMaxX();
        double x3 = x4 - (x2 -x1);

        double y1 = this.box.getBoundsInParent().getMinY();
        double y2 = y1 + Math.cos(Math.toRadians(this.angleOfBox)) * this.height; // Länge der Ankathete
        double y4 = this.box.getBoundsInParent().getMaxY();
        double y3 = y4 - (y2 - y1);

        this.line = new Line(x2, y1, x4, y3);

        this.boundingBox = this.box.getBoundsInParent();
        this.box.setFill(this.color);

//        System.out.println(this.box.getBoundsInParent());
//        System.out.println("Oben links: " + this.cornerTopLeft + " Oben rechts: " + this.cornerTopRight + " Unten links: " + this.cornerBottomLeft + "Oben rechts: " + this.cornerBottomRight);
//        System.out.println(this.line);
        System.out.println(Math.cos(Math.toRadians(20)) * 10);
    }
}
