package Game.Core;

/** Interface implemented by all objects which needs
 * to be updated and receive game loop update events. */
public interface IUpdatable
{
    /** Called when the game starts. */
    void start();

    /** Called each game tick(every frame). */
    void update(float deltaTime);

    /** Called when the object gets destroyed. */
    void destruction();
}
