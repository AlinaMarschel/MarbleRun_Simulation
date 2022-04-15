package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Marble extends Object {

    double positionX;
    double positionY;

    Color color;

    double radius;
    float speed;

    Circle circle;

    public Marble(double positionX, double positionY, int radius, Color color)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.color = color;

        this.circle = new Circle(this.positionX, this.positionY, this.radius, this.color);
    }

    public String toString()
    {
        return "Position: " + this.positionX + "|" + this.positionY + " Radius: " + this.radius;
    }

    public void update(double newPositionY, float newSpeed)
    {
        this.circle.setCenterY(newPositionY);
        this.positionY = newPositionY;

        this.speed = newSpeed;

        System.out.println(this.positionY);
    }

}
