package sample;

import java.util.ArrayList;

public class Simulation {

    float gravity;
    int frames = 0;

    float wind = 2; // m/s

    Controller controller;

    ArrayList<Object> objectsInSimulation = new ArrayList<>();

    public Simulation(float gravity, Controller controller)
    {
        this.gravity = gravity;
        this.controller = controller;
    }

    public void update()
    {
        System.out.println("Frames: " + frames);
        for (Object object : objectsInSimulation)
        {
            object.isCollided = false;
        }

        for (Object object1 : objectsInSimulation)
        {
            for (Object object2 : objectsInSimulation) {

                System.out.println("Object 1:" + object1.isCollided);
                if(object1 != object2 && (!object1.isCollided && !object2.isCollided))
                {
//                    if(object1.isCollided || object2.isCollided){
//                        System.out.println("Bereits kollidiert");
//                        break;
//                    }
                    if(checkCollision(object1, object2))
                    {
                        if(object1 instanceof Marble)
                        {
                            Marble marble = ((Marble) object1);
                            marble.speedY *= - 0.8;
                            marble.speedX *= 0.8;
                            object1.isCollided = true;
                        }
                    }
                }
            }
            //Wenn das Objekt nicht statisch ist wird berechnet
            if(!object1.isStatic)
            {
                if(object1 instanceof Marble)
                {
                    Marble marble = ((Marble) object1);
                    float startPositionY = marble.positionY;
                    float startPositionX = marble.positionX;

                    // m/s wird mit 100 multipliziert und dadurch in cm/s umgerechnet.
                    // Berechnung der Geschwindigkeit in Y Richtung
                    float velocityY = (marble.speedY + this.gravity * (1/60f));
                    float newPositionY = (float) (startPositionY + (marble.speedY * 100) * (1/60f) + 0.5 * (this.gravity * 100) * Math.pow(1/60f, 2));
                    //        cm                        cm               m/s             s                            m/s^2         s^2

                    // m/s wird mit 100 multipliziert und dadurch in cm/s umgerechnet.
                    float velocityX = (marble.speedX + wind * (1/60f));
                    float newPositionX = (startPositionX + (marble.speedX * 100) * (1/60f));
                    //       cm             cm                  m/s           s

                    // Kollsion mit dem Boden
                    if(startPositionY + marble.radius > 800 && marble.speedY > 0)
                    {
                        // Nimmt die restliche Energie aus der Kugel, falls sie am boden liegt
                        if(marble.speedY < 0.3f && marble.speedY > -0.3f || marble.speedY == 0)
                        {
                            marble.speedY = 0f;
                            System.out.println("Marble hold detected");
                            break;
                        }

                        // Bounce der Kugel
                        marble.speedX *= 0.8;
                        velocityY *= -0.8;
                    }

                    // Wenn die Kugel keine Geschwindigkeit mehr hat, bleibt sie am Boden liegen und fällt nicht durch den boden durch
                    if(startPositionY+marble.radius >= 800 && marble.speedY == 0)
                    {
                        velocityY = 0;
                        newPositionY = startPositionY;
                        marble.speedX *= 0.8;

                        //System.out.println(marble.speedX + " | " + velocityX);
                        if(marble.speedX < 0.04 && marble.speedX > 0.03 || marble.speedX == 0)
                        {
                            marble.speedX = 0f;
                        }
                   }

                    marble.update(newPositionY, velocityY, newPositionX, velocityX);
                    controller.setText(marble.speedY, newPositionX);
                }
            }
        }
        frames ++;
    }
    public boolean checkCollision(Object object, Object collisionObject) {
            if(object.boundingBox.intersects(collisionObject.boundingBox))
            {
                return true;
            }
        return false;
    }

    public void addObject(Object obj)
    {
        this.objectsInSimulation.add(obj);
    }

}
