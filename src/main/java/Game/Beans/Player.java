package Game.Beans;

import Game.Graphics.SpriteComponent;
import Game.Utils.Constants;
import Game.Utils.GameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor implements IUpdatable
{
    /** Component responsible for moving the player character. */
    private final MovementComponent2D movementComponent;

    /** Component responsible for rendering the player sprite. */
    private final SpriteComponent spriteComponent;

    public Player()
    {
        // Initialize player transform.
        Vector2 initialPosition = new Vector2(0, 0);
        setPosition(initialPosition.x, initialPosition.y);
        setScale(1 * Constants.Transformations.GAME_UNIT_SCALE);

        // Initialize player physics body.
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.active = true;
        bodyDefinition.position.set(initialPosition);
        FixtureDef fixtureDefinition = new FixtureDef();
        PolygonShape collider = new PolygonShape();
        collider.setAsBox(5, 5, initialPosition, 0);
        fixtureDefinition.shape = collider;
        movementComponent = new MovementComponent2D(this, bodyDefinition, fixtureDefinition);

        // Initialize player sprite component.
        String playerSpriteFilepath = GameUtils.RES_FILEPATH + "sprites/viggo_idle_0000.png";
        spriteComponent = new SpriteComponent(this, playerSpriteFilepath);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // Tell the sprite component to draw the player sprite
        // on screen.
        float deltaTime = Gdx.graphics.getDeltaTime();
        spriteComponent.update(deltaTime);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {
       movementComponent.update(deltaTime);

    }
}
