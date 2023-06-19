package Game.Utils;

import Game.Beans.GameLevel;
import Game.Core.Scene;
import com.badlogic.gdx.math.Vector2;

/** Collection of constants used to configure the game. */
public class GameConfig
{
    /** The scene class which will be used for the game. */
    public static final Class<? extends Scene> sceneClass = GameLevel.class;

    /** The unit scale of the game. 1/16 by default. */
    public static final float METER_PER_PIXEL = 16f;

    /** The gravity force of the game. */
    public static final Vector2 gravity = new Vector2(0f, -9.8f);

    /** The game resource folder filepath. */
    public static final String RES_FILEPATH = "src/main/resources/";

}
