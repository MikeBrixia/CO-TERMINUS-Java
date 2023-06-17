package Game.Beans;

import com.badlogic.gdx.scenes.scene2d.Actor;

/** Components are classes which can be attached to game
 * entities to offer additional functionality. */
public abstract class GameComponent implements IUpdatable
{
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
