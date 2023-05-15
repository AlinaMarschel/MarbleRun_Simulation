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

    double edgeTopLeft;
    double edgeTopRight;
    double edgeBottomLeft;
    double edgeBottomRight;

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
        //this.rotate.setAngle(this.angleOfBox);
        //this.box.getTransforms().addAll(rotate);

        this.edgeTopLeft = (double) this.box.getBoundsInParent().getMinX();
        this.edgeTopRight = (double) this.box.getBoundsInParent().getMaxX();
        this.edgeBottomLeft = (double) this.box.getBoundsInParent().getMinY() + this.height;
        this.edgeBottomRight = (double) this.box.getBoundsInParent().getMaxY();

        this.line = new Line(this.edgeTopLeft, this.edgeBottomLeft - this.box.getHeight(), this.edgeTopRight, this.edgeBottomRight - this.box.getHeight());

        this.boundingBox = this.box.getBoundsInParent();
        this.box.setFill(this.color);

        System.out.println(this.box.getBoundsInParent());
        System.out.println("Oben links: " + this.edgeTopLeft + " Oben rechts: " + this.edgeTopRight + " Unten links: " + this.edgeBottomLeft + "Oben rechts: " + this.edgeBottomRight);
        System.out.println(this.line);
    }

//    public void update() {
//        this.boundingBox = this.box.getBoundsInParent();
//    }

}
