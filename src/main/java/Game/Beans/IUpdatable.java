package Game.Beans;

/** Interface implemented by all objects which needs
 * to be updated and receive game loop update events. */
public interface IUpdatable
{
    /** Called when the game starts. */
    void start();

    /** Called each game tick(every frame). */
    void update(float deltaTime);

}
