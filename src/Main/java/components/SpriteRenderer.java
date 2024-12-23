package Main.java.components;

import Main.java.jade.Transform;
import Main.java.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component
{

    private Vector4f color;
    private Sprite sprite;

    private Transform lastTransform;
    private boolean isDirty = true;

    public SpriteRenderer(Vector4f color)
    {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite sprite)
    {
        this.sprite = sprite;
        this.color = new Vector4f(1,1,1,1);
    }

    @Override
    public void start()
    {
        lastTransform = gameObject.transform.copy();

    }

    @Override
    public void update(float dt)
    {

        if(!this.lastTransform.equals(gameObject.transform))
        {
            gameObject.transform.copy(lastTransform);
            isDirty = true;
        }
    }

    public Vector4f getColor()
    {
        return color;
    }

    public Texture getTexture()
    {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords()
    {
        return sprite.getTexCoords();
    }
    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
        isDirty = true;
    }

    public void setColor(Vector4f color)
    {
        if(!this.color.equals(color)) {
            this.color.set(color);
            isDirty = true;
        }
    }

    public boolean isDirty(){return this.isDirty;}
    public void setClean(){ isDirty = false;}
}
