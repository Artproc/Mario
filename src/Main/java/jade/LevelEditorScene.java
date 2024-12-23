package Main.java.jade;


import Main.java.components.Sprite;
import Main.java.components.SpriteRenderer;
import Main.java.components.Spritesheet;
import Main.java.jade.util.AssetPool;
import Main.java.renderer.Texture;
import org.joml.Vector2f;

public class LevelEditorScene extends Scene
{

    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene()
    {

    }

    @Override
    public void init()
    {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

       obj1 = new GameObject("Red Square", new Transform(new Vector2f(200,100), new Vector2f(256,256)), 2);
       obj1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/redSquare.png"))));
       addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Green Square", new Transform(new Vector2f(400,100), new Vector2f(256,256)), 2);
        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/greenSquare.png"))));
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

        for (GameObject go : gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }

}
