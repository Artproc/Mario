package Main.java.util;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Color
{
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;


    public static final Vector3f black3f = 	new Vector3f(0, 0, 0);
    public static final Vector3f white3f = 	new Vector3f(1, 1, 1);
    public static final Vector3f red3f = 		new Vector3f(1, 0, 0);
    public static final Vector3f blue3f = 	new Vector3f(0, 0, 1);
    public static final Vector3f green3f = 	new Vector3f(0, 1, 0);
    public static final Vector3f grey3f = 	new Vector3f(0.5f, 0.5f, 0.5f);
    public static final Vector3f wine3f =		new Vector3f(0.5f, 0, 0);
    public static final Vector3f forrest3f = 	new Vector3f(0, 0.5f, 0);
    public static final Vector3f marine3f = 	new Vector3f(0, 0, 0.5f);
    public static final Vector3f yellow3f = 	new Vector3f(1, 1, 0);
    public static final Vector3f cyan3f = 	new Vector3f(0, 1, 1);
    public static final Vector3f magenta3f = 	new Vector3f(1, 0, 1);
    public static final Vector3f skyblue3f = 	new Vector3f(.53f, 0.81f, 0.92f);

    public static final Vector4f black4f = 	new Vector4f(0, 0, 0,1);
    public static final Vector4f white4f = 	new Vector4f(1, 1, 1,1);
    public static final Vector4f red4f = 		new Vector4f(1, 0, 0,1);
    public static final Vector4f blue4f = 	new Vector4f(0, 0, 1,1);
    public static final Vector4f green4f = 	new Vector4f(0, 1, 0,1);
    public static final Vector4f grey4f = 	new Vector4f(0.5f, 0.5f, 0.5f,1);
    public static final Vector4f wine4f =		new Vector4f(0.5f, 0, 0,1);
    public static final Vector4f forrest4f = 	new Vector4f(0, 0.5f, 0,1);
    public static final Vector4f marine4f = 	new Vector4f(0, 0, 0.5f,1);
    public static final Vector4f yellow4f = 	new Vector4f(1, 1, 0,1);
    public static final Vector4f cyan4f = 	new Vector4f(0, 1, 1,1);
    public static final Vector4f magenta4f = 	new Vector4f(1, 0, 1,1);
    public static final Vector4f skyblue4f = 	new Vector4f(.53f, 0.81f, 0.92f, 1);


    //Color constructor with alpha
    public Color(float r, float g, float b, float a)
    {
        new Vector4f(r,g,b,a);
    }

    //Color constructor without alpha
    public Color(float r, float g, float b)
    {
        new Vector3f(r,g,b);
    }

}
