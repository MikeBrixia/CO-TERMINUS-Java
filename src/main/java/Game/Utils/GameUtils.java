package Game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class GameUtils
{
    /** The game resource folder filepath. */
    public static final String RES_FILEPATH = "src/main/resources/";

    /** Load a tilemap from a .tmx file.
     * @param filepath the path on disk of the map file.
     * @return the loaded tilemap.
     * */
    public static TiledMap loadTmxTilemap(String filepath)
    {
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap loadedMap = mapLoader.load(filepath);
        return loadedMap;
    }

}
