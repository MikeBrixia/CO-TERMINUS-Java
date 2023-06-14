package Game.Utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

public class GameUtils
{
    public static final String RES_FILEPATH = "src/main/resources/";

    public static final float PIXEL_TO_METERS_FACTOR = 1/3779.527559f;
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

    /** Convert the given pixel value to meters.
     * @param pixelValue the pixel value to convert.
     * @return the value converted to meters. */
    public static float pixelToMeters(float pixelValue)
    {
        return pixelValue *  PIXEL_TO_METERS_FACTOR;
    }
}
