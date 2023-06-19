package Game.Core;

import Game.Beans.GameManager;
import Game.Beans.Projectile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;

/** This task will take care of shooting projectiles logic.*/
public class ShootTask extends Timer.Task
{
    /** The time that needs to pass before for a projectile before
     *  returning inside the pool.*/
    private float projectileLifespan;

    /** The position at which the projectile is going to spawn. */
    private Vector2 position;

    /** The direction in which the player wants to shoot the projectile. */
    private Vector2 direction;

    /** True if the player is able to shoot, false otherwise. */
    private boolean canShoot;

    /** A pool of projectiles ready to be used by this task. */
    private List<Projectile> projectilePool = new ArrayList<>();

    /** List of all the projectiles which are currently not available. */
    private List<Projectile> unavailableProjectiles = new ArrayList<>();

    public ShootTask(int projectilePoolAmount)
    {
        this.canShoot = true;
        this.projectileLifespan = 10f;
        fillPool(projectilePoolAmount);
    }

    public ShootTask(Vector2 position, Vector2 direction, int projectilePoolAmount)
    {
        this.position = position;
        this.direction = direction;
        this.canShoot = true;
        this.projectileLifespan = 10f;
        fillPool(projectilePoolAmount);
    }

    /** Get a projectile from the object pool. */
    private Projectile pool()
    {
        Projectile projectile = null;
        // If there is at least one projectile in the pool,
        // get it and remove it from the pool.
        if(projectilePool.size() > 0)
        {
            projectile = projectilePool.get(0);
            projectilePool.remove(projectile);
            unavailableProjectiles.add(projectile);
            // Pass to the game manager only the currently in use projectiles,
            // these are the one which needs to be rendered.
            GameManager.get().projectiles = unavailableProjectiles;
        }
        return projectile;
    }

    /** Fill the pool with a variable amount of projectiles. */
    private void fillPool(int amount)
    {
        // An offset to avoid projectile from colliding one another at the start of the game.
        float spawnOffset = 0;
        // Clear the pool from any existing object.
        projectilePool.clear();
        // Create N amount of projectiles, and
        // ad them to the pool.
        for(int i = 0; i < amount; i++)
        {
            // Create new object entity.
            Projectile projectile = (Projectile) GameFactory.createEntity(Projectile.class);
            projectilePool.add(projectile);
            projectile.setPosition(3000000 + spawnOffset, 30000000 + spawnOffset);
            // Increment the offset to avoid pool collisions.
            spawnOffset += 1000;
        }
    }

    /** Shoot a projectile. */
    public void shoot()
    {
        if(canShoot)
        {
            Projectile projectile = pool();
            projectile.setMovementDirection(direction);
            projectile.setProjectilePosition(position);
            projectile.active = true;
            canShoot = false;

            // Lifespan timer before returning the projectile
            // to the pool.
            Timer lifespanTimer = new Timer();
            Timer.Task backToPoolTask = new Timer.Task() {
                @Override
                public void run() {
                    // Return the projectile to the pool.
                    unavailableProjectiles.remove(projectile);
                    projectilePool.add(projectile);
                }
            };
            lifespanTimer.scheduleTask(backToPoolTask, projectileLifespan);
        }
    }

    @Override
    public void run() {
        // Unlock shoot task to be used again by entities.
        canShoot = true;
    }

    public void setSpawnPosition(Vector2 position)
    {
        this.position = position;
    }

    public void setDirection(Vector2 direction)
    {
        this.direction = direction;
    }
}
