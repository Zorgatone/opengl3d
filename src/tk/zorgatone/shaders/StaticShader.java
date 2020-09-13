package tk.zorgatone.shaders;

public class StaticShader extends ShaderProgram {

  private static final String VERTEX_FILE = "src/tk/zorgatone/shaders/shader.vert";
  private static final String FRAGMENT_FILE = "src/tk/zorgatone/shaders/shader.frag";

  public StaticShader() {
    super(VERTEX_FILE, FRAGMENT_FILE);
  }

  @Override
  protected void bindAttributes() {
    bindAttribute(0, "position");
    bindAttribute(1, "textureCoords");
  }

}
