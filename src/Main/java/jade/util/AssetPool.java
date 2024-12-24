package Main.java.jade.util;

import Main.java.components.Spritesheet;
import Main.java.renderer.Shader;
import Main.java.renderer.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool
{
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, Spritesheet> spritesheets = new HashMap<>();

    public static Shader getShader(String resourceName)
    {
        File file = new File(resourceName);
        if(shaders.containsKey(file.getAbsolutePath()))
        {
            return shaders.get(file.getAbsolutePath());
        }
        else {
            Shader shader = new Shader(resourceName);
            shader.compile();
            shaders.put(file.getAbsolutePath(), shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName)
    {
        File file = new File(resourceName);
        if(textures.containsKey(file.getAbsolutePath()))
        {
            return textures.get(file.getAbsolutePath());
        }
        else
        {
            Texture texture = new Texture();
            texture.init(resourceName);
            textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpritesheet(String resourceName, Spritesheet spritesheet)
    {
        File file = new File(resourceName);
        if(!spritesheets.containsKey(file.getAbsolutePath()))
        {
            spritesheets.put(file.getAbsolutePath(), spritesheet);
        }
    }

    public static Spritesheet getSpritesheet(String resourceName)
    {
        File file = new File(resourceName);
        if(!spritesheets.containsKey(file.getAbsolutePath()))
        {
            assert false : "Error: Tried to access spritesheet '" + resourceName + "'but it hasn't been added to assetpool!";
        }
        return AssetPool.spritesheets.getOrDefault(file.getAbsolutePath(), null);
    }
}
