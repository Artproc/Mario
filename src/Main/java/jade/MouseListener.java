package Main.java.jade;

import org.joml.Vector4f;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener
{
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
    private boolean isDragging;

    private MouseListener()
    {
        scrollX = 0.0;
        scrollY = 0.0;
        xPos =    0.0;
        yPos =    0.0;
        lastX =   0.0;
        lastY =   0.0;
        isDragging = false;
    }

    public static MouseListener get()
    {
        if(instance == null){
            instance = new MouseListener();
        }
        return instance;
    }

    public static void mousePosCallback(long window, double xpos, double ypos)
    {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;

        for(int i = 0; i < get().mouseButtonPressed.length; i++)
        {
            if(get().mouseButtonPressed[i]){
                get().isDragging = true;
                break;
            }
        }
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods)
    {
        if(action == GLFW_PRESS) {
            if(button < get().mouseButtonPressed.length)
                get().mouseButtonPressed[button] = true;
        }
        else if(action == GLFW_RELEASE) {
            if(button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset)
    {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame()
    {
        get().scrollX = 0.0;
        get().scrollY = 0.0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static void clear() {
        get().scrollX = 0.0;
        get().scrollY = 0.0;
        get().xPos = 0.0;
        get().yPos = 0.0;
        get().lastX = 0.0;
        get().lastY = 0.0;
        //get().mouseButtonDown = 0;
        get().isDragging = false;
        Arrays.fill(get().mouseButtonPressed, false);
    }

    public static float getX(){return (float)get().xPos;}

    public static float getY(){return (float)get().yPos; }

    public static float getOrthoX()
    {
        float currentX = getX();
        currentX = (currentX / (float)Window.getWidth()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);
        tmp.mul(Window.getScene().camera().getInverseProjection()).mul(Window.getScene().camera().getInverseView());
        currentX = tmp.x;

        return currentX;
    }

    public static float getOrthoY()
    {
        float currentY = Window.getHeight() - getY();
        currentY = (currentY/(float)Window.getHeight()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);
        tmp.mul(Window.getScene().camera().getInverseProjection()).mul(Window.getScene().camera().getInverseView());
        currentY = tmp.y;
        return currentY;
    }

    public static float getDX(){return (float)(get().lastX - get().xPos);}

    public static float getDY(){return (float)(get().lastY - get().yPos);}

    public static float getScrollX(){return (float)(get().scrollX );}

    public static float getScrollY(){return (float)(get().scrollY );}

    public static boolean isDragging(){return get().isDragging;}

    public static boolean mouseButtonDown(int button)
    {
        if(button < get().mouseButtonPressed.length)
            return get().mouseButtonPressed[button];
        else return false;
    }
}
