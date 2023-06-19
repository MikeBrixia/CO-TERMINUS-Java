package Game.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationUtils
{
    public static Texture[] createAnimationFramesFromPaths(String ...paths)
    {
        int length = paths.length;
        Texture[] textures = new Texture[length];
        for(int i = 0; i < length; i++)
        {
            textures[i] = new Texture(paths[i]);
        }
        return textures;
    }

    public static Animation<Texture> createAnimation(String ID, float frameDuration, String ...paths)
    {
        // Initialize idle animation.
        Texture[] idleFrames = createAnimationFramesFromPaths(paths);
        return new Animation<Texture>(frameDuration, idleFrames);
    }
}
