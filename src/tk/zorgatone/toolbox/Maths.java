package tk.zorgatone.toolbox;

import static java.lang.Math.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public final class Maths {

  private Maths() {
    throw new RuntimeException("Cannot instantiate static class!");
  }

  public static Matrix4f createTransformationMatrix(
    Vector3f translation,
    float rx,
    float ry,
    float rz,
    float scale
  ) {
    Matrix4f matrix = new Matrix4f();

    matrix.setIdentity();
    Matrix4f.translate(translation, matrix, matrix);

    Matrix4f.rotate(
      (float) toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix
    );
    Matrix4f.rotate(
      (float) toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix
    );
    Matrix4f.rotate(
      (float) toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix
    );

    Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);

    return matrix;
  }

  public static Matrix4f createProjectionMatrix(
    float FOV, float NEAR_PLANE, float FAR_PLANE
  ) {
    float aspectRatio = (float) Display.getWidth() / Display.getHeight();
    float yScale = (float) ((1.0 / tan(toRadians(FOV / 2.0))) * aspectRatio);
    float xScale = yScale / aspectRatio;
    float frustumLength = FAR_PLANE - NEAR_PLANE;

    Matrix4f matrix4f  = new Matrix4f();

    matrix4f.m00 = xScale;
    matrix4f.m11 = yScale;
    matrix4f.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
    matrix4f.m23 = -1;
    matrix4f.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
    matrix4f.m33 = 0;

    return matrix4f;
  }

  public static Matrix4f createViewMatrix(
    Vector3f cameraPosition, float pitch, float yaw, float roll
  ) {
    Matrix4f viewMatrix = new Matrix4f();
    viewMatrix.setIdentity();

    Matrix4f.rotate(
      (float) toRadians(pitch),
      new Vector3f(1.0f, 0.0f, 0.0f),
      viewMatrix,
      viewMatrix
    );
    Matrix4f.rotate(
      (float) toRadians(yaw),
      new Vector3f(0.0f, 1.0f, 0.0f),
      viewMatrix,
      viewMatrix
    );

    Vector3f negativeCameraPosition = new Vector3f(
      -cameraPosition.x,
      -cameraPosition.y,
      -cameraPosition.z
    );
    Matrix4f.translate(negativeCameraPosition, viewMatrix, viewMatrix);

    return viewMatrix;
  }

}
