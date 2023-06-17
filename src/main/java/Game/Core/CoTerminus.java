package Game.Core;

import Game.Beans.*;
import Game.Utils.Constants;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/** Global configuration class for CO-TERMINUS game.
 * this class will hold all the info and flow of the game
 * we're playing. */
public class CoTerminus extends Game implements IUpdatable
{
    /** Reference to the global physics world of the game. */
    private World world;

    /** Global collision contact handler object*/
    private WorldContactListener worldContactListener;

    /** The scene currently played inside by the game application.
     *  Only one scene at a time can be played. */
    private GameLevel gameLevel;

    /** Global singleton game manager instance. */
    private GameManager gameManager;

    private Box2DDebugRenderer red;

    @Override
    public void create() {

        // Initialize/get game manager.
        gameManager = GameManager.get();

        // Initialize physics world.
        world = new World(Constants.gravity, true);
        red = new Box2DDebugRenderer();
        // Initialize game scene representation.
        gameLevel = new GameLevel();
        gameLevel.start();

        Player player = new Player();
        gameManager.player = player;

        start();
    }

    @Override
    public void start() {

        // Initialize world collision handler.
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);
    }

    @Override
    public void update(float deltaTime) {
        world.step(1/60f, 6, 2);

        gameLevel.update(deltaTime);

        Player player = gameManager.player;
        player.update(deltaTime);
        player.draw(null, 0);
        red.render(world, GameApplication.getCamera().combined);
    }

    @Override
    public void destruction() {

    }

    public void render()
    {

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
