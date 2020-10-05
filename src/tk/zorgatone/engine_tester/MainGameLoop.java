package tk.zorgatone.engine_tester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import tk.zorgatone.entities.Camera;
import tk.zorgatone.entities.Entity;
import tk.zorgatone.entities.Light;
import tk.zorgatone.models.TexturedModel;
import tk.zorgatone.render_engine.DisplayManager;
import tk.zorgatone.render_engine.Loader;
import tk.zorgatone.models.RawModel;
import tk.zorgatone.render_engine.ObjLoader;
import tk.zorgatone.textures.ModelTexture;
import tk.zorgatone.render_engine.Renderer;
import tk.zorgatone.shaders.StaticShader;

public class MainGameLoop {

  public static void main(String[] args) {
    Loader loader = null;
    StaticShader shader = null;

    try {
      DisplayManager.createDisplay();

      Camera camera = new Camera();

      loader = new Loader();

      Entity entity = new Entity(
        new TexturedModel(
          ObjLoader.loadObjModel("dragon", loader),
          new ModelTexture(loader.loadTexture("dragonTexture"))
        ),
        new Vector3f(0.0f, 0.0f, -25.0f),
        0.0f, 0.0f, 0.0f,
        1.0f
      );
      Light light = new Light(
        new Vector3f(0.0f, 0.0f, -20.0f),
        new Vector3f(1.0f, 1.0f, 1.0f)
      );

      shader = new StaticShader();

      final Renderer renderer = new Renderer(shader);

      while (!Display.isCloseRequested()) {
//        entity.increasePosition(0.0f, 0.0f, -0.1f);
        entity.increaseRotation(0.0f, 1.0f, 0.0f);
        camera.move();

        renderer.prepare();

        shader.start();
        shader.loadLight(light);
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
