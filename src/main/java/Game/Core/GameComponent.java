package Game.Core;

/** Components are classes which can be attached to game
 * entities to offer additional functionality. */
public abstract class GameComponent
{
    /** Is the game component active and therefore should
     * receive update? When false this component will not
     * receive any game update. */
    public boolean active = true;

    /** The owner is the actor to which this component
     * is attached to. */
    protected GameEntity owner;

    public GameComponent(GameEntity owner)
    {
        // Initialize this component and add it to
        // the owner component list.
        this.owner = owner;
        this.owner.addComponent(this);
    }

    public GameEntity getOwner() {
        return owner;
    }

    public void setOwner(GameEntity owner) {
        this.owner = owner;
    }
}
