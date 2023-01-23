package com.mygdx.game.BlackCore;

import com.badlogic.gdx.math.Vector3;

import java.util.LinkedList;
import java.util.List;

public class Table
{
    public List<Vector3> seats;
    private float radius;
    private Vector3 position;

    int takenSeats = 0;

    public Table(Vector3 position, float radius){
    this.radius = radius;
    this.position = position;
    }


    public Vector3 GetSeat(){
        return seats.get(takenSeats++);
    }

    public void reset(){
        takenSeats = 0;
        seats.clear();
    }
    public void DefineSeatingArrangement(List<Vector3> seats){
        this.seats = seats;
    }
    public void DefineSeatingArrangement(float noSeats){

        //assume circular table
        seats = new LinkedList<>();

        double offset = 2 * Math.PI / noSeats;

        for (int i = 0; i < noSeats; i++) {
            double j = Math.sin(i * offset);
            double k = Math.cos(i * offset);

            seats.add(new Vector3((float) (j * radius), (float) 0, (float) (k * radius)));

        }
    }
}
