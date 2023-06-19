package Game.Beans;

import Game.Core.*;
import Game.Graphics.AnimatorComponent;
import Game.Graphics.SpriteComponent;
import Game.Utils.AnimationUtils;
import Game.Utils.GameConfig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends GameEntity implements IUpdatable, ICollisionListener, IRenderable
{
    /** Object used to handle player inputs. */
    private Input inputHandler;

    /** Component responsible for moving the player character. */
    private MovementComponent2D movementComponent;

    /** Component responsible for rendering the player sprite. */
    private SpriteComponent spriteComponent;

    /** Component responsible for animating the player sprites. */
    private AnimatorComponent animatorComponent;

    /** The player collider. */
    private Fixture collider;

    /** The last movement input scale of the player. */
    private float movementInput;

    /** The time it needs to pass before the player is able
     * to make another jump after landing. */
    private float jumpCooldown;

    private float currentJumpCooldown;

    private boolean canJump;

    private final String[] playerIdleAnimations = new String[]{
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_idle_0000.png",
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_idle_0001.png"
    };

    private final String[] playerRunningAnimations = new String[]{
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_run_0000.png",
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_run_0001.png",
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_run_0002.png",
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_run_0003.png"
    };

    private final String[] playerJumpingAnimation = new String[]{
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_jump_0000.png",
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_jump_0001.png",
            GameConfig.RES_FILEPATH + "sprites/Player/viggo_jump_0002.png",
    };

    public Player()
    {
        // Mark player entity with the 'Player' tag.
        tag = "Player";

        // Get input object.
        inputHandler = Gdx.input;

        // Create and initialize player sprite component.
        spriteComponent = (SpriteComponent) GameFactory.createComponent(SpriteComponent.class, this);
        Sprite playerSprite = new Sprite(new Texture(GameConfig.RES_FILEPATH + "sprites/Player/viggo_idle_0000.png"));
        spriteComponent.setSprite(playerSprite);

        // Create movement component.
        movementComponent = (MovementComponent2D) GameFactory.createComponent(MovementComponent2D.class, this);
        jumpCooldown = .45f;
        currentJumpCooldown = 0;
        canJump = true;

        // Create animator component.
        animatorComponent = (AnimatorComponent) GameFactory.createComponent(AnimatorComponent.class, this);
        // Initialize idle animation.
        Animation<Texture> idleAnimation = AnimationUtils.createAnimation("idle", 0.3f, playerIdleAnimations);
        animatorComponent.addAnimation("idle", idleAnimation);
        // Initialize Running animation.
        Animation<Texture> runningAnimation = AnimationUtils.createAnimation("running", 0.2f, playerRunningAnimations);
        animatorComponent.addAnimation("running", runningAnimation);
        // Initialize Jumping animation.
        Animation<Texture> jumpingAnimation = AnimationUtils.createAnimation("jumping", 0.3f, playerJumpingAnimation);
        animatorComponent.addAnimation("jumping", jumpingAnimation);
    }

    @Override
    public void start() {
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

        // Play the idle animation when the game starts.
        animatorComponent.play("idle", Animation.PlayMode.LOOP, true);
    }

    @Override
    public void update(float deltaTime) {

        // Handle player movement input.
        movementInput(inputHandler);
    }

    /** Process and use player movement inputs. */
    private void movementInput(Input inputHandler)
    {
        // Process and handle horizontal movement input.
        horizontalMovementInput();

        // Process and handle jump inputs.
        jumpInput();

    }

    /** Process horizontal movement inputs. */
    private void horizontalMovementInput()
    {
        // Handle horizontal movement input.
        movementInput = 0f;
        // Press A to move the player left.
        if(inputHandler.isKeyPressed(Input.Keys.A)) {
            movementInput = -1f;
        }
        // Press D to move the player right.
        else if(inputHandler.isKeyPressed(Input.Keys.D)) {
            movementInput = 1f;
        }

        // Is the animator component currently playing any animation?
        // and the player on the ground?
        if(!animatorComponent.isPlaying() && movementComponent.isGrounded()) {
            // Is the player movement input valid(not 0)?
            if((movementInput > 0f || movementInput < 0f) ){
                // If true, then play the player running animation.
                animatorComponent.play("running", Animation.PlayMode.LOOP, true);
            }
            else{
                // Otherwise, play the idle animation.
                animatorComponent.play("idle", Animation.PlayMode.LOOP, true);
            }
        }

        // Determine movement direction using player input, and
        // apply horizontal movement to the player character.
        Vector2 movementDirection = new Vector2(movementInput, 0);
        movementComponent.addMovementInput(movementDirection);
    }

    /** Process jump input. */
    private void jumpInput()
    {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Has the player pressed the jump button?
        if(inputHandler.isKeyPressed(Input.Keys.SPACE)) {
            // Is the player currently able to jump?
            if(canJump){
                // If true, then perform a jump.
                movementComponent.jump();
                // Play the jump animation and block the player jump temporarily.
                animatorComponent.play("jumping", Animation.PlayMode.NORMAL, true);
                canJump = false;
            }
        }
        else {
            // If player releases the jump button early,
            // stop the jump.
            movementComponent.stopJump();
        }

        // Is the player currently blocked from jumping?
        if(!canJump){
            // If true then advance cooldown timer.
            currentJumpCooldown += deltaTime;
            // Has the jump cooldown expired?
            if(currentJumpCooldown >= jumpCooldown){
                // If true, unlock jump for the player and
                // reset cooldown.
                canJump = true;
                currentJumpCooldown = 0f;
            }
        }
    }

    /** Flip the player sprite on the Y axis. */
    private void flipPlayer(float inputScale)
    {
        // Is input valid?
        if(inputScale != 0)
        {
            // If true, then flip the sprite.
            Sprite sprite = spriteComponent.getSprite();
            boolean shouldFlip = inputScale == 1f;
            sprite.setFlip(shouldFlip, false);
        }
    }

    @Override
    public void render() {

        // Is the player sprite valid?
        Sprite playerSprite = spriteComponent.getSprite();
        if(playerSprite != null)
        {
            // If true, then update the sprite to render by asking the animator
            // component what's the current animation frame.
            playerSprite.setTexture(animatorComponent.getCurrentAnimationFrame());
            // Flip player sprite only if input was valid. Sprite must be flipped before rendering.
            flipPlayer(movementInput);
            // Tell the sprite component to render the player sprite.
            spriteComponent.render();
        }
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
