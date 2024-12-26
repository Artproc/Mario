package Main.java.scenes;

import Main.java.components.Component;
import Main.java.components.ComponentDeserializer;
import Main.java.jade.Camera;
import Main.java.jade.GameObject;
import Main.java.jade.GameObjectDeserializer;
import Main.java.renderer.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene
{
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;

    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject = null;
    protected boolean levelLoaded = false;

    public Scene()
    {

    }

    public void init()
    {

    }

    public void start()
    {
        for (GameObject go : gameObjects)
        {
            go.start();
            renderer.add(go);
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go)
    {
        if(!isRunning)
        {
            gameObjects.add(go);
        }
        else
        {
            gameObjects.add(go);
            go.start();
            renderer.add(go);
        }
    }

    public abstract void update(float dt);

    public Camera camera(){return camera;}

    public void sceneImgui()
    {
        if(activeGameObject != null)
        {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }
        imgui();
    }

    public void imgui()
    {

    }

    public void saveExit()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();

        try{
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void load()
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(GameObject.class, new GameObjectDeserializer())
                .create();
        String infile = "";

        try{
            infile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!infile.equals(""))
        {
            int maxGoId = -1;
            int maxCompId = -1;
            GameObject[] objs = gson.fromJson(infile, GameObject[].class);
            for(int i = 0; i < objs.length; i++)
            {
                addGameObjectToScene(objs[i]);

                for(Component c : objs[i].getAllComponents())
                {
                    if(c.getUid() > maxCompId)
                    {
                        maxCompId = c.getUid();
                    }
                }
                if(objs[i].getId() > maxGoId)
                {
                    maxGoId = objs[i].getId();
                }
            }
            maxGoId++;
            maxCompId++;
            GameObject.init(maxGoId);
            Component.init(maxCompId);
            this.levelLoaded = true;
        }
    }


}
