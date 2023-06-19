package Game.Core;

import com.badlogic.gdx.physics.box2d.Contact;

/** Implements this interface if you want your
 * objects to listen to collision events. */
public interface ICollisionListener
{
    /** Called when there is a collision event */
    void onCollision(Contact contact);

    /** Called when the collision event ends. */
    void onCollisionEnd(Contact contact);
}
