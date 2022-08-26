package main.renderer;

import main.components.SpriteRenderer;
import main.jade.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer
{
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer()
    {
        batches = new ArrayList<>();
    }

    public void add(GameObject go)
    {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if(spr != null){
            add(spr);
        }
    }

    public void add(SpriteRenderer sprite)
    {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom() && sprite.gameObject.zIndex() == batch.zIndex()) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }
        if(!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.gameObject.zIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
            Collections.sort(batches);
        }
    }//end of add()

    public void render()
    {
        for(RenderBatch batch : batches){
            batch.render();
        }
    }
}//end of Renderer Class
