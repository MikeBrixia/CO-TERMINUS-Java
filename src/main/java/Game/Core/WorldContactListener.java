package Game.Core;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/** Global world listener for collision events.
 * This object will listen to every collision event
 * happening in the world, and it's going to handle it.*/
public class WorldContactListener implements ContactListener
{
    @Override
    public void beginContact(Contact contact) {
        try
        {
            // Call on collision event on target bodies owners using reflection.
            Method collisionEvent = ICollisionListener.class.getMethod("onCollision", Contact.class);
            notifyBodies(contact, collisionEvent);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void endContact(Contact contact) {
        try
        {
            // Call on collision end event on target bodies owners using reflection.
            Method endEvent = ICollisionListener.class.getMethod("onCollisionEnd", Contact.class);
            notifyBodies(contact, endEvent);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    /** Notify bodies owners of the collision event. */
    private void notifyBodies(Contact contact, Method event) throws InvocationTargetException, IllegalAccessException
    {
        // If body A is attached to a collision listener, then notify it of the collision event.
        if(contact.getFixtureA().getBody().getUserData() instanceof ICollisionListener listenerA)
        {
            event.invoke(listenerA, contact);
        }

        // If body B is attached to a collision listener, then notify it of the collision event.
        if(contact.getFixtureB().getBody().getUserData() instanceof ICollisionListener listenerB)
        {
            event.invoke(listenerB, contact);
        }
    }
}
