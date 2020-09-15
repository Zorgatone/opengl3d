package tk.zorgatone.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {

  private static final String VERTEX_FILE = "src/tk/zorgatone/shaders/shader.vert";
  private static final String FRAGMENT_FILE = "src/tk/zorgatone/shaders/shader.frag";

  private int locationTransformationMatrix;

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
  }

  public void loadTransformationMatrix(Matrix4f matrix) {
    loadMatrix(locationTransformationMatrix, matrix);
  }

}
