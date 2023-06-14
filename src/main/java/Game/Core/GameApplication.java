package Game.Core;

import Game.Beans.GameManager;
import Game.Beans.Player;
import Game.Beans.Scene;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameApplication extends ApplicationAdapter {

    private World world;

    /** The scene currently played inside by the game application.
     *  Only one scene at a time can be played. */
    private static Scene scene;

    /** Main game camera. */
    private static OrthographicCamera camera;

    private GameManager gameManager;

    private Viewport viewport;

    Box2DDebugRenderer worldDebugRenderer;

    @Override
    public void create() {

        gameManager = GameManager.get();

        // Initialize game camera.
        camera = new OrthographicCamera(1280f, 720f);
        // Move camera origin from Libgdx screen center(0,0) to bottom left corner.
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        // Apply changes to the camera.
        camera.update();

        // Initialize physics world.
        world = new World(new Vector2(0, -9.8f), true);
        worldDebugRenderer = new Box2DDebugRenderer();
        gameManager.world = world;

        scene = new Scene();
        scene.start();

        Player player = new Player();
        gameManager.player = player;

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.setToOrtho(false, width, height);
    }

    /** This method gets called each tick(every frame).
     * Even though it is called 'render', it functions as
     * a game loop and so the update logic should be
     * handled here for both graphics and game logic. */
    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(1/60f, 6, 2);

        float deltaTime = Gdx.graphics.getDeltaTime();
        scene.update(deltaTime);
        camera.update();

        Player player = gameManager.player;
        player.update(deltaTime);
        player.draw(null, 0);

    }

    @Override
    public void dispose () {

    }

    /** Get the main camera of the game. */
    public static OrthographicCamera getCamera() {
        return camera;
    }

    /** Get the current target scene of the game application. */
    public static Scene getScene() {
        return scene;
    }

    /** Set a new target scene for the game application. */
    public static void setScene(Scene scene) {
        GameApplication.scene = scene;
    }
}
