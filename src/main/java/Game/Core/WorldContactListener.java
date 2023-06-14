package Game.Core;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/** Global world listener for collision events.
 * This object will listen to every collision event
 * happening in the world, and it's going to handle it.*/
public class WorldContactListener implements ContactListener
{
    @Override
    public void beginContact(Contact contact) {
       System.out.println("Colliding!" + "\s" + contact.getFixtureA().getBody().getPosition());
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Ending collision!");
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
