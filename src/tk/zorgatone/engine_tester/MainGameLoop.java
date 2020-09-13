package tk.zorgatone.engine_tester;

import org.lwjgl.opengl.Display;
import tk.zorgatone.render_engine.DisplayManager;
import tk.zorgatone.render_engine.Loader;
import tk.zorgatone.render_engine.RawModel;
import tk.zorgatone.render_engine.Renderer;
import tk.zorgatone.shaders.StaticShader;

public class MainGameLoop {

  public static void main(String[] args) {
    Loader loader = null;
    StaticShader shader = null;

    try {
      DisplayManager.createDisplay();

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

      loader = new Loader();
      RawModel model = loader.loadToVAO(vertices);

      final Renderer renderer = new Renderer();
      shader = new StaticShader();

      while (!Display.isCloseRequested()) {
        renderer.prepare();
        // TODO: Game logic

        shader.start();
        renderer.render(model);
        shader.stop();

        DisplayManager.updateDisplay();
      }

      shader.cleanUp();
      loader.cleanUp();
      DisplayManager.closeDisplay();
    } catch (Exception e) {
      if (loader != null) {
        loader.cleanUp();
      }

      if (shader != null) {
        shader.cleanUp();
      }

      DisplayManager.closeDisplay();
      e.printStackTrace(System.err);
      System.exit(1);
    }
  }

}
