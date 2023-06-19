package Game.Beans;

/** Data class to hold movement properties which can be used by
 * movement components to compute and perform movement simulations. */
public class MovementProperty
{
    /** The speed at which the component owner is going to move. */
    public float speed;

    /** The max speed that the component owner can reach. */
    public float maxSpeed;

    /** The Y speed of the jump. */
    public float jumpSpeed;

    /** The maximum reachable Y speed for the jump.*/
    public float maxJumpSpeed;

    public MovementProperty(float speed, float maxSpeed, float jumpSpeed, float maxJumpSpeed)
    {
        this.speed = speed;
        this.maxSpeed = maxSpeed;
        this.jumpSpeed = jumpSpeed;
        this.maxJumpSpeed = maxJumpSpeed;
    }
}
