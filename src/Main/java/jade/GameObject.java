package Main.java.jade;

import Main.java.components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject
{
    private static int ID_COUNTER = 0;
    private int uid = -1;

    String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;



    public GameObject(String name, Transform transform, int zIndex)
    {
        this.name = name;
        this.zIndex = zIndex;
        this.components = new ArrayList<>();
        this.transform = transform;

        this.uid = ID_COUNTER++;
    }

    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        for(Component c : components)
        {
            if(componentClass.isAssignableFrom(c.getClass()))
            {
                    return componentClass.cast(c);
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass)
    {
        for(int i = 0; i < components.size(); i++)
        {
            Component c = components.get(i);
            if(componentClass.isAssignableFrom(c.getClass()))
            {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c)
    {
        c.generateId();
        components.add(c);
        c.gameObject = this;
    }

    public void update(float dt)
    {
        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).update(dt);
        }
    }

    public void start()
    {
        for(int i = 0; i < components.size(); i++)
        {
            components.get(i).start();
        }
    }

    public void imgui()
    {
        for(Component c : components)
        {
            c.imgui();
        }
    }

    public int zIndex()
    {
        return zIndex;
    }
    public static void init(int maxId){ID_COUNTER = maxId;}
    public int getId(){ return uid;}
    public List<Component> getAllComponents(){return components;}
}
