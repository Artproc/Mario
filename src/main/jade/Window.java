package main.jade;

import main.renderer.*;
import main.scenes.LevelEditorScene;
import main.scenes.LevelScene;
import main.scenes.Scene;
import main.util.AssetPool;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static final double MONITOR_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final double MONITOR_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private double width, height;
    private final String title;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;

    public float r, g, b, a;

    private static Window window = null;

    private static Scene currentScene;
    private static FrameBuffer frameBuffer;
    private static PickingTexture pickingTexture;

    private Window() {
        width = MONITOR_WIDTH;
        height = MONITOR_HEIGHT;

        this.title = "Mario";
        r = .1f;
        b = .99f;
        g = .71f;
        a = 1;

    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }

        currentScene.load();
        currentScene.init();
        currentScene.start();
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene() {
        return Window.currentScene;
    }



    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);


        // Create the window
        glfwWindow = glfwCreateWindow((int) width, (int)height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        imguiLayer = new ImGuiLayer(glfwWindow);
        imguiLayer.initImGui();

        frameBuffer = new FrameBuffer((int) MONITOR_WIDTH, (int) MONITOR_HEIGHT);
        pickingTexture = new PickingTexture((int) MONITOR_WIDTH, (int) MONITOR_HEIGHT);
        glViewport(0,0, (int) MONITOR_WIDTH, (int) MONITOR_HEIGHT);

        Window.changeScene(0);
    }

    public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        Shader defaultShader = AssetPool.getShader("assets/shaders/default.glsl");
        Shader pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Render pass 1. Render to picking texture
            glDisable(GL_BLEND);
            //pickingTexture.enableWriting();

            glViewport(0,0, (int) MONITOR_WIDTH, (int) MONITOR_HEIGHT);
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Renderer.bindShader(pickingShader);
            currentScene.render();

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                int x = (int)MouseListener.getScreenX();
                int y = (int)MouseListener.getScreenY();
                System.out.println(pickingTexture.readPixel(x, y));
            }
            if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(glfwWindow,true);
            }

            //pickingTexture.disableWriting();
            glEnable(GL_BLEND);

            // Render pass 2. Render actual game
            DebugDraw.beginFrame();

            frameBuffer.bind();
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                DebugDraw.draw();
                Renderer.bindShader(defaultShader);
                currentScene.update(dt);
                currentScene.render();
            }
            frameBuffer.unbind();

            this.imguiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow);

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

        currentScene.saveExit();
    }

    public static double getWidth() {
        return get().width;
    }

    public static double getHeight() {
        return get().height;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public static FrameBuffer getFramebuffer() {
        return frameBuffer;
    }

    public static float getTargetAspectRatio() {

        return (float) (MONITOR_WIDTH / MONITOR_HEIGHT);
    }
}
