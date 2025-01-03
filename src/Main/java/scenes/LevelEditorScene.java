package Main.java.scenes;


import Main.java.components.*;
import Main.java.jade.Camera;
import Main.java.jade.GameObject;
import Main.java.jade.Prefabs;
import Main.java.jade.Transform;
import Main.java.renderer.DebugDraw;
import Main.java.util.AssetPool;
import Main.java.util.Color;
import Main.java.util.Settings;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene
{

    private GameObject obj1;
    private Spritesheet sprites;

   GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()),0);

    public LevelEditorScene()
    {

    }

    @Override
    public void init()
    {
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png");

        if(levelLoaded)
        {
            this.activeGameObject = gameObjects.getFirst();
            return;
        }



//       obj1 = new GameObject("Red Square", new Transform(new Vector2f(200,100), new Vector2f(256,256)), 2);
//       SpriteRenderer obj1Sprite = new SpriteRenderer();
//       obj1Sprite.setColor(new Vector4f(.2f,.3f,.8f,1));
//       obj1.addComponent(obj1Sprite);
//       obj1.addComponent(new Rigidbody());
//       addGameObjectToScene(obj1);
//        activeGameObject = obj1;
//
//        GameObject obj2 = new GameObject("Green Square", new Transform(new Vector2f(400,100), new Vector2f(256,256)), 3);
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        Sprite obj2Sprite = new Sprite();
//        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/greenSquare.png"));
//        obj2SpriteRenderer.setSprite(obj2Sprite);
//        obj2.addComponent(obj2SpriteRenderer);
//        addGameObjectToScene(obj2);


    }

    public void loadResources()
    {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
                        16,16,81,0));
        AssetPool.getTexture("assets/images/greenSquare.png");
    }



    @Override
    public void update(float dt)
    {
        levelEditorStuff.update(dt);

        for (GameObject go : gameObjects) {
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui()
    {
        ImGui.begin("My Window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float window2 = windowPos.x + windowSize.x;
        for(int i = 0; i < sprites.size(); i++)
        {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y))
            {
                GameObject object = Prefabs.generateSpriteObject(sprite, Settings.GRID_WIDTH, Settings.GRID_HEIGHT);
                //Attach to mouse cursor
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if(i + 1 < sprites.size() && nextButtonX2 < window2)
            {
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
