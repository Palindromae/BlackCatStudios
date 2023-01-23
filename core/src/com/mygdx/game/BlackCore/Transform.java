package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.Pathfinding.GridPartition;

public class Transform {
    //Becareful with the Y axis, works as a layer system due to the limited rendering system, any object with y1>y2 for o1 o2, will be rendered before o2
    public Vector3 position;
    public Quaternion rotation;
    public Vector3 scale;
    Vector3 pastPos;

    public GridPartition gridPartition;

    public Transform(){
        position = new Vector3(0,0,0);
        rotation = new Quaternion(0,0,0,1);
        scale    = new Vector3(1,1,1);
        pastPos = new Vector3(0,0,0);;

    }


    public void updatePastPos(){
        pastPos.x = position.x;
        pastPos.y = position.y;
        pastPos.z = position.z;
    }

    public void moveToPast(){
       UpdatePosition(pastPos);

    }

    public void UpdatePosition(Vector3 a){

        gridPartition.update_entity_on_grid_from_world((int)position.x,(int)position.z,(int)a.x,(int)a.z);

        position.set(a);
    }

    public boolean hasMoved(){
        //System.out.println(pastPos);
        //System.out.println(position);
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
