package sample;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Simulation {

    Vector2D gravity; // m/s
    int frames = 0;
    double zeit = (1.0/60.0f);
    Controller controller;
    AnchorPane scene;
    CollisionHandling collisionHandling = new CollisionHandling();

    ArrayList<Object> objectsInSimulation = new ArrayList<>();

    public Simulation(double gravity, Controller controller, AnchorPane scene)
    {
        this.gravity = new Vector2D(0, gravity);
        this.controller = controller;
        this.scene = scene;
    }

    // Höhe der Scene wird berechnet
    public double getSceneHeight() {
        double sceneHeight = scene.getHeight();
        double obenY = scene.localToScene(0,0).getY();
        double untenY= obenY + sceneHeight;

        return untenY;
    }

    public void update()
    {

        for (Object object : objectsInSimulation)
        {
            object.isCollided = false;
        }

        for (Object object1 : objectsInSimulation)
        {
            for (Object object2 : objectsInSimulation)
            {
                collisionHandling.checkCollisionMarble(object1,object2);
            }

            //Wenn das Objekt nicht statisch ist wird berechnet
            if(!object1.isStatic)
            {
                if(object1 instanceof Marble)
                {
                    Marble marble = ((Marble) object1);

                    Vector2D vecStartGeschwindigkeit = marble.geschwindigkeit;

                    // Berechnung der Position
                    Vector2D strecke = new Vector2D(marble.vecPos.x + marble.geschwindigkeit.x * zeit + 0.5 * Math.pow(zeit,2),
                                                    marble.vecPos.y + marble.geschwindigkeit.y * zeit + 0.5 * this.gravity.y * Math.pow(zeit,2));


                    //Berechnung der Geschwindigkeit
                    Vector2D geschwindigkeit = new Vector2D(vecStartGeschwindigkeit.x + marble.wind.x * zeit,
                                                            vecStartGeschwindigkeit.y + this.gravity.y * zeit);

                    // Kollisionsabfrage mit dem Boden in Y-Richtung

                    double radius = marble.radius / 100;

                    if(strecke.y < 0 + radius) {

                        if(geschwindigkeit.y < 0.3f && geschwindigkeit.y > -0.3f || geschwindigkeit.y == 0) {
                            marble.geschwindigkeit.y = 0;
                            break;
                        }

                        double h = Math.abs(geschwindigkeit.y) * -0.8;
                        geschwindigkeit.y = h;

                        // Energieverlust
                        geschwindigkeit.x *= 0.8;
                        geschwindigkeit.y *= -0.8;

                        // Kugel soll am Boden liegen bleiben
                        if (Math.abs(geschwindigkeit.y) < 0.3) {
                            marble.geschwindigkeit.y = 0;
                            strecke.y = radius;
                        }
                    }

                    marble.update(strecke, geschwindigkeit);
                    controller.setText(marble.geschwindigkeit, marble.vecPos);

                }
            }
        }
        frames ++;
    }

    public void addObject(Object obj)
    {
        this.objectsInSimulation.add(obj);
    }

}
