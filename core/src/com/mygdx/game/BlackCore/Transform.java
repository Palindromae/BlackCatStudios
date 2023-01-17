package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Transform {
    public Vector3 position;
    public Quaternion rotation;
    public Vector3 scale;
    Vector3 pastPos;

    public Transform(){
        position = new Vector3(0,0,0);
        rotation = new Quaternion(0,0,0,1);
        scale    = new Vector3(1,1,1);
        pastPos = new Vector3(0,0,0);;

    }


    public void updatePastPos(){
        pastPos = position;
    }

    public void moveToPast(){
        position = pastPos;
    }

    public boolean hasMoved(){
        System.out.println(pastPos);
        System.out.println(position);
        return position != pastPos;
        //return true;
    }

    public Transform(Vector3 pos, Quaternion rotation, Vector3 scale, Vector3 pastPos){
        this.position = pos;
        this.rotation = rotation;
        this.scale = scale;
        this.pastPos = pastPos;
    }

    public void rotateEuler(float x, float y, float z){
        Quaternion rot = new Quaternion();
        rot.setEulerAngles(x,y,z);

        rotateAround(rot);
    }

    public void rotateAroundAxis(Vector3 axis, float angle){
        Quaternion rot = new Quaternion();
        rot.set(axis,angle);
        rotateAround(rot);
    }

    public void rotateAround(Quaternion rot){
        rotation = rot.mul(rotation);
    }

    public void setPosition(int x, int y, int z) {
        this.position.set(x,y,z);
    }

    public void setRotation(int x, int y, int z, int w) {
        this.rotation.set(x,y,z,w);
    }

    public void setScale(int x, int y, int z) {
        this.scale.set(x,y,z);
    }
}
