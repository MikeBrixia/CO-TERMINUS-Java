package Game.Utils;

import com.badlogic.gdx.math.Vector2;

public class Constants
{
    public static class Physics
    {
        public static final Vector2 gravity = new Vector2(0f, -9.8f);
    }

    public static class Transformations
    {
        public static final float UNIT_SCALE_1x16 = 1/16f;
        public static final float UNIT_SCALE_1x32 = 1/32f;

        /** The unit scale of the game. 1/16 by default. */
        public static final float GAME_UNIT_SCALE = 1/16f;
    }
}
