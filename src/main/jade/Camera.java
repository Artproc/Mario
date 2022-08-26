package main.jade;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera
{
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Matrix4f inverseProjectionMatrix;
    private Matrix4f inverseViewMatrix;
    public Vector2f position;

    public Camera(Vector2f position)
    {
        this.position = position;
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        inverseProjectionMatrix = new Matrix4f();
        inverseViewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection()
    {
        projectionMatrix.identity();
        projectionMatrix.ortho(0.0f, 32.0f * 40.0f,0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
        projectionMatrix.invert(inverseProjectionMatrix);
    }

    public Matrix4f getViewMatrix()
    {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                                            cameraFront.add(position.x, position.y, 0.0f), cameraUp);
        viewMatrix.invert(inverseViewMatrix);
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix(){return projectionMatrix;}

    public Matrix4f getInverseProjectionMatrix(){return inverseProjectionMatrix;}

    public Matrix4f getInverseViewMatrix(){return inverseViewMatrix;}
}
