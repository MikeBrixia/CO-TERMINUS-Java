package Game.Core;

import Game.Beans.IUpdatable;
import Game.Beans.Scene;
import Game.Utils.GameUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class GameApplication extends ApplicationAdapter {

    private SpriteBatch batch;
    private Sprite viggo;
    private World world;
    private OrthogonalTiledMapRenderer mapRenderer;

    /** The scene currently played inside by the game application.
     *  Only one scene at a time can be played. */
    private static Scene scene;

    /** Main game camera. */
    private static OrthographicCamera camera;

    @Override
    public void create () {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), 400);
        scene = new Scene();
        scene.start();
        world = new World(new Vector2(0, -10), true);
        batch = new SpriteBatch();

        Texture img = new Texture(GameUtils.RES_FILEPATH + "sprites/viggo_idle_0000.png");

        viggo = new Sprite(img);
        viggo.setPosition(-200, -298);
        viggo.setScale(.08f);

        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load(GameUtils.RES_FILEPATH + "Levels/Map.tmx");

        float unitScale = 1;
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        mapRenderer.setView(camera);
    }

    /** This method gets called each tick(every frame).
     * Even though it is called 'render', it functions as
     * a game loop and so the update logic should be
     * handled here for both graphics and game logic. */
    @Override
    public void render () {

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        scene.update(Gdx.graphics.getDeltaTime());

        batch.begin();
        viggo.draw(batch);
        batch.end();

    }

    @Override
    public void dispose () {
        batch.dispose();
        mapRenderer.dispose();
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
