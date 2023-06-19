package Game.Utils;

import Game.Core.GameEntity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

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
