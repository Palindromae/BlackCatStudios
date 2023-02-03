package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.LinkedList;
import java.util.List;

public class Table
{
    public List<Vector3> seats;
    private float radius;
    private float rOffset;
    private Vector3 position;

    int takenSeats = 0;

    /**
     * Create a table given a position and radius
     * @param position
     * @param radius
     * @param offset
     */
    public Table(Vector3 position, float radius, float offset){
    this.radius = radius;
    this.rOffset = offset;
    this.position = position;
    }

    /**
     * get the next seat
     * @return
     */
    public Vector3 GetSeat(){
        return seats.get(takenSeats++);
    }

    public void reset(){
        takenSeats = 0;
        seats.clear();
    }

    /**
     * Defines the seating arrangement based on a list
     * @param seats
     */
    public void DefineSeatingArrangement(List<Vector3> seats){
        this.seats = seats;

        BTexture tex = new BTexture("minceCrate.png",20,20);

        Vector3 pos;

        for (Vector3 p:seats
             ) {
            GameObject obj = new GameObject(new Rectangle(), tex);

            pos = new Vector3(p);
            pos.y = 7;
            obj.transform.position =  pos;
        }


    }

    /**
     * Defines the seating arrangement based on the number of seats/customers
     * @param noSeats
     */
    public void DefineSeatingArrangement(float noSeats){

        seats = new LinkedList<>();

        //assume circular table
        seats = new LinkedList<>();

        double offset = 2 * Math.PI / noSeats;
       // BTexture tex = new BTexture("minceCrate.png",20,20);
        Vector3 pos;

        //GameObject objA = new GameObject(new Rectangle(), tex);
        //objA.transform.position = new Vector3(position).add(new Vector3((float) (radius-rOffset*2), 2F, (float) (radius-rOffset*2)));

        for (int i = 0; i < noSeats; i++) {
            double j = Math.sin(i * offset);
            double k = Math.cos(i * offset);

            pos = new Vector3((float) ((float) (j * radius) + radius -rOffset*2), (float) 0, (float) ((float) (k * radius)+radius-rOffset*2));

            pos.add(position);

            seats.add(pos);

            //   GameObject obj = new GameObject(new Rectangle(), tex);
            //pos.y = 5;
            //obj.transform.position =  pos;




        }
    }
}
