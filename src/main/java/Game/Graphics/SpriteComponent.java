package Game.Graphics;
import Game.Beans.IUpdatable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


/** Component used to handle sprite creation and
 *  drawing policies. */
public class SpriteComponent implements IUpdatable
{
    private Sprite sprite;

    public SpriteComponent(String path)
    {
        Texture image = new Texture(path);
        this.sprite = new Sprite(image);
    }

    public SpriteComponent(Sprite sprite)
    {
        this.sprite = sprite;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {

    }
}
