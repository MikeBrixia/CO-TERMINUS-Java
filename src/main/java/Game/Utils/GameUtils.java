package Game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameUtils
{

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
