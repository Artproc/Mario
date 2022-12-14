package main.components;

import main.renderer.Texture;
import org.joml.Vector2f;


public class Sprite {

    private float width, height;
    private int texID;
    private Texture texture = null;
    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }

    public void setTexture(Texture tex)
    {
        this.texture = tex;
    }

    public void setTexCoords(Vector2f[] texCoords)
    {
        this.texCoords = texCoords;
    }

    public float getWidth(){return width;}

    public float getHeight() { return height; }

    public void setWidth(float width){this.width = width; }

    public void setHeight(float height) { this.height = height;}
    public int getTexID(){return texture == null ? -1 : texture.getID();}
}