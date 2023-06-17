package Game.Beans;

import java.util.ArrayList;
import java.util.List;

/** Scene is the object representation of the game world. */
public abstract class Scene implements IUpdatable
{
    /** All the entities inside the scene. */
    protected final List<GameEntity> entities = new ArrayList<>();

    /** Add a new entity to the scene. */
    public void addEntity(GameEntity entity)
    {
        if(entity != null)
        {
            // Add the entity to the scene.
            entities.add(entity);
            // Is the new entity updatable?
            if(entity instanceof IUpdatable updatable)
            {
                // If true, call on start on the entity.
                updatable.start();
            }
        }
    }

    public void removeEntity(GameEntity entity)
    {
        if(entity != null)
        {
            // Remove the entity from the scene.
            entities.remove(entity);
            {
                // Is the new entity updatable?
                if(entity instanceof IUpdatable updatable)
                {
                    // If true, notify entity which it has been destroyed.
                    updatable.destruction();
                }
            }
        }
    }
}
