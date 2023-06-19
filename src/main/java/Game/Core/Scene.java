package Game.Core;

import java.util.ArrayList;
import java.util.List;

/** Scene is the object representation of the game world. */
public abstract class Scene implements IUpdatable, IRenderable
{
    /** All the entities inside the scene. */
    public final List<GameEntity> entities = new ArrayList<>();

    /** Called to initialize the game scene. */
    public abstract void init();

    @Override
    public void start() {
        // Notify each game entity that
        // the game has started.
        for(GameEntity entity : entities)
        {
            entity.startEntity();
        }
    }

    @Override
    public void update(float deltaTime) {
        // Notify each game entity of a new
        // game tick update.
        for(GameEntity entity : entities)
        {
            // Is the entity active and therefore updatable?
            if(entity.active)
            {
                entity.updateEntity(deltaTime);
            }
        }
    }

    @Override
    public void destruction() {
        // When this scene gets disposed,
        // destroy all entities inside it.
        for(GameEntity entity : entities)
        {
            // First destroy all the entity components.
            entity.destroyComponents();
            // Then destroy
            entity.destruction();
        }
    }
}
