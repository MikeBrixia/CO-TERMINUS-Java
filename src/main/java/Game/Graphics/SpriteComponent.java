package Game.Graphics;
import Game.Beans.GameComponent;
import Game.Beans.IUpdatable;
import Game.Core.GameApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


/** Component used to handle sprite creation and
 *  drawing policies. */
public class SpriteComponent extends GameComponent
{
    /** The sprite this component is going to render, */
    private Sprite sprite;

    /** The batch responsible for drawing the sprite. */
    private SpriteBatch batch;

    public SpriteComponent(Actor owner, String path)
    {
        super(owner);
        Texture image = new Texture(path);
        this.sprite = new Sprite(image);
        this.batch = new SpriteBatch();
        batch.setProjectionMatrix(GameApplication.getCamera().combined);
    }

    public SpriteComponent(Actor owner, Sprite sprite)
    {
        super(owner);
        this.sprite = sprite;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {

        // Get actor position and use it to update sprite position.
        Vector2 actorPosition = new Vector2(owner.getX(), owner.getY());
        sprite.setPosition(actorPosition.x, actorPosition.y);

        // Get the actor scale and use it to update sprite scale.
        Vector2 actorScale = new Vector2(owner.getScaleX(), owner.getScaleY());
        sprite.setScale(actorScale.x, actorScale.y);
        
        // Get the actor rotation and use it to update the sprite rotation.
        sprite.setRotation(owner.getRotation());

        // Draw the player sprite each frame.
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
}
