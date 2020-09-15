package tk.zorgatone.engine_tester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import tk.zorgatone.entities.Camera;
import tk.zorgatone.entities.Entity;
import tk.zorgatone.models.TexturedModel;
import tk.zorgatone.render_engine.DisplayManager;
import tk.zorgatone.render_engine.Loader;
import tk.zorgatone.models.RawModel;
import tk.zorgatone.textures.ModelTexture;
import tk.zorgatone.render_engine.Renderer;
import tk.zorgatone.shaders.StaticShader;

public class MainGameLoop {

  public static void main(String[] args) {
    Loader loader = null;
    StaticShader shader = null;

    try {
      DisplayManager.createDisplay();

      // OpenGL expects vertices to be defined counter clockwise by default
      float[] vertices = {
        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,

        -0.5f, 0.5f, 0.5f,
        -0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,

        0.5f, 0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,

        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,

        -0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, 0.5f,

        -0.5f, -0.5f, 0.5f,
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
      };

      float[] textureCoordinates = {
        0, 0,
        0, 1,
        1, 1,
        1, 0,
        0, 0,
        0, 1,
        1, 1,
        1, 0,
        0, 0,
        0, 1,
        1, 1,
        1, 0,
        0, 0,
        0, 1,
        1, 1,
        1, 0,
        0, 0,
        0, 1,
        1, 1,
        1, 0,
        0, 0,
        0, 1,
        1, 1,
        1, 0,
      };

      int[] indices = {
        0, 1, 3,
        3, 1, 2,
        4, 5, 7,
        7, 5, 6,
        8, 9, 11,
        11, 9, 10,
        12, 13, 15,
        15, 13, 14,
        16, 17, 19,
        19, 17, 18,
        20, 21, 23,
        23, 21, 22,
      };

      Camera camera = new Camera();

      loader = new Loader();
      RawModel model = loader.loadToVAO(vertices, textureCoordinates, indices);

      ModelTexture texture = new ModelTexture(
        loader.loadTexture("image")
      );
      TexturedModel texturedModel = new TexturedModel(model, texture);
      Entity entity = new Entity(
        texturedModel,
        new Vector3f(0.0f, 0.0f, -5.0f),
        0.0f, 0.0f, 0.0f,
        1.0f
      );

      shader = new StaticShader();

      final Renderer renderer = new Renderer(shader);

      while (!Display.isCloseRequested()) {
//        entity.increasePosition(0.0f, 0.0f, -0.1f);
        entity.increaseRotation(1.0f, 1.0f, 0.0f);

        camera.move();

        renderer.prepare();

        shader.start();
        shader.loadViewMatrix(camera);
        renderer.render(entity, shader);
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
