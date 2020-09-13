package tk.zorgatone.engine_tester;

import org.lwjgl.opengl.Display;
import tk.zorgatone.models.TexturedModel;
import tk.zorgatone.render_engine.DisplayManager;
import tk.zorgatone.render_engine.Loader;
import tk.zorgatone.models.RawModel;
import tk.zorgatone.textures.ModelTexture;
import tk.zorgatone.render_engine.Renderer;
import tk.zorgatone.shaders.ShaderProgram;
import tk.zorgatone.shaders.StaticShader;

public class MainGameLoop {

  public static void main(String[] args) {
    Loader loader = null;
    ShaderProgram shader = null;

    try {
      DisplayManager.createDisplay();

      // OpenGL expects vertices to be defined counter clockwise by default
      final float[] vertices = {
        -0.5f, 0.5f, 0f,  // V0
        -0.5f, -0.5f, 0f, // V1
        0.5f, -0.5f, 0f,  // V2
        0.5f, 0.5f, 0f,  // V3
      };

      final float[] textureCoords = {
        0, 0, // V0
        0, 1, // V1
        1, 1, // V2
        1, 0, // V3
      };

      final int[] indices = {
        0, 1, 3, // Top left triangle (V0, V1, V3)
        3, 1, 2  // Bottom right triangle (V3, V1, V2)
      };

      final Renderer renderer = new Renderer();

      loader = new Loader();
      RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
      ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
      TexturedModel texturedModel = new TexturedModel(model, texture);

      shader = new StaticShader();

      while (!Display.isCloseRequested()) {
        // TODO: Game logic
        renderer.prepare();

        shader.start();
        renderer.render(texturedModel);
        shader.stop();

        DisplayManager.updateDisplay();
      }

      loader.cleanUp();
      shader.cleanUp();
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
      System.exit(-1);
    }
  }

}
