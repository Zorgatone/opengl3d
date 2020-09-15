package tk.zorgatone.render_engine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import tk.zorgatone.entities.Entity;
import tk.zorgatone.models.RawModel;
import tk.zorgatone.models.TexturedModel;
import tk.zorgatone.shaders.StaticShader;
import tk.zorgatone.toolbox.Maths;

public class Renderer {

  private static final float FOV = 70.0f;
  private static final float NEAR_PLANE = 0.1f;
  private static final float FAR_PLANE = 1000f;

  @SuppressWarnings("FieldCanBeLocal")
  private final Matrix4f projectionMatrix;

  public Renderer(StaticShader shader) {
    projectionMatrix = Maths.createProjectionMatrix(FOV, NEAR_PLANE, FAR_PLANE);

    shader.start();
    shader.loadProjectionMatrix(projectionMatrix);
    shader.stop();
  }

  public void prepare() {
    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    GL11.glClearColor(1, 0, 0,  1);
  }

  public void render(Entity entity, StaticShader shader) {
    TexturedModel model = entity.getModel();
    RawModel rawModel = model.getRawModel();

    GL30.glBindVertexArray(rawModel.getVaoID());
    GL20.glEnableVertexAttribArray(0);
    GL20.glEnableVertexAttribArray(1);

    Matrix4f transformationMatrix = Maths.createTransformationMatrix(
      entity.getPosition(),
      entity.getRotX(),
      entity.getRotY(),
      entity.getRotZ(),
      entity.getScale()
    );

    shader.loadTransformationMatrix(transformationMatrix);

    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
    GL11.glDrawElements(
      GL11.GL_TRIANGLES,
      rawModel.getVertexCount(),
      GL11.GL_UNSIGNED_INT,
      0
    );

    GL20.glDisableVertexAttribArray(0);
    GL20.glDisableVertexAttribArray(1);
    GL30.glBindVertexArray(0);
  }

}
