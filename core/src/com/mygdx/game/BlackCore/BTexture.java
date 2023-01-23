package com.mygdx.game.BlackCore;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class BTexture {

    //This is the actual image
   public Texture image;
   //this is the region that the texture will use
   public TextureRegion texture;
   public String name;

   public int width,height;

   public Vector3 textureOrigin = Vector3.Zero.setZero();

//Setting width or height to -1 will result in capturing the width or height exactly
    public BTexture(String path, Integer width, Integer height){

        loadTexture(path,width,height);
    }

   public void loadTexture(String path,Integer width, Integer height){
        if(image != null){
            image.dispose();

        }

       image = new Texture(path);

       this.width = (width == null) ? image.getWidth() : width;
       this.height = (height == null) ? image.getHeight() : height;

       texture = new TextureRegion(image, this.width,this.height);
   }

   public void setWrap(Texture.TextureWrap U, Texture.TextureWrap V){
        image.setWrap(U,V);
   }

   public void setWrap(Texture.TextureWrap UV){
        setWrap(UV,UV);
   }

   public void setFilters(Texture.TextureFilter min, Texture.TextureFilter max){
        image.setFilter(min, max);
   }
   public void dispose(){
       image.dispose();
   }

   public Integer getWidth(){
        return width;
   }

   public Integer getHeight(){
        return height;
   }
}
