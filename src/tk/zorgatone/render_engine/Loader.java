package tk.zorgatone.render_engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import tk.zorgatone.models.RawModel;

public class Loader {

  private final List<Integer> vaos;
  private final List<Integer> vbos;
  private final List<Integer> textures;

  public Loader() {
    vaos = new ArrayList<>();
    vbos = new ArrayList<>();
    textures = new ArrayList<>();
  }

  public RawModel loadToVAO(
    float[] positions,
    float[] textureCoordinates,
    float[] normals,
    int[] indices
  ) {
    int vaoID = createVAO();
    bindIndicesBuffer(indices);

    storeDataInAttributeList(0, 3, positions);
    storeDataInAttributeList(1, 2, textureCoordinates);
    storeDataInAttributeList(2, 3, textureCoordinates);
    unbindVAO();

    return new RawModel(vaoID, indices.length);
  }

  public int loadTexture(String fileName) {
    Texture texture = null;
    try {
      texture = TextureLoader.getTexture(
        "PNG", new FileInputStream("res/" + fileName + ".png")
      );
    } catch (IOException e) {
      e.printStackTrace(System.err);
      System.exit(-1);
    }

    int textureID = texture.getTextureID();
    textures.add(textureID);

    return textureID;
  }

  public void cleanUp() {
    for (int vao : vaos) {
      GL30.glDeleteVertexArrays(vao);
    }

    for (int vbo : vbos) {
      GL15.glDeleteBuffers(vbo);
    }

    for (int texture : textures) {
      GL11.glDeleteTextures(texture);
    }
  }

  private int createVAO() {
    int vaoID = GL30.glGenVertexArrays();
    vaos.add(vaoID);
    GL30.glBindVertexArray(vaoID);

    return vaoID;
  }

  @SuppressWarnings("SameParameterValue")
  private void storeDataInAttributeList(
    int attributeNumber, int coordinateSize, float[] data
  ) {
    int vboID = GL15.glGenBuffers();
    vbos.add(vboID);
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); // Specify type of VBO

    FloatBuffer buffer = storeDataInFloatBuffer(data);
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    GL20.glVertexAttribPointer(
      attributeNumber,
      coordinateSize,
      GL11.GL_FLOAT,
      false,
      0,
      0
    );
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
  }

  private void unbindVAO() {
    GL30.glBindVertexArray(0);
  }

  private void bindIndicesBuffer(int[] indices) {
    int vboID = GL15.glGenBuffers();
    vbos.add(vboID);
    // Specify type of VBO
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
    IntBuffer buffer = storeDataInIntBuffer(indices);
    GL15.glBufferData(
      GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW
    );
  }

  private IntBuffer storeDataInIntBuffer(int[] data) {
    IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
    buffer.put(data);
    buffer.flip();

    return buffer;
  }

  private FloatBuffer storeDataInFloatBuffer(float[] data) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
    buffer.put(data);
    buffer.flip(); // Stop writing and prepare to be read

    return buffer;
  }

}
