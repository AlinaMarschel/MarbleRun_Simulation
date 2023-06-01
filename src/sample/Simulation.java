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
    public double getSceneWidth() {
        double sceneWitdhUmgerechnet = 800/100;
        return sceneWitdhUmgerechnet;
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


                    double radius = marble.radius / 100;

                    // Kollisionsabfrage mit dem Boden in Y-Richtung
                    if(strecke.y < 0 + radius) {
                        if(geschwindigkeit.y < 0.3f && geschwindigkeit.y > -0.3f || geschwindigkeit.y == 0) {
                            marble.geschwindigkeit.y = 0;
                            break;
                        }
                        // Energieverlust
                        double h = Math.abs(geschwindigkeit.y) * -0.8;
                        geschwindigkeit.y = h;
                        // Abprallen am Boden
                        geschwindigkeit.y *= -0.8;

                        // Kugel soll am Boden liegen bleiben
                        if (Math.abs(geschwindigkeit.y) < 0.3) {
                            marble.geschwindigkeit.y = 0;
                            strecke.y = radius;
                        }
                    }

                    // Kollisionsabfrage rechte wand
                    if(strecke.x >= getSceneWidth() - radius) {
                        if(geschwindigkeit.x < 0.3f && geschwindigkeit.x > -0.3f || geschwindigkeit.x == 0) {
                            marble.geschwindigkeit.x = 0;
                            break;
                        }
                        // Energieverlust
                        double h = Math.abs(geschwindigkeit.x) * -0.8;
                        geschwindigkeit.x = -h;
                        // Abprallen an der Wand
                        geschwindigkeit.x *= -0.8;
                    }

                    // Kollisionsabfrage linke Wand
                    if(strecke.x <= 0 + radius) {
                        if(geschwindigkeit.x < 0.3f && geschwindigkeit.x > -0.3f || geschwindigkeit.x == 0) {
                            marble.geschwindigkeit.x = 0;
                            break;
                        }

                        // Energieverlust
                        double h = Math.abs(geschwindigkeit.x) * -0.8;
                        geschwindigkeit.x = h;
                        // Abprallen an der Wand
                        geschwindigkeit.x *= -0.8;

                        // Kugel auf der x-Achse zurücksetzen, um eine Überlappung zu verhindern
                        if (strecke.x < 0 + radius) {
                            strecke.x = radius;
                        } else {
                            strecke.x = getSceneWidth() - radius;
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
