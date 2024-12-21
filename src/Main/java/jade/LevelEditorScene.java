package Main.java.jade;


import Main.java.components.SpriteRenderer;
import Main.java.jade.util.AssetPool;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene
{


    public LevelEditorScene()
    {

    }

    @Override
    public void init()
    {
        this.camera = new Camera(new Vector2f(-250, 0));

       GameObject obj1 = new GameObject("Mario", new Transform(new Vector2f(100,100), new Vector2f(256,256)));
       obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage.png")));
       addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Goomba", new Transform(new Vector2f(400,100), new Vector2f(256,256)));
        obj2.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/testImage2.png")));
        addGameObjectToScene(obj2);
        loadResources();
    }

    public void loadResources()
    {
        AssetPool.getShader("assets/shaders/default.glsl");
    }


    @Override
    public void update(float dt)
    {
        System.out.println(1.0f / dt + " fps");
        for (GameObject go : gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }

}
