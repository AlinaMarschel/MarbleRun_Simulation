package sample;


import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Obstacle extends Object {

    Rotate rotate = new Rotate();

    Line line = new Line();

    float positionX;
    float positionY;
    double angleOfBox;

    float edgeTopLeft;
    float edgeTopRight;
    float edgeBottomLeft;
    float edgeBottomRight;


    float width;
    float height;

    Color color;
    Rectangle box;

    public Obstacle(float positionX, float positionY, float width, float height, double angleOfBox) {

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

//        this.edgeTopLeft = (float) this.box.getBoundsInParent().getMinX();
//        this.edgeTopRight = (float) this.box.getBoundsInParent().getMaxX();
//        this.edgeBottomLeft = (float) this.box.getBoundsInParent().getMinY() + this.height;
//        this.edgeBottomRight = (float) this.box.getBoundsInParent().getMaxY();

        this.edgeTopLeft = (float) this.box.getBoundsInParent().getMinX();
        this.edgeTopRight = (float) this.box.getBoundsInParent().getMaxX();
        this.edgeBottomLeft = (float) this.box.getBoundsInParent().getMinY() + this.height;
        this.edgeBottomRight = (float) this.box.getBoundsInParent().getMaxY();

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
