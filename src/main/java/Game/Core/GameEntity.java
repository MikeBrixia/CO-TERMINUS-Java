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
    protected String tag = "default";

    public GameEntity()
    {
    }

    /** Notify an entity and all it's components
     * that the game has started. */
    public void startEntity()
    {
        // First start the entity.
        start();
        // Then starts each one of it's attached components.
        startComponents();
    }

    /** Start all the entity components. */
    private void startComponents()
    {
        // Notify each entity component that the game
        // has been started.
        for(GameComponent component : entityComponents)
        {
            // Is the component active?
            if(component instanceof IUpdatable updatableComponent)
            {
                updatableComponent.start();
            }
        }
    }

    /** Update an entity with all it's components. */
    public void updateEntity(float deltaTime)
    {
        // First update the entity.
        update(deltaTime);
        // Then update each one of the entity components.
        updateComponents(deltaTime);
    }

    /** Update all the entity components. */
    private void updateComponents(float deltaTime)
    {
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

    /** Destroy all the components attached to this entity. */
    public void destroyComponents()
    {
        // Update each entity component.
        for(GameComponent component : entityComponents)
        {
            // Is the component active?
            if(component.active)
            {
                // Is the component updatable?
                if(component instanceof IUpdatable updatableComponent)
                {
                    updatableComponent.destruction();
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
