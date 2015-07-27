package net.spencerhaney.engine;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import net.spencerhaney.game.MyGame;

public class EngineManager
{
    private GLFWErrorCallback errorCallback;
    private ScreenManager screen;
    private Game game;

    public void run(Game game)
    {
        this.game = game;
        try
        {
            init();
            loop();
        }
        finally
        {
            cleanup();
        }
    }

    private void init()
    {
        Logging.fine(this, "Initiating engine.");

        Time.init();
        
        // Setup error callback. Print to System.err
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW
        if (glfwInit() != GL11.GL_TRUE)
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        screen = new ScreenManager();
        screen.createFullWindow(game.getTitle());
    }

    private void loop()
    {
        screen.show();
        screen.init();   
        while (screen.isOpen())
        {
            screen.update();
            game.update();
            game.render();            
        }
    }

    private void cleanup()
    {
        Logging.fine(this, "Stopping engine.");
        game.cleanup();
        glfwTerminate();
        errorCallback.release();
    }

    public static void main(String[] args)
    {
        EngineManager engine = new EngineManager();
        Game game = new MyGame();
        engine.run(game);
    }
}