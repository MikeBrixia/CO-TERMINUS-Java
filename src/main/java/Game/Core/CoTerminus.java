package Game.Core;

import Game.Utils.GameConfig;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.lang.reflect.InvocationTargetException;

/** Main game class, responsible for handling the game loop. */
public class CoTerminus extends Game
{
    /** Reference to the global physics world of the game. */
    private World world;

    /** Global collision contact handler object*/
    private WorldContactListener worldContactListener;

    /** The scene currently played inside by the game application.
     *  Only one scene at a time can be played. */
    private Scene gameLevel;

    private Box2DDebugRenderer debugRenderer;

    @Override
    public void create() {

        // Initialize physics world.
        world = new World(GameConfig.gravity, true);

        // Initialize world collision handler.
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        // Initialize physics debug renderer.
        debugRenderer = new Box2DDebugRenderer();

        // Create game scene.
        gameLevel = createSceneFromConfig();
        // Initialize game scene.
        gameLevel.init();
        // Once it has been initialized,
        // start the game inside the scene.
        gameLevel.start();
    }

    /** Create the scene specified inside the GameConfig.java file. */
    private Scene createSceneFromConfig()
    {
        Scene scene = null;
        try
        {
            Class<?> sceneClass = GameConfig.sceneClass;
            // Is the configuration scene class valid?
            if(sceneClass != null)
            {
                // If true, then create scene by reading the GameConfig.java file.
                scene = (Scene) sceneClass.getConstructor().newInstance();
            }
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch(InvocationTargetException e)
        {
            e.getTargetException().printStackTrace();
        }
        return scene;
    }

    /** Update the game world. */
    public void gameLoopUpdate(float deltaTime)
    {
        // Process user input.
        handleInput();
        // Tick update(called every frame)
        update(deltaTime);
        // Draw game data on screen.
        render();
    }

    private void handleInput() {
    }

    public void update(float deltaTime) {
        
        // Advance physics world simulation.
        world.step(1/60f, 6, 2);
        
        // Update the game scene.
        gameLevel.update(deltaTime);

    }

    public void render()
    {
        // Render the current scene.
        gameLevel.render();

        // Render physics debug data. DISABLED FOR NOW.
        //debugRenderer.render(world, GameApplication.getCamera().combined);
    }

    /** Get the current target scene of the game application. */
    public Scene getScene() {
        return gameLevel;
    }

    /** Get the current physics world. */
    public World getWorld() {
        return world;
    }
}
