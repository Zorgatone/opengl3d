package tk.zorgatone.shaders;

import org.lwjgl.util.vector.Matrix4f;
import tk.zorgatone.entities.Camera;
import tk.zorgatone.toolbox.Maths;

public class StaticShader extends ShaderProgram {

  private static final String VERTEX_FILE = "src/tk/zorgatone/shaders/shader.vert";
  private static final String FRAGMENT_FILE = "src/tk/zorgatone/shaders/shader.frag";

  private int locationTransformationMatrix;
  private int locationProjectionMatrix;
  private int locationViewMatrix;

  public StaticShader() {
    super(VERTEX_FILE, FRAGMENT_FILE);
  }

  @Override
  protected void bindAttributes() {
    bindAttribute(0, "position");
    bindAttribute(1, "textureCoords");
  }

  @Override
  protected void getAllUniformLocations() {
    locationTransformationMatrix = getUniformLocation("transformationMatrix");
    locationProjectionMatrix = getUniformLocation("projectionMatrix");
    locationViewMatrix = getUniformLocation("viewMatrix");
  }

  public void loadTransformationMatrix(Matrix4f matrix) {
    loadMatrix(locationTransformationMatrix, matrix);
  }

  public void loadViewMatrix(Camera camera) {
    Matrix4f viewMatrix = Maths.createViewMatrix(
      camera.getPosition(),
      camera.getPitch(),
      camera.getYaw(),
      camera.getRoll()
    );

    loadMatrix(locationViewMatrix, viewMatrix);
  }

  public void loadProjectionMatrix(Matrix4f projection) {
    loadMatrix(locationProjectionMatrix, projection);
  }

}
