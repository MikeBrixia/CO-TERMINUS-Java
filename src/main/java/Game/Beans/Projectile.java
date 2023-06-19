package Game.Beans;

import Game.Core.*;
import Game.Graphics.AnimatorComponent;
import Game.Graphics.SpriteComponent;
import Game.Utils.GameConfig;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/** A projectile which can be used by other game entities.  */
public class Projectile extends GameEntity implements ICollisionListener, IRenderable
{
    /** The collider of this projectile. */
    private Fixture collider;

    /** Physic body of the projectile. */
    private Body rigidbody;

    /** The position at which the projectile spawns when
     * instantiated by the object pool. When added back to the pool,
     * the projectile will return to this position.*/
    private Vector2 initialPosition;

    /** The direction towards which the projectile is moving. */
    private Vector2 movementDirection;

    /** The component responsible for drawing the projectile sprite on screen. */
    private SpriteComponent spriteComponent;

    /** The constant speed at which the projectile is moving.*/
    private float speed;

    public Projectile()
    {
        // Initialize object default parameters.
        this.speed = 2000f;
        this.movementDirection = new Vector2(0f, 0f);
        this.active = false;

        // Create and initialize sprite component.
        this.spriteComponent = (SpriteComponent) GameFactory.createComponent(SpriteComponent.class, this);
        Sprite sprite = new Sprite(new Texture(GameConfig.RES_FILEPATH + "sprites/player/projectile/viggo-blast_0000.png"));
        spriteComponent.setSprite(sprite);
    }

    @Override
    public void start() {

        initialPosition = new Vector2(getX(), getY());
        float unitScale = GameConfig.METER_PER_PIXEL;
        setScale(.1f / unitScale);

        // Initialize player physics body.
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.KinematicBody;
        bodyDefinition.active = true;
        bodyDefinition.position.set(getX(), getY());
        bodyDefinition.gravityScale = 0f;

        // Define player fixture(colliderShape).
        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.isSensor = true;
        PolygonShape colliderShape = new PolygonShape();
        colliderShape.setAsBox(28f / unitScale, 10f / unitScale);
        fixtureDefinition.shape = colliderShape;

        // Create body definition and pass it to the movement component to complete
        // its initialization process.
        rigidbody = GameApplication.getGameInstance().getWorld().createBody(bodyDefinition);
        collider = rigidbody.createFixture(fixtureDefinition);
        rigidbody.setUserData(this);

    }

    @Override
    public void update(float deltaTime) {
        float velocityX = movementDirection.x * speed * deltaTime;
        // Move the projectile towards the shooting direction.
        Vector2 velocity = rigidbody.getLinearVelocity();
        velocity.x = velocityX;
        velocity.y = 0f;
        rigidbody.setLinearVelocity(velocity);
        // Update physic body position.
        Vector2 bodyPosition = rigidbody.getPosition();
        setPosition(bodyPosition.x, bodyPosition.y);
    }

    @Override
    public void destruction() {
         setPosition(initialPosition.x, initialPosition.y);

    }

    @Override
    public void onCollision(Contact contact) {
        Fixture otherCollider = contact.getFixtureA().equals(collider)? contact.getFixtureB() : contact.getFixtureA();
        Object userData = otherCollider.getBody().getUserData();

        // Is the other body collider an entity with the AI tag assigned to it?
        if(userData instanceof Enemy enemy)
        {
            // Apply damage to the enemy.
            enemy.onReceiveDamage();
        }
    }

    @Override
    public void onCollisionEnd(Contact contact) {

    }

    @Override
    public void render() {
        // Is the sprite component active?
        if(active)
        {
            // Depending on the direction of projectile movement,
            // flip the sprite.
            Sprite sprite = spriteComponent.getSprite();
            boolean shouldFlip = movementDirection.x == 1f;
            sprite.flip(shouldFlip, false);

            // Draw the projectile sprite on screen.
            spriteComponent.render();
        }
    }

    public void setProjectilePosition(Vector2 position)
    {
        setPosition(position.x, position.y);
        rigidbody.setTransform(position.x, position.y, 0f);
    }

    public void setMovementDirection(Vector2 direction)
    {
        this.movementDirection = direction;
    }
}
