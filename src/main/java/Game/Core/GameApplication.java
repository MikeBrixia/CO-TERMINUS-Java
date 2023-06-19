package Game.Core;

import Game.Utils.GameConfig;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameApplication extends ApplicationAdapter {

    /** Global game instance reference. */
    private static CoTerminus gameInstance;

    /** Main game camera. */
    private static OrthographicCamera camera;

    /** Reference to the game viewport. */
    private static Viewport viewport;

    // 16:9 resolution.
    private final float viewportWidth = 1024;
    private final float viewportHeight = 576;

    @Override
    public void create() {

        // Scale resolution by the game unit constant.
        float unitScale = GameConfig.METER_PER_PIXEL;
        float width = viewportWidth / unitScale;
        float height = viewportHeight / unitScale;

        // Initialize game camera.
        camera = new OrthographicCamera(width, height);
        viewport = new ExtendViewport(width, height, camera);

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
        // Update the viewport.
        viewport.update(width, height);
        // Center the camera on screen.
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
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

        // Get time delta.
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Update the game.
        gameInstance.gameLoopUpdate(deltaTime);

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
