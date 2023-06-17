package Game.Beans;

import com.badlogic.gdx.physics.box2d.World;

/** Singleton game manager class. */
public class GameManager
{
    /** Global instance of the game manager. */
    private static GameManager instance;

    public Player player;

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
