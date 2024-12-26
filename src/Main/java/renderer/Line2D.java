package Main.java.renderer;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Line2D
{
    private Vector2f from;
    private Vector2f to;
    private Vector3f color;
    private int lifetime;

    public Line2D(Vector2f from, Vector2f to, Vector3f color, int lifetime)
    {
        this.color = color;
        this.from = from;
        this.lifetime = lifetime;
        this.to = to;
    }

    public int beginFrame()
    {
        lifetime--;
        return lifetime;
    }

    public Vector2f getTo()
    {
        return to;
    }

    public Vector2f getFrom()
    {
        return from;
    }

    public Vector3f getColor()
    {
        return color;
    }
}
