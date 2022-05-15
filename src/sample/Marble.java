package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Marble extends Object {

    float positionX; // m
    float positionY; // m

    Color color;

    float radius; // m

    float speedX; // m/s
    float speedY; // m/s

    float startSpeed; // m/s

    double acceleration; // m/s²

    Circle circle;

    public Marble(float positionX, float positionY, int radius, Color color, float startSpeed)
    {
        this.positionX = positionX;
        this.positionY = positionY;

        this.radius = radius;
        this.color = color;

        this.startSpeed = startSpeed;

        this.circle = new Circle(this.positionX, this.positionY, this.radius, this.color);
        this.boundingBox = this.circle.getBoundsInParent();
    }

    public String toString()
    {
        return "Position: " + this.positionX + "|" + this.positionY + " Radius: " + this.radius;
    }

    public void update(float newPositionY, float newSpeedY, float newPositionX, float newSpeedX)
    {
        this.circle.setCenterX(newPositionX);
        this.positionX = newPositionX;

//        float translateX = newPositionX - this.positionX;
//        this.circle.setTranslateX(translateX);
//
//        float translateY = newPositionY - this.positionY;
//        this.circle.setTranslateY(translateY);


        this.circle.setCenterY(newPositionY);
        this.positionY = newPositionY;

        this.speedY = newSpeedY;
        this.speedX = newSpeedX;
        this.boundingBox = this.circle.getBoundsInParent();

        //System.out.println(this.positionY);
    }

}
