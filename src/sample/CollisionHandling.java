package sample;

public class CollisionHandling {

    boolean isCollided = false;

    public CollisionHandling() {

    }

    public void checkCollisionFloor(Object object)
    {
        if(object instanceof Marble)
        {
            Marble marble = (Marble)object;
            if(marble.vecPos.y < 0 + marble.radius) {

                if(marble.geschwindigkeit.y < 0.3f && marble.geschwindigkeit.y > -0.3f || marble.geschwindigkeit.y == 0) {
                    marble.geschwindigkeit.y = 0;
                }

                double h = Math.abs(marble.geschwindigkeit.y) * -0.8;
                marble.geschwindigkeit.y = h;

                // Energieverlust
                marble.geschwindigkeit.x *= 0.8;
                marble.geschwindigkeit.y *= -0.8;

                // Kugel soll am Boden liegen bleiben
                if (Math.abs(marble.geschwindigkeit.y) < 0.3) {
                    marble.geschwindigkeit.y = 0;
                    marble.vecPos.y = marble.radius;
                }
            }
        }
    }

    public boolean checkCollisionBox(Object object1, Object object2)
    {
        if((object1 instanceof Marble && object2 instanceof Obstacle) || (object1 instanceof Obstacle && object2 instanceof Marble))
        {
            if(object1 != object2)
            {

            }
        }
        return false;
    }

    public void checkCollisionMarble(Object object1, Object object2)
    {
        if(object1 instanceof Marble && object2 instanceof Marble)
        {
            Marble marble1 = ((Marble) object1);
            Marble marble2 = ((Marble) object2);
            double dx = (marble1.vecPos.x) - (marble2.vecPos.x);
            double dy = (marble1.vecPos.y) - (marble2.vecPos.y);
            double distance = (double) Math.sqrt(dx * dx + dy * dy);

            if(object1 != object2)
            {
                if(distance < marble1.radius + marble2.radius)
                {
                    System.out.println("Collision between two marbles");
                    System.out.println(distance);
                    this.isCollided = true;
                }
            }
        } else
            {
                this.isCollided = false;
            }
    }

//    public float getDistance()
//    {
//
//
//
//        return distance;
//    }
}
