package Game.Core;

import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;
import java.util.List;

/** Game Entity represents an object which can interact with
 * the game world. Game entities are actors with the ability
 * of having multiple components attached to them. */
public abstract class GameEntity extends Actor implements IUpdatable
{
    /** Is the game entity active and therefore should
     * receive update? When false this entity will not
     * receive any game update. */
    public boolean active = true;

    /** All the components attached to this entity. */
    public final List<GameComponent> entityComponents = new ArrayList<>();

    /** Tag attached to the game entity. Tags are used to categorize
     * game entities. Tags can be something like: AI, Player, Ground for
     * example. */
    protected String tag;

    public GameEntity()
    {
    }

    @Override
    public void start() {
        // Notify each entity component that the game
        // has been started.
        for(GameComponent component : entityComponents)
        {
            // Is the component active?
            if(component.active && component instanceof IUpdatable updatableComponent)
            {
                updatableComponent.start();
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        // Update each entity component.
       for(GameComponent component : entityComponents)
       {
           // Is the component active?
           if(component.active)
           {
               // Is the component updatable?
               if(component instanceof IUpdatable updatableComponent)
               {
                   updatableComponent.update(deltaTime);
               }
           }
       }
    }

    /** Attach a brand-new component to this entity.
     * @param component the component to attach. */
    public void addComponent(GameComponent component)
    {
        if(component != null)
        {
            entityComponents.add(component);
        }
    }

    /** Remove a component from this entity component list.
     * @param component the component to remove/detach from this entity. */
    public void removeComponent(GameComponent component)
    {
        if(component != null)
        {
            entityComponents.remove(component);
        }
    }

    /** Get the tag assigned to this entity. */
    public String getTag()
    {
        return tag;
    }
}
