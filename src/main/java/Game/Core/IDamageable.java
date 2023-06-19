package Game.Core;

/** Interface that must be implemented by all damageable
 * objects and entities. */
@FunctionalInterface
public interface IDamageable
{
    /** Called when the object gets damaged.*/
    void onReceiveDamage();
}
