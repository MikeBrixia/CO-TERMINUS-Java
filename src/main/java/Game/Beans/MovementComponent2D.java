package Game.Beans;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

/** Component used to handle actors movement inside the
 * world. */
public class MovementComponent2D extends GameComponent
{
     /** The definition and configuration of the physics body. */
     private BodyDef bodyDefinition;

     /** The physics body which will be affected by movement. */
     private Body rigidBody;

     /** The collider of the physic body. */
     private Fixture collider;

     /** Global physics world reference. */
     private World world;

     private float speed;
     private float jumpSpeed;

     private ShapeRenderer debugRenderer;

     public MovementComponent2D(Actor owner, BodyDef bodyDefinition, FixtureDef fixtureDefinition)
     {
          super(owner);
          this.world = GameManager.get().world;
          this.bodyDefinition = bodyDefinition;
          this.debugRenderer = new ShapeRenderer();
          debugRenderer.setAutoShapeType(true);

          // Create the physic body for physic movement handling
          rigidBody = world.createBody(this.bodyDefinition);
          this.collider = rigidBody.createFixture(fixtureDefinition);
     }

     @Override
     public void start() {

     }

     @Override
     public void update(float deltaTime) {
          // Get rigidbody position and use it to update
          // the owner position.
          Vector2 bodyPosition = rigidBody.getPosition();
          owner.setPosition(bodyPosition.x, bodyPosition.y);
     }

     public void debugRender()
     {
          // Render polygon shape.
          debugRenderer.begin(ShapeRenderer.ShapeType.Line);
          debugRenderer.setColor(Color.GREEN);
          Vector2 a = new Vector2();
          Vector2 b = new Vector2();
          PolygonShape colliderShape = (PolygonShape) collider.getShape();
          int vertexCount = colliderShape.getVertexCount();
          for(int i = 0; i < vertexCount; i++)
          {
               colliderShape.getVertex(i, a);
               colliderShape.getVertex((i+1) % vertexCount, b);
               debugRenderer.rectLine(a, b, 3);
          }
          debugRenderer.end();
          // shape rendering has ended.
     }

     public Body getRigidBody()
     {
          return rigidBody;
     }

}
