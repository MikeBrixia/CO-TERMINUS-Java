package Game.Graphics;
import Game.Core.GameComponent;
import Game.Core.GameEntity;
import Game.Core.IRenderable;
import Game.Core.GameApplication;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


/** Component used to handle sprite creation and
 *  drawing policies. */
public class SpriteComponent extends GameComponent implements IRenderable
{
    /** The sprite this component is going to render, */
    private Sprite sprite;

    /** The batch responsible for drawing the sprite. */
    private SpriteBatch batch;

    public SpriteComponent(GameEntity owner)
    {
        super(owner);
        this.batch = new SpriteBatch();
    }

    @Override
    public void render() {

        // Get the actor scale and use it to update sprite scale.
        Vector2 actorScale = new Vector2(owner.getScaleX(), owner.getScaleY());
        sprite.setScale(actorScale.x, actorScale.y);

        // Get actor position and use it to update sprite position.
        // Actor position will be used to compute the sprite center.
        Vector3 actorPosition = new Vector3(owner.getX(), owner.getY(), 0);
        sprite.setPosition(actorPosition.x + -sprite.getWidth()/2, actorPosition.y + -sprite.getHeight()/2);

        // Get the actor rotation and use it to update the sprite rotation.
        sprite.setRotation(owner.getRotation());

        // Draw the player sprite each frame.
        batch.setProjectionMatrix(GameApplication.getCamera().combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }
}
