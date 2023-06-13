package Game;

import Game.Core.GameApplication;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main
{
    private static Lwjgl3Application application;

    public static void main (String[] arg)
    {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1000, 900);
        config.setIdleFPS(60);
        config.useVsync(true);
        config.setTitle("CO-TERMINUS");
        application = new Lwjgl3Application(new GameApplication(), config);
    }

    /** Get the application instance object reference. */
    public static Lwjgl3Application getApplication() {
        return application;
    }
}