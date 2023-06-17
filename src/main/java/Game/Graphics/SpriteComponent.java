package Game.Graphics;
import Game.Beans.GameComponent;
import Game.Beans.GameEntity;
import Game.Beans.IUpdatable;
import Game.Core.GameApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;


/** Component used to handle sprite creation and
 *  drawing policies. */
public class SpriteComponent extends GameComponent
{
    /** The sprite this component is going to render, */
    private Sprite sprite;

    /** The batch responsible for drawing the sprite. */
    private SpriteBatch batch;

    public SpriteComponent(GameEntity owner, String path)
    {
        super(owner);
        Texture image = new Texture(path);
        this.sprite = new Sprite(image);
        this.batch = new SpriteBatch();
    }

    public SpriteComponent(GameEntity owner, Sprite sprite)
    {
        super(owner);
        this.sprite = sprite;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {

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

    @Override
    public void destruction() {

    }
}
