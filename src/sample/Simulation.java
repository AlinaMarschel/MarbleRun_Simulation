package sample;

import java.util.ArrayList;

public class Simulation {

    float gravity;

    ArrayList<Object> objectsInSimulation = new ArrayList<>();

    public Simulation(float gravity)
    {
        this.gravity = gravity;
    }

    public void update()
    {
        for (Object obj : objectsInSimulation)
        {
            //Wenn das Objekt nicht statisch ist wird berechnet
            if(!obj.isStatic)
            {
                if(obj instanceof Marble)
                {
                    Marble marble = ((Marble) obj);
                    double startPositionY = marble.positionY;
                    float velocity = marble.speed + this.gravity * (1/60f);
                    double newPositionY = startPositionY + marble.speed * (1/60f) + 0.5 * this.gravity * Math.pow(1/60f, 2);

                    if(startPositionY >= 800 - marble.radius){
                        velocity *= -1;
                    }

                    marble.update(newPositionY, velocity);
                }
            }

        }
    }

    public void addObject(Object obj)
    {
        this.objectsInSimulation.add(obj);
    }


}
