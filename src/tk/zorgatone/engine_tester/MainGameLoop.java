package tk.zorgatone.engine_tester;

import org.lwjgl.opengl.Display;
import tk.zorgatone.render_engine.DisplayManager;
import tk.zorgatone.render_engine.Loader;
import tk.zorgatone.render_engine.RawModel;
import tk.zorgatone.render_engine.Renderer;

public class MainGameLoop {

  public static void main(String[] args) {
    Loader loader = null;

    try {
      DisplayManager.createDisplay();

      loader = new Loader();
      final Renderer renderer = new Renderer();

      // OpenGL expects vertices to be defined counter clockwise by default
      final float[] vertices = {
        // Left bottom triangle
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        // Right top triangle
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f,
        -0.5f, 0.5f, 0f
      };

      RawModel model = loader.loadToVAO(vertices);

      while (!Display.isCloseRequested()) {
        renderer.prepare();
        // TODO: Game logic

        // White rectangle won't render on osx without a shader
        renderer.render(model);

        DisplayManager.updateDisplay();
      }

      loader.cleanUp();
      DisplayManager.closeDisplay();
    } catch (Exception e) {
      if (loader != null) {
        loader.cleanUp();
      }

      DisplayManager.closeDisplay();
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

}
