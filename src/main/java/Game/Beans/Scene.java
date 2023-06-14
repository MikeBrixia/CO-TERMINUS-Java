package Game.Beans;

import Game.Core.GameApplication;
import Game.Core.WorldContactListener;
import Game.Utils.GameUtils;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;


/** Scene is the object representation of the game world. */
public class Scene implements IUpdatable
{
    /** Tilemap object instance. */
    private TiledMap map;

    /** The renderer of the loaded tilemap. */
    private OrthogonalTiledMapRenderer mapRenderer;

    /** Reference to the physic world instance. */
    private World world;

    /** List of all the map colliders inside the scene. */
    private List<Body> sceneColliders = new ArrayList<>();

    /** Collision contact handler object*/
    private WorldContactListener worldContactListener;

    private float sceneUnitScale = 1.2f;

    private ShapeRenderer renderer;

    @Override
    public void start() {
        // Load tilemap .tmx file from disk.
        map = GameUtils.loadTmxTilemap(GameUtils.RES_FILEPATH + "Levels/Map.tmx");

        // Initialize tilemap renderer.
        mapRenderer = new OrthogonalTiledMapRenderer(map, sceneUnitScale);
        mapRenderer.setView(GameApplication.getCamera());

        world = GameManager.get().world;
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(GameApplication.getCamera().combined);

        // Create all the scene collisions using the data
        // imported from the tiled editor.
        createSceneCollisions();
    }

    @Override
    public void update(float deltaTime) {
        // Is the map renderer valid?
        if(mapRenderer != null)
        {
            // If true, then Render the tilemap on screen.
            mapRenderer.render();
        }
    }

    /** Create all the scene colliders using the map layer collision
     * object data imported from the tiled .tmx file.*/
    private void createSceneCollisions()
    {
        Camera camera = GameApplication.getCamera();
        // Origin coordinates of the map imported from tiled.
        // The origin of the tiled map is in the top-left of the
        // window.
        Vector2 origin = new Vector2(-(camera.viewportWidth / 2), camera.viewportHeight / 2);

        // Create colliders using the data imported from tiled .tmx map.
        MapLayer collisionLayer = map.getLayers().get("Collisions");
        MapObjects collidersData = collisionLayer.getObjects();
        for(MapObject obj : collidersData)
        {
            // All the properties of the collision object.
            MapProperties collisionProperties = obj.getProperties();

            // Read collision properties and apply to them the game scene
            // unit scale.
            float x = GameUtils.pixelToMeters((float) collisionProperties.get("x") * sceneUnitScale);
            float y = GameUtils.pixelToMeters((float) collisionProperties.get("y") * sceneUnitScale);
            float width = (float) collisionProperties.get("width") * sceneUnitScale;
            float height = (float) collisionProperties.get("height") * sceneUnitScale;

            // Initialize collider body definition.
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;

            // Additional offset applied to the y position of the collider to
            // align it with the map ground. This a hardcoded hack and should
            // be changed in the future, but for now it works.
            int additionalYOffset = 60;
            // Offset the collider from the top left corner of the window(tiled origin)
            // of x and y meters.
            bodyDef.position.set(x + origin.x, y - origin.y - additionalYOffset);

            System.out.println(bodyDef.position);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width, height);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.isSensor = true;

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            sceneColliders.add(body);
        }
    }

    public void setMap(TiledMap map)
    {
        this.map = map;
        // Are both the tilemap and map renderer valid?
        if(mapRenderer != null && map != null)
        {
            // If true, set the new map as the
            // tilemap to render.
            mapRenderer.setMap(map);
        }
    }

    public void setMapRenderer(OrthogonalTiledMapRenderer renderer)
    {
        this.mapRenderer = renderer;
    }

    /** Get the tilemap which is being rendered.*/
    public TiledMap getMap() {
        return map;
    }

    /** Get the tilemap renderer. */
    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }
}
