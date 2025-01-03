package Main.java.renderer;

import Main.java.jade.Window;
import Main.java.util.AssetPool;
import Main.java.util.Color;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugDraw
{
    private static int MAX_LINES = 500;

    private static List<Line2D> lines = new ArrayList<>();
    // 6 floats per vertex, 2 vertices per line
    private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static Shader shader = AssetPool.getShader("assets/shaders/debugLine2D.glsl");

    private static int vaoID;
    private static int vboID;

    private static boolean started = false;

    public static void start()
    {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        //Enable Vertex array attributes
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glLineWidth(2.0f);
    }

    public static void beginFrame()
    {
        if(!started)
        {
            start();
            started = true;
        }

        //Remove dead lines
        for(int i = 0; i < lines.size(); i++)
        {
            if(lines.get(i).beginFrame() < 0)
            {
                lines.remove(i);
                i--;
            }
        }
    }

    public static void draw()
    {
        if(lines.size() <= 0)return;

        int index = 0;
        for(Line2D line : lines)
        {
            for(int i = 0; i < 2; i++)
            {
                Vector2f position = i == 0 ? line.getFrom() : line.getTo();
                Vector3f color = line.getColor();

                //load position
                vertexArray[index] = position.x;
                vertexArray[index + 1] = position.y;
                vertexArray[index + 2] = -10.0f;

                //load color
                vertexArray[index + 3] = color.x;
                vertexArray[index + 4] = color.y;
                vertexArray[index + 5] = color.z;
                index += 6;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));

        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());

        //bind vao
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        //draw the batch
        glDrawArrays(GL_LINES, 0, lines.size() * 6 * 2);

        //disable location
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

        //unbind shader
        shader.detach();
    }

    //================================================================================================================
    // Add line2D methods
    //================================================================================================================
    public static void addLine2D(Vector2f from, Vector2f to)
    {
        addLine2D(from, to, Color.green3f, 1);
    }

    public static void addLine2D(Vector2f from, Vector2f to, Vector3f color)
    {
        addLine2D(from, to, color, 1);
    }

    public static void addLine2D(Vector2f from, Vector2f to, Vector3f color, int lifetime)
    {
        if(lines.size() >= MAX_LINES) return;
        DebugDraw.lines.add(new Line2D(from, to, color, lifetime));
    }

}
