package com.mygdx.game.BlackCore;

import com.badlogic.gdx.Gdx;
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

   private Texture.TextureWrap _U,_V;

   public Vector3 textureOrigin = Vector3.Zero.setZero();

//Setting width or height to -1 will result in capturing the width or height exactly
    public BTexture(String path, Integer width, Integer height){

        loadTexture(path,width,height,1,1);
    }

    public BTexture(String path, Integer width, Integer height, int Repeats){

        loadTexture(path,width,height, Repeats,Repeats);
    }

    public BTexture(String path, Integer width, Integer height, int RepeatsU,int RepeatsV){

        loadTexture(path,width,height, RepeatsU,RepeatsV);
    }
   public void loadTexture(String path,Integer width, Integer height, int RepeatsU, int RepeatsV){
        if(image != null){
            image.dispose();

        }

       image = new Texture(Gdx.files.internal(path));

        int RV = 1, RU = 1;

        if(_V != Texture.TextureWrap.ClampToEdge)
            RV = RepeatsV;
        if(_U != Texture.TextureWrap.ClampToEdge)
            RU = RepeatsU;

       this.width = (width == null) ? image.getWidth() * RU: width* RU;
       this.height = (height == null) ? image.getHeight() * RV: height* RV;

       texture = new TextureRegion(image, this.width,this.height);
   }

   public void setWrap(Texture.TextureWrap U, Texture.TextureWrap V){

       _U = U;
       _V = V;
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

    /**
     * Returns the width of the texture region
     * @return width of the texture region
     */
   public Integer getWidth(){
        return  width;
   }

    /**
     * Returns the height of the texture region
     * @return height of the texture region
     */
   public Integer getHeight(){
        return height;
   }
}
