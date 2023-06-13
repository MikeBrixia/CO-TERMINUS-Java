package Game.Beans;

import Game.Core.GameApplication;
import Game.Utils.GameUtils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.List;

/** Scene is the object representation of the game world. */
public class Scene implements IUpdatable
{
    /** All the entities which are placed inside the world. */
    private List<GameEntity> entities;

    /** Tilemap object instance. */
    private TiledMap map;

    /** The renderer of the loaded tilemap. */
    private OrthogonalTiledMapRenderer mapRenderer;

    @Override
    public void start() {
        // Load tilemap .tmx file from disk.
        map = GameUtils.loadTmxTilemap(GameUtils.RES_FILEPATH + "Levels/Map.tmx");

        // Initialize tilemap renderer.
        float unitScale = 1;
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        mapRenderer.setView(GameApplication.getCamera());
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
