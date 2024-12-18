package Main.java.jade;

public class LevelScene extends Scene
{
    public LevelScene()
    {
        System.out.println("Inside Level Scene");
    }

    @Override
    public void update(float dt)
    {
        Window.Get().r = 1.0f;
        Window.Get().g = 1.0f;
        Window.Get().b = 1.0f;
    }


}
