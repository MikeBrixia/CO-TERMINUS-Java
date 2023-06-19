package Game.Graphics;

import Game.Core.GameComponent;
import Game.Core.GameEntity;
import Game.Core.IUpdatable;

/** Component used to animate */
public class AnimatorComponent extends GameComponent implements IUpdatable
{
    public AnimatorComponent(GameEntity owner) {
        super(owner);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void destruction() {

    }
}
