package Game.Beans;

import com.badlogic.gdx.scenes.scene2d.Actor;

/** Components are classes which can be attached to game
 * entities to offer additional functionality. */
public abstract class GameComponent implements IUpdatable
{
    /** The owner is the actor to which this component
     * is attached to. */
    protected Actor owner;

    public GameComponent(Actor owner)
    {
        this.owner = owner;
    }

    public Actor getOwner() {
        return owner;
    }

    public void setOwner(Actor owner) {
        this.owner = owner;
    }
}
