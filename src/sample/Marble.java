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

        this.speedX = startSpeed;

        this.circle = new Circle(this.positionX, this.positionY, this.radius, this.color);
        this.boundingBox = this.circle.getBoundsInParent();
    }

    public String toString()
    {
        return "Position: " + this.positionX + "|" + this.positionY + " Radius: " + this.radius;
    }

    // Marble wird jeden Frame aktualisiert und die Werte aus der Simulation werden hier übergeben
    public void update(float newPositionY, float newSpeedY, float newPositionX, float newSpeedX)
    {

        // die Position der Kugel in X-Richtung wird aktualisiert
        this.circle.setCenterX(newPositionX);
        this.positionX = newPositionX;

        // die Position der Kugel in Y-Richtung wird aktualisiert
        this.circle.setCenterY(newPositionY);
        this.positionY = newPositionY;

        // Geschwindigkeit in X und Y Richtung wird aktualisiert
        this.speedY = newSpeedY;
        this.speedX = newSpeedX;

        // Hier wird die Boundingbox der Kugel aktualisiert
        this.boundingBox = this.circle.getBoundsInParent();


    }

}
