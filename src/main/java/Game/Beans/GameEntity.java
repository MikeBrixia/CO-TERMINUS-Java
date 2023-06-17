package Game.Beans;

import Game.Core.CoTerminus;
import Game.Core.GameApplication;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;
import java.util.List;

/** Game Entity represents an object which can interact with
 * the game world. Game entities are actors with the ability
 * of having multiple components attached to them. */
public abstract class GameEntity extends Actor
{
    /** All the components attached to this entity. */
    public final List<GameComponent> entityComponents = new ArrayList<>();

    public GameEntity()
    {
        // Add the newly created entity to the current scene.
        Scene gameScene = GameApplication.getGameInstance().getScene();
        gameScene.addEntity(this);
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
}
