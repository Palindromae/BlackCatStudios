package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.BlackCore.BTexture;
import com.mygdx.game.BlackCore.BlackScripts;

import java.awt.*;
import java.util.List;


public class Animate extends BlackScripts {

    public BTexture tex;
    private float TimePerFrame;
    private  float accumulated = 0;
    int TexInWidth;
    int TexWidth;
    int TexHeight;
    int TexInHeight;
    public int CurrentTexturesInTextures;

    public int keyFrame = 0;

    public int FrameID = 0;



    public Animate(float timeToNextFrame, String path, int width, int height, int ImagesPerHeight,int ImagesPerWidth){

        tex = new BTexture(path,null,null);
        TimePerFrame = timeToNextFrame;

        TexInWidth = ImagesPerWidth;
        TexInHeight = ImagesPerHeight;


        TexHeight = tex.image.getHeight()/ImagesPerHeight;
        TexWidth =  tex.image.getWidth() / ImagesPerWidth;


        tex.setWrap(Texture.TextureWrap.ClampToEdge);

        CurrentTexturesInTextures = TexInWidth;
        setRegion();


    }


    public void SwapFrameID(FrameIDs frameID){

        FrameID = frameID.ordinal();
    }
    public void SwapTextureToNewFrame()
    {
        keyFrame = (keyFrame+1)%CurrentTexturesInTextures;
        setRegion();
    }

    public void setRegion(){
        tex.texture.setRegion((int)(keyFrame*TexWidth), FrameID *TexHeight,TexWidth,TexHeight);

    }

    @Override
    public void Update(float dt){
        accumulated += dt;

        if(accumulated>=TimePerFrame) {
          accumulated = 0;
            SwapTextureToNewFrame();
        }
    }
}
