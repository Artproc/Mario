package Main.java.components;

public class SpriteRenderer extends Component
{

    private boolean firstTime = true;

    @Override
    public void start()
    {
        System.out.println("I am starting");
    }

    @Override
    public void update(float dt)
    {
        if(firstTime) {
            System.out.println("I am Updating");
            firstTime = false;
        }
    }
}
