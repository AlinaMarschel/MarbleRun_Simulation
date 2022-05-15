package sample;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Object {

    float positionX;
    float positionY;
    double angleOfBox;

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
        this.boundingBox = this.box.getBoundsInParent();
        this.box.setFill(this.color);

    }

//    public void update() {
//        this.boundingBox = this.box.getBoundsInParent();
//    }

}
