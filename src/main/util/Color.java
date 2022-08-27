package main.util;

import org.joml.Vector3f;

public class Color
{
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;

    public static final Vector3f black = 	new Vector3f(0, 0, 0);
    public static final Vector3f white = 	new Vector3f(1, 1, 1);
    public static final Vector3f red = 		new Vector3f(1, 0, 0);
    public static final Vector3f blue = 	new Vector3f(0, 0, 1);
    public static final Vector3f green = 	new Vector3f(0, 1, 0);
    public static final Vector3f grey = 	new Vector3f(0.5f, 0.5f, 0.5f);
    public static final Vector3f wine =		new Vector3f(0.5f, 0, 0);
    public static final Vector3f forrest = 	new Vector3f(0, 0.5f, 0);
    public static final Vector3f marine = 	new Vector3f(0, 0, 0.5f);
    public static final Vector3f yellow = 	new Vector3f(1, 1, 0);
    public static final Vector3f cyan = 	new Vector3f(0, 1, 1);
    public static final Vector3f magenta = 	new Vector3f(1, 0, 1);

    //Color constructor with alpha
    public Color(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    //Color constructor without alpha
    public Color(float r, float g, float b)
    {
        this(r,g,b,1.0f);
    }

    public boolean Compare(Color c)
    {
        return c.r == r && c.g == g && c.b == b && c.a == a;
    }

    //Returns a string that contains the color values
    public String ToShortString() {return r + "," + g + "," + b + "," + a;}
    public String ToString() {return "(" + r + ", " + g + ", " + b + ", " + a + ")";}
}
