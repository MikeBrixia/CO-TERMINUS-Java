package Game.Beans;

import Game.Core.GameApplication;
import Game.Graphics.SpriteComponent;
import Game.Utils.Constants;
import Game.Utils.GameUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends GameEntity implements IUpdatable, ICollisionListener
{
    /** Component responsible for moving the player character. */
    private MovementComponent2D movementComponent;

    /** Component responsible for rendering the player sprite. */
    private SpriteComponent spriteComponent;

    public Player()
    {
        setScale(.1f / Constants.GameConfig.METER_PER_PIXEL);

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
        float unitScale = Constants.GameConfig.METER_PER_PIXEL;

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
        Vector3 initialPosition = new Vector3(x, y + 400,0);
        setPosition(initialPosition.x, initialPosition.y);

        // Initialize player physics body.
        BodyDef bodyDefinition = new BodyDef();
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.active = true;
        bodyDefinition.position.set(getX(), getY());
        bodyDefinition.gravityScale = 30f;
        // Define player fixture(collider).
        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.isSensor = false;
        PolygonShape collider = new PolygonShape();
        collider.setAsBox(5f / unitScale, 5f / unitScale);
        fixtureDefinition.shape = collider;
        movementComponent = new MovementComponent2D(this, bodyDefinition, fixtureDefinition);
    }

    @Override
    public void update(float deltaTime) {
       movementComponent.update(deltaTime);

    }

    @Override
    public void destruction() {

    }

    @Override
    public void onCollision(Contact contact) {
       Body rigidbody = movementComponent.getRigidBody();
       System.out.println("Collision!");
    }

    @Override
    public void onCollisionEnd(Contact contact) {
        System.out.println("Collision End!");
    }
}
