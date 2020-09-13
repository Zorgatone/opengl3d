package tk.zorgatone.models;

import tk.zorgatone.textures.ModelTexture;

public class TexturedModel {

  private final RawModel rawModel;
  private final ModelTexture texture;

  public TexturedModel(RawModel model, ModelTexture texture) {
    rawModel = model;
    this.texture = texture;
  }

  public RawModel getRawModel() {
    return rawModel;
  }

  public ModelTexture getTexture() {
    return texture;
  }

}
