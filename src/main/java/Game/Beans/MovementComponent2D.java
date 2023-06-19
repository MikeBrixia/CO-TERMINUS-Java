package Game.Beans;

import Game.Core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
import java.util.Map;

/** Component used to handle actors 2D movement inside the world. */
public final class MovementComponent2D extends GameComponent
{
     /** The physics body which will be affected by movement. */
     private Body rigidBody;

     /** The collider of the physic body. */
     private Fixture collider;

     /** Global physics world reference. */
     private World world;

     /** Movement data relative to each movement state. */
     private Map<EMovementState, MovementProperty> movementPropertiesMap = new HashMap<>();

     /** The currently active and used movement properties. */
     private MovementProperty movementProperty;

     /** The current movement state of this component. */
     private EMovementState movementState;

     // Use the constructor method to initialize default properties
     // for the component.
     public MovementComponent2D(GameEntity owner)
     {
          super(owner);
          this.world = GameApplication.getGameInstance().getWorld();
          this.movementState = EMovementState.NONE;

          // Initialize walking movement property.
          MovementProperty walkingMovement = new MovementProperty(700, 800,
                                                               100, 100,
                                                           200);
          this.movementPropertiesMap.put(EMovementState.WALKING, walkingMovement);
          this.movementPropertiesMap.put(EMovementState.FALLING, walkingMovement);
          this.movementPropertiesMap.put(EMovementState.JUMPING, walkingMovement);

          // Set the movement state to walking the the game starts.
          // Walking will be the default movement mode on game startup
          // for this component.
          setMovementState(EMovementState.WALKING);
     }

     /** Make the component perform a jump on the Y axis.*/
     public void jump()
     {
          // Is the component owner currently grounded?
          if(isGrounded())
          {
               // If true, then apply and impulse on the Y axis using jump speed.
               Vector2 jumpImpulse = new Vector2(0f, movementProperty.jumpSpeed);
               setMovementState(EMovementState.JUMPING);
               rigidBody.applyLinearImpulse(jumpImpulse, rigidBody.getLocalCenter(), true);
          }
     }

     /** If the component is jumping, stop it and
      * make it fall. */
     public void stopJump()
     {
          // Is the movement component currently jumping?
          if(movementState.equals(EMovementState.JUMPING))
          {
               // If true, then set Y linear velocity to 0, to make
               // the component fall down.
               Vector2 linearVelocity = rigidBody.getLinearVelocity();
               rigidBody.setLinearVelocity(linearVelocity.x, 0);
               setMovementState(EMovementState.FALLING);
          }
     }

     /** Add movement input to the owner actor. Must be called
      * each frame in order to actually advance movement simulation. */
     public void addMovementInput(Vector2 direction)
     {
          // Scale velocity using delta time. This will make movement
          // velocity frame rate independent.
          float deltaTime = Gdx.graphics.getDeltaTime();
          Vector2 velocity = direction.scl(movementProperty.speed).scl(deltaTime);
          // Using input velocity compute movement.
          velocity = computeMovement(new Vector2(velocity.x, rigidBody.getLinearVelocity().y));
          // Update the component owner linear velocity.
          rigidBody.setLinearVelocity(velocity);
          // Update physic body position.
          Vector2 bodyPosition = rigidBody.getPosition();
          owner.setPosition(bodyPosition.x, bodyPosition.y);
     }

     /** Compute movement using input velocity. */
     private Vector2 computeMovement(Vector2 velocity)
     {
          // Is the movement owner falling down on
          // the Y axis?
          if(velocity.y < 0)
          {
               // If true, then set it's state to fall,
               // if not already in it.
               setMovementState(EMovementState.FALLING);
          }
          else if(velocity.y == 0)
          {
               setMovementState(EMovementState.WALKING);
          }

          // Clamp velocity to keep it in defined bounds.
          velocity.x = MathUtils.clamp(velocity.x, -movementProperty.maxSpeed, movementProperty.maxSpeed);
          velocity.y = MathUtils.clamp(velocity.y, -movementProperty.maxFallingSpeed, movementProperty.maxJumpSpeed);

          return velocity;
     }

     /** Is the component owner currently in air?
      * @return True if component owner is in air, false otherwise.*/
     public boolean isInAir()
     {
          return movementState.equals(EMovementState.JUMPING) ||
                  movementState.equals(EMovementState.FALLING);
     }

     /** Is the component owner currently on the ground?
      * @return True if component owner is on the ground, false otherwise. */
     public boolean isGrounded()
     {
          return movementState.equals(EMovementState.WALKING);
     }

     /** Change the movement mode of this component
      * @param movementState The new movement mode which will override the current one. */
     public void setMovementState(EMovementState movementState)
     {
          // Is the new movement state already the current one?
          if(!this.movementState.equals(movementState))
          {
               // If not, update current movement state.
               this.movementState = movementState;
               // When we update the movement state, we also need to switch the movement properties
               // being used with the ones relative to the new state.
               this.movementProperty = movementPropertiesMap.get(movementState);
          }
     }

     public void setRigidBody(Body rigidBody)
     {
          this.rigidBody = rigidBody;
     }

     public void setCollider(Fixture collider)
     {
          this.collider = collider;
     }

     public EMovementState getMovementState()
     {
          return movementState;
     }

     public Body getRigidBody()
     {
          return rigidBody;
     }
}
