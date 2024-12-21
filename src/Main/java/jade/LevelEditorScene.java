package Main.java.jade;


import Main.java.components.Sprite;
import Main.java.components.SpriteRenderer;
import Main.java.components.Spritesheet;
import Main.java.jade.util.AssetPool;
import Main.java.renderer.Texture;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene
{


    public LevelEditorScene()
    {

    }

    @Override
    public void init()
    {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

       GameObject obj1 = new GameObject("Mario", new Transform(new Vector2f(100,100), new Vector2f(256,256)));
       obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
       addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Goomba", new Transform(new Vector2f(400,100), new Vector2f(256,256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(15)));
        addGameObjectToScene(obj2);

    }

    public void loadResources()
    {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16,16,26,0));
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
