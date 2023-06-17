package sample;

import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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


        Marble.marble.roll(zeit, gravity);

//        if(Marble.marble.isRolling) {
//            Marble.marble.roll(gravity);
//        } else {
//            Marble.marble.move(zeit, gravity);
//        }

        for (MRLine line : MRLine.lines) {
            Marble.marble.checkAndHandleCollision(line);
        }

        controller.setText(Marble.marble.geschwindigkeit, Marble.marble.vecPos);

        frames++;
    }

    public void addObject(Object obj)
    {
        this.objectsInSimulation.add(obj);
    }

}
