package Game.Beans;

import Game.Core.*;
import Game.Graphics.AnimatorComponent;
import Game.Graphics.SpriteComponent;
import Game.Utils.AnimationUtils;
import Game.Utils.GameConfig;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/** AI enemy of CO-TERMINUS. */
public class Enemy extends GameEntity implements IDamageable, ICollisionListener, IRenderable
{
    /** The movement component responsible for making the AI move. */
    private MovementComponent2D movementComponent;

    /** Component responsible for drawing the AI sprite on screen.*/
    private SpriteComponent spriteComponent;

    /** Component responsible for animating the AI entity.*/
    private AnimatorComponent animatorComponent;

    /** The direction in which this AI will move. */
    private Vector2 movementDirection;

    private String[] enemyFrames = new String[]{
            "sprites/enemy/boomba-light_00.png",
            "sprites/enemy/boomba-light_01.png",
            "sprites/enemy/boomba-light_02.png",
            "sprites/enemy/boomba-light_03.png",
            "sprites/enemy/boomba-light_04.png",
            "sprites/enemy/boomba-light_05.png",
            "sprites/enemy/boomba-light_06.png",
            "sprites/enemy/boomba-light_07.png",
            "sprites/enemy/boomba-light_08.png",
           "sprites/enemy/boomba-light_10.png",
            "sprites/enemy/boomba-light_11.png",
    };

    public Enemy()
    {
        float unitScale = GameConfig.METER_PER_PIXEL;
        this.tag = "AI";

        // Create and initialize movement component.
        this.movementComponent = (MovementComponent2D) GameFactory.createComponent(MovementComponent2D.class, this);

        // Create and initialize sprite component.
        this.spriteComponent = (SpriteComponent) GameFactory.createComponent(SpriteComponent.class, this);
        Sprite sprite = new Sprite(new Texture(enemyFrames[0]));
        spriteComponent.setSprite(sprite);

        // Create and initialize animator component
        this.animatorComponent = (AnimatorComponent) GameFactory.createComponent(AnimatorComponent.class, this);
        // Initialize enemy animation.
        String id = "default";
        Animation<Texture> enemyAnimation = AnimationUtils.createAnimation(id,0.2f, enemyFrames);
        animatorComponent.addAnimation(id, enemyAnimation);
    }

    @Override
    public void start() {
        float unitScale = GameConfig.METER_PER_PIXEL;

        // Initialize player scale.
        setScale(.1f / unitScale);

        // Initialize AI position using game level property EnemySpawn_0 point.
        GameLevel gameLevel = (GameLevel) GameApplication.getGameInstance().getScene();
        MapLayer spawnLayer = gameLevel.getMap().getLayers().get("SpawnPoints");
        MapObject playerSpawn = spawnLayer.getObjects().get("EnemySpawn_0");
        MapProperties spawnProperties = playerSpawn.getProperties();

        // Get the X and Y coordinates of the spawn point scaled by the
        // game unit scale.
        float x = spawnProperties.get("x", float.class) / unitScale;
        float y = spawnProperties.get("y", float.class) / unitScale;

        // Set initial player actor position.
        setPosition(x, y);

        // Initialize AI physics body.
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.active = true;
        bodyDefinition.position.set(getX(), getY());
        bodyDefinition.gravityScale = 30f;

        // Define AI fixture(colliderShape).
        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.isSensor = false;
        PolygonShape colliderShape = new PolygonShape();
        colliderShape.setAsBox(20f / unitScale, 18f / unitScale);
        fixtureDefinition.shape = colliderShape;

        // Create body definition and pass it to the movement component to complete
        // its initialization process.
        Body rigidbody = GameApplication.getGameInstance().getWorld().createBody(bodyDefinition);
        Fixture collider = rigidbody.createFixture(fixtureDefinition);
        rigidbody.setUserData(this);
        movementComponent.setRigidBody(rigidbody);
        movementComponent.setCollider(collider);

        // Play default AI animation.
        animatorComponent.play("default", Animation.PlayMode.LOOP, true);
    }

    @Override
    public void update(float deltaTime) {
        // Get the player current position.
        Player player = GameManager.get().player;
        Vector2 playerPosition = new Vector2(player.getX(), player.getY());
        Body rigidbody = movementComponent.getRigidBody();
        // Find the direction from the enemy to the player, and move the AI Towards it.
        movementDirection = playerPosition.sub(rigidbody.getPosition()).nor();
        movementComponent.addMovementInput(new Vector2(movementDirection.x, 0f));
    }

    @Override
    public void destruction() {
        // Put enemy outside of the map.
        Body rigidbody = movementComponent.getRigidBody();
        rigidbody.getFixtureList().get(0).setSensor(true);
        movementComponent.active = false;
        spriteComponent.active = false;
        spriteComponent.setSprite(null);
        active = false;
    }

    @Override
    public void onReceiveDamage() {
        // When receiving damage, destroy this entity.
        destruction();
    }

    @Override
    public void onCollision(Contact contact) {
        // Get the only collider attached to this AI entity.
        Fixture collider = movementComponent.getRigidBody().getFixtureList().get(0);
        // Select the other collider.
        Fixture otherCollider = contact.getFixtureA().equals(collider)? contact.getFixtureB() : contact.getFixtureA();
        Object userData = otherCollider.getBody().getUserData();
        // Is the other body collider a player entity?
        if(userData instanceof Player player && active)
        {
            // If true, then apply damage to the player.
            player.onReceiveDamage();
        }
    }

    @Override
    public void onCollisionEnd(Contact contact) {

    }

    @Override
    public void render() {
        // Update sprite frame using the animator component.
        Sprite sprite = spriteComponent.getSprite();
        sprite.setTexture(animatorComponent.getCurrentAnimationFrame());
        // Update sprite rotation by looking at the AI movement direction.
        boolean shouldFlip = movementDirection.x > 0f;
        sprite.setFlip(shouldFlip, false);
        // Draw the AI sprite on screen.
        spriteComponent.render();
    }
}
