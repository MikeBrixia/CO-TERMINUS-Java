package Game.Core;

import Game.Utils.Constants;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameApplication extends ApplicationAdapter {

    /** Global game instance reference. */
    private static CoTerminus gameInstance;

    /** Main game camera. */
    private static OrthographicCamera camera;

    /** Reference to the game viewport. */
    private static Viewport viewport;

    private float viewportWidth = 1280;
    private float viewportHeight = 720;

    @Override
    public void create() {

        float scale = Constants.GameConfig.METER_PER_PIXEL;

        // Initialize game camera.
        camera = new OrthographicCamera(viewportWidth / scale, viewportHeight / scale);
        viewport = new FitViewport(viewportWidth / scale, viewportHeight / scale, camera);
        // Center the camera on the screen.
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight()/ 2, 0);
        // Apply changes to the camera.
        camera.update();

        // Create game instance and call its start method.
        gameInstance = new CoTerminus();
        gameInstance.create();
    }

    /** Called when the game gets resumed from the paused state. */
    @Override
    public void resume() {
        super.resume();
    }

    /** Called when the game gets put into the paused state. */
    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.update();
    }

    /** This method gets called each tick(every frame).
     * Even though it is called 'render', it functions as
     * a game loop and so the update logic should be
     * handled here for both graphics and game logic. */
    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update game instance.
        float deltaTime = Gdx.graphics.getDeltaTime();
        gameInstance.update(deltaTime);

        // Update the camera properties at the end of the game update.
        camera.update();
    }

    @Override
    public void dispose () {

    }

    /** Get the main camera of the game. */
    public static OrthographicCamera getCamera() {
        return camera;
    }

    /** Get the application game instance. */
    public static CoTerminus getGameInstance() { return gameInstance; }

    public static Viewport getViewport() { return viewport; }
}
