package Game.Beans;

import Game.Core.GameApplication;
import Game.Utils.Constants;
import Game.Utils.GameUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

/** Default level for CO-TERMINUS. */
public class GameLevel extends Scene implements IUpdatable
{
    /** Tilemap object instance. */
    private TiledMap map;

    /** The renderer of the loaded tilemap. */
    private OrthogonalTiledMapRenderer mapRenderer;

    /** Game Scene unit scale. */
    private float unitScale;

    @Override
    public void start() {
        // Load tilemap .tmx file from disk.
        map = GameUtils.loadTmxTilemap(GameUtils.RES_FILEPATH + "Levels/Map.tmx");

        // Read map unit scale from the transformation constants config.
        unitScale = Constants.METER_PER_PIXEL;

        // Initialize tilemap renderer.
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1/unitScale);

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
            mapRenderer.setView(GameApplication.getCamera());
        }
    }

    @Override
    public void destruction() {

    }

    /** Create all the scene colliders using the map layer collision
     * object data imported from the tiled .tmx file.*/
    private void createSceneCollisions()
    {
        // Create colliders using the data imported from tiled .tmx map.
        MapLayer collisionLayer = map.getLayers().get("Collisions");
        MapObjects collidersData = collisionLayer.getObjects();

        for(MapObject obj : collidersData)
        {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            // Read collision properties and apply to them the game scene
            // unit scale.
            float x = (rect.getX() + rect.getWidth() / 2) / unitScale;
            float y = (rect.getY() + rect.getHeight() / 2) / unitScale;
            Vector3 position = new Vector3(x, y, 0);

            // Initialize collider body definition.
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(position.x, position.y);

            // Create box 2D shape.
            PolygonShape shape = new PolygonShape();

            shape.setAsBox((rect.getWidth() / 2) / unitScale, (rect.getHeight() / 2) / unitScale);
            // Create collision using box 2D shape.
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.isSensor = false;

            // Create the actual physics body and collider attached to it.
            World world = GameApplication.getGameInstance().getWorld();
            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
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
