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
                    float startPositionY = marble.positionY;
                    float startPositionX = marble.positionX;

                    float velocityY = (marble.speedY + this.gravity * (1/60f));
                    float newPositionY = (float) (startPositionY + marble.speedY * (1/60f) + 0.5 * this.gravity * Math.pow(1/60f, 2));
                    //        m                        m               m/s             s                m/s               s^2

                    float velocityX = (marble.speedX + marble.startSpeed * (1/60f));
                    float newPositionX = (startPositionX + velocityX * (1/60f));

                    if(startPositionY + marble.radius >800 && marble.speedY > 0){

                        if(marble.speedY < 15.7f && marble.speedY > -15.7f || marble.speedY == 0){
                            marble.speedY = 0f;
                            System.out.println("Marble hold detected");
                            break;
                        }
                        velocityX *= 0.8;
                        velocityY *= -0.8;

                    }

                    if(startPositionY+marble.radius >= 800 && marble.speedY == 0){
                        break;
                    }

                    marble.update(newPositionY, velocityY, newPositionX, velocityX);
                }

            }

        }
    }

    public void addObject(Object obj)
    {
        this.objectsInSimulation.add(obj);
    }


}
