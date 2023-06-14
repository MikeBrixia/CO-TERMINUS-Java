package Game.Beans;

/** Implements this interface if you want your
 * objects to listen to collision events. */
public interface ICollisionListener
{
    /** Called when there is a collision event */
    void onCollision();

    /** Called when the collision event ends. */
    void onCollisionEnd();
}
