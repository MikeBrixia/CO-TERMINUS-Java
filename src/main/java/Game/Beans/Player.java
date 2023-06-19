package Game.Beans;

import Game.Core.*;
import Game.Graphics.SpriteComponent;
import Game.Utils.GameConfig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends GameEntity implements IUpdatable, ICollisionListener, IRenderable
{
    /** Component responsible for moving the player character. */
    private MovementComponent2D movementComponent;

    /** Component responsible for rendering the player sprite. */
    private SpriteComponent spriteComponent;

    /** The player collider. */
    private Fixture collider;

    public Player()
    {
        // Create and initialize player sprite component.
        spriteComponent = (SpriteComponent) GameFactory.createComponent(SpriteComponent.class, this);
        String playerSpriteFilepath = GameConfig.RES_FILEPATH + "sprites/viggo_idle_0000.png";
        Sprite sprite = new Sprite(new Texture(playerSpriteFilepath));
        spriteComponent.setSprite(sprite);

        // Create movement component.
        movementComponent = (MovementComponent2D) GameFactory.createComponent(MovementComponent2D.class, this);
    }

    @Override
    public void start() {
        super.start();
        float unitScale = GameConfig.METER_PER_PIXEL;

        // Initialize player scale.
        setScale(.1f / unitScale);

        // Initialize player position using game level property PlayerSpawn point.
        GameLevel gameLevel = (GameLevel) GameApplication.getGameInstance().getScene();
        MapLayer spawnLayer = gameLevel.getMap().getLayers().get("SpawnPoints");
        MapObject playerSpawn = spawnLayer.getObjects().get("PlayerSpawn");
        MapProperties spawnProperties = playerSpawn.getProperties();

        // Get the X and Y coordinates of the spawn point scaled by the
        // game unit scale.
        float x = spawnProperties.get("x", float.class) / unitScale;
        float y = spawnProperties.get("y", float.class) / unitScale;

        // Set initial player actor position.
        setPosition(x, y);

        // Initialize player physics body.
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.active = true;
        bodyDefinition.position.set(getX(), getY());
        bodyDefinition.gravityScale = 30f;

        // Define player fixture(colliderShape).
        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.isSensor = false;
        PolygonShape colliderShape = new PolygonShape();
        colliderShape.setAsBox(20f / unitScale, 30f / unitScale);
        fixtureDefinition.shape = colliderShape;

        // Create body definition and pass it to the movement component to complete
        // its initialization process.
        Body rigidbody = GameApplication.getGameInstance().getWorld().createBody(bodyDefinition);
        collider = rigidbody.createFixture(fixtureDefinition);
        rigidbody.setUserData(this);
        movementComponent.setRigidBody(rigidbody);
        movementComponent.setCollider(collider);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Get Gdx input handler.
        Input inputHandler = Gdx.input;

        // Handle horizontal movement input.
        float inputScale = 0f;
        if(inputHandler.isKeyPressed(Input.Keys.A))
        {
            inputScale = -1f;
        }
        else if(inputHandler.isKeyPressed(Input.Keys.D))
        {
            inputScale = 1f;
        }

        // Handle jump input.
        if(inputHandler.isKeyPressed(Input.Keys.SPACE))
        {
            movementComponent.jump();
        }
        else
        {
            movementComponent.stopJump();
        }

        Vector2 movementDirection = new Vector2(inputScale, 0);
        movementComponent.addMovementInput(movementDirection);
    }

    @Override
    public void render() {
        // Tell the sprite component to render the
        // player sprite.
        spriteComponent.render();
    }

    @Override
    public void destruction() {

    }

    @Override
    public void onCollision(Contact contact) {
        Fixture otherCollider = contact.getFixtureA().equals(collider)? contact.getFixtureB() : contact.getFixtureA();

        Object userData = otherCollider.getBody().getUserData();
        // Is the other body collider an entity with the AI tag assigned to it?
        if(userData instanceof GameEntity entity
                && entity.getTag().equals("AI"))
        {

        }
        // If the player has collided with the ground or an invisible wall
        // notify the movement component that there was a collision.
        else if(userData.equals("Ground") || userData.equals("InvisibleWall"))
        {
            //movementComponent.onCollision(contact);
        }
    }

    @Override
    public void onCollisionEnd(Contact contact) {
        Fixture otherCollider = contact.getFixtureA().equals(collider)? contact.getFixtureB() : contact.getFixtureA();

        Object userData = otherCollider.getBody().getUserData();
        // Is the other body collider an entity with the AI tag assigned to it?
        if(userData instanceof GameEntity entity && entity.getTag().equals("AI"))
        {

        }
        // If the player has collided with the ground or an invisible wall
        // notify the movement component that there was a collision end event.
        else if(userData.equals("Ground") || userData.equals("InvisibleWall"))
        {
            //movementComponent.onCollisionEnd(contact);
        }

    }
}
