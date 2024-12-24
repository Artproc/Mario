package Main.java.jade;


import Main.java.components.*;
import Main.java.jade.util.AssetPool;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

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
        if(levelLoaded)
        {
            this.activeGameObject = gameObjects.getFirst();
            return;
        }

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

       obj1 = new GameObject("Red Square", new Transform(new Vector2f(200,100), new Vector2f(256,256)), 2);
       SpriteRenderer obj1Sprite = new SpriteRenderer();
       obj1Sprite.setColor(new Vector4f(.2f,.3f,.8f,1));
       obj1.addComponent(obj1Sprite);
       obj1.addComponent(new Rigidbody());
       addGameObjectToScene(obj1);
        activeGameObject = obj1;

        GameObject obj2 = new GameObject("Green Square", new Transform(new Vector2f(400,100), new Vector2f(256,256)), 3);
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/greenSquare.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        addGameObjectToScene(obj2);


    }

    public void loadResources()
    {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16,16,26,0));
        AssetPool.getTexture("assets/images/greenSquare.png");
    }


    @Override
    public void update(float dt)
    {

        for (GameObject go : gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui()
    {
        ImGui.begin("My Window");
        ImGui.text("This is some text! Hello ImGui!");
        ImGui.end();
    }
}
