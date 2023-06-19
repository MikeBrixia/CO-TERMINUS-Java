package Game.Core;

import Game.Exceptions.InvalidObjectException;

import java.lang.reflect.InvocationTargetException;

/** Factory used to create game entities and components. */
public class GameFactory
{
    /** Create a brand-new entity inside the current scene. */
    public static GameEntity createEntity(Class<? extends GameEntity> entityClass)
    {
        GameEntity entity = null;
        try
        {
            // Is the entity class valid?
            if(entityClass == null)
            {
                throw new InvalidObjectException("Cannot create a game entity of type: 'null'!");
            }
            // Create an entity of the supplied class and add it to the current
            // scene.
            entity = entityClass.getConstructor().newInstance();
            GameApplication.getGameInstance().getScene().entities.add(entity);
        }
        catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
        }
        return entity;
    }

    /** Create a brand-new entity inside the current scene. */
    public static GameComponent createComponent(Class<? extends GameComponent> componentClass, GameEntity parent)
    {
        GameComponent component = null;
        try
        {
            // Is the component class valid?
            if(componentClass == null)
            {
               throw new InvalidObjectException("Cannot create a component of type: 'null'!");
            }
            // Create a component of the supplied class.
            component = componentClass.getConstructor(GameEntity.class).newInstance(parent);
            parent.entityComponents.add(component);
        }
        catch (NoSuchMethodException | InvocationTargetException
                | InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return component;
    }
}
