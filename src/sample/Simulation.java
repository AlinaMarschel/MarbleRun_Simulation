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
            for (Object collisionObj : objectsInSimulation) {
                if(obj != collisionObj)
                {
//                    System.out.println(obj.boundingBox);
////                    System.out.println(collisionObj.boundingBox);
                    checkCollision(obj, collisionObj);
                }
            }
            //Wenn das Objekt nicht statisch ist wird berechnet
            if(!obj.isStatic)
            {
                if(obj instanceof Marble)
                {
                    Marble marble = ((Marble) obj);
                    float startPositionY = marble.positionY;
                    float startPositionX = marble.positionX;

                    // m/s wird mit 100 multipliziert und dadurch in cm/s umgerechnet.
                    float velocityY = (marble.speedY + this.gravity * (1/60f));
                    float newPositionY = (float) (startPositionY + (marble.speedY * 100) * (1/60f) + 0.5 * (this.gravity * 100) * Math.pow(1/60f, 2));
                    //        cm                        cm               m/s             s                            m/s^2         s^2

                    // m/s wird mit 100 multipliziert und dadurch in cm/s umgerechnet.
                    float velocityX = (marble.speedX + marble.startSpeed * (1/60f));
                    float newPositionX = (startPositionX + (velocityX * 100) * (1/60f));
                    //       cm             cm                  m/s           s

                    if(startPositionY + marble.radius > 800 && marble.speedY > 0)
                    {
                        if(marble.speedY < 0.3f && marble.speedY > -0.3f || marble.speedY == 0)
                        {
                            marble.speedY = 0f;
                            System.out.println("Marble hold detected");
                            break;
                        }
                        velocityX *= 0.8;
                        velocityY *= -0.8;
                    }

                    if(startPositionY+marble.radius >= 800 && marble.speedY == 0)
                    {
                        velocityY = 0;
                        newPositionY = startPositionY;
                        velocityX *= 0.8;

                        //System.out.println(marble.speedX + " | " + velocityX);
//                        if(marble.speedX < 0.04 && marble.speedX > 0.03 || marble.speedX == 0)
//                        {
//                            marble.speedX = 0f;
//                            System.out.println("hallo");

//                        }
                   }
                    marble.update(newPositionY, velocityY, newPositionX, velocityX);
                }
            }
        }
    }
    public boolean checkCollision(Object object, Object collisionObject) {
            if(object.boundingBox.intersects(collisionObject.boundingBox))
            {
                //object.isStatic = true;
                System.out.println("Collision");
                return true;
            }
        return false;
    }

    public void addObject(Object obj)
    {
        this.objectsInSimulation.add(obj);
    }


}
