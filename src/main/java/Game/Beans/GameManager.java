package Game.Beans;

import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

/** Singleton game manager class. */
public class GameManager
{
    /** Global instance of the game manager. */
    private static GameManager instance;

    /** The current player of the game. */
    public Player player;

    public Enemy AI;

    /** All the pooled projectiles. */
    public List<Projectile> projectiles;

    private GameManager()
    {
    }

    public static GameManager get()
    {
        if(instance == null)
        {
            instance = new GameManager();
        }
        return instance;
    }
}
