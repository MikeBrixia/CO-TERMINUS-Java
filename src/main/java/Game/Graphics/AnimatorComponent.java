package Game.Graphics;

import Game.Core.GameComponent;
import Game.Core.GameEntity;
import Game.Core.IUpdatable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.HashMap;
import java.util.Map;

/** Component used to animate sprite sheets. */
public class AnimatorComponent extends GameComponent implements IUpdatable
{
    /** All the animations this animator can play.*/
    private Map<String, Animation<Texture>> animations = new HashMap<>();

    /** Time advancement of the animation playback.*/
    private float playbackTime;

    /** The animation which is currently playing. */
    private Animation<Texture> currentAnimation;

    /** The current frame of the animation. */
    private Texture currentFrame;

    /** True if the animator is currently playing an animation, false otherwise. */
    private boolean isPlaying;

    public AnimatorComponent(GameEntity owner) {
        super(owner);
        this.playbackTime = 0f;
        this.isPlaying = false;
    }

    /** Ask the animator component to start the playback of the selected animation in the
     * specified play mode.
     * @param animationID The ID of the animation to play.
     * @param playMode The type of play mode to use for this animation. */
    public void play(String animationID, Animation.PlayMode playMode, boolean override)
    {
        if(!isPlaying || override)
        {
            // Stop any currently running animation
            stop();
            playbackTime = 0f;
            isPlaying = true;
            currentAnimation = animations.get(animationID);
            currentAnimation.setPlayMode(playMode);
        }
    }

    /** Stop the currently running animation. */
    public void stop()
    {
        playbackTime = 0f;
        isPlaying = false;
        currentAnimation = null;
    }

    public void addAnimation(String ID, Animation<Texture> animation)
    {
        if(animation != null && ID != null)
        {
            animations.put(ID, animation);
        }
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    @Override
    public void start() {
        playbackTime = 0f;
    }

    @Override
    public void update(float deltaTime) {
        if(isPlaying)
        {
            // Get the frame of the current playback time.
            currentFrame = currentAnimation.getKeyFrame(playbackTime);
            // Advance animation state time.
            playbackTime += deltaTime;
            // If animation has finished playing, stop it.
            boolean isFinished = currentAnimation.isAnimationFinished(playbackTime);
            if(isFinished){
                stop();
            }
        }
    }

    @Override
    public void destruction() {
    }

    /** The current frame of the playing animation. */
    public Texture getCurrentAnimationFrame()
    {
        return currentFrame;
    }
}
