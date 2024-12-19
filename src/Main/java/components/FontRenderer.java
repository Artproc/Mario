package Main.java.components;

public class FontRenderer extends Component
{
    @Override
    public void start()
    {
        if(gameObject.getComponent(SpriteRenderer.class) != null)
        {
            System.out.println("I found a Sprite Renderer!!");
        }
    }

    @Override
    public void update(float dt)
    {

    }
}
