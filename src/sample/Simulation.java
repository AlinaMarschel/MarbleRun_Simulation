package sample;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class Simulation {

    Vector2D gravity; // m/s
    int frames = 0;
    double zeit = (1.0/60.0);
    Controller controller;
    AnchorPane scene;

    ArrayList<Object> objectsInSimulation = new ArrayList<>();

    public Simulation(double gravity, Controller controller, AnchorPane scene)
    {
        this.gravity = new Vector2D(0, gravity);
        this.controller = controller;
        this.scene = scene;
    }

    public void update()
    {

        for (Marble marble : Marble.marbles) {
            if(marble.isRolling) {
                marble.roll(zeit, gravity);
            } else {
                marble.move(zeit, gravity);
            }

            for (Marble marbleKollision : Marble.marbles) {
                if (marbleKollision != marble && !marble.kollisionsmarble.contains(marbleKollision)) {
                    marble.checkAndHandleCollision(marbleKollision);
                }
            }

            for (MRLine line : MRLine.lines) {
                marble.checkAndHandleCollision(line);
            }
        }
        for (Marble marble : Marble.marbles) {
            marble.kollisionsmarble.clear();
        }
        controller.setText(Marble.marbles.get(Marble.marbles.size() - 1).geschwindigkeit, Marble.marbles.get(Marble.marbles.size() - 1).vecPos);

        frames++;
    }

    public void addObject(Object obj)
    {
        this.objectsInSimulation.add(obj);
    }

}
