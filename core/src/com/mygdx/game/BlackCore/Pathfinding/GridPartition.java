package com.mygdx.game.BlackCore.Pathfinding;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class GridPartition {

    occupationID[] gridSpaces;


    private int xSize,zSize;
    private float sizeOfGridSpaces;
    private Vector2 gridsWorldPosition_BOTTOMLEFT;


    public GridPartition(GridSettings settings){

        xSize = (int)(settings.XSizeOfFloor / settings.scaleOfGrid);
        zSize = (int)(settings.ZSizeOfFloor / settings.scaleOfGrid);


        gridSpaces = new occupationID[xSize * zSize];

        gridsWorldPosition_BOTTOMLEFT = new Vector2(0,0);
    }


    private int translateToLocal(float i, float offset){
        if (i == -1)
            return -1;

        int b = (int) ((i -offset)/sizeOfGridSpaces);

        return (b<0)? -1 : b; //if outside of bounds default to -1
    }
    public boolean withinBound(int i, int b){
        return  i >= 0 && i<b;
    }
    public boolean withinBounds(int x, int y){

        return withinBound(x,xSize) && withinBound(y, zSize);
    }
    public List<Vector2> pathfindFromWorldCoord(float x, float y, float to_X, float to_Y,PathfindingConfig config){
        return pathfindFrom(translateToLocal(x,gridsWorldPosition_BOTTOMLEFT.x),translateToLocal(y,gridsWorldPosition_BOTTOMLEFT.y),translateToLocal(to_X,gridsWorldPosition_BOTTOMLEFT.x),translateToLocal(to_Y,gridsWorldPosition_BOTTOMLEFT.y),config);
    }
    public List<Vector2> pathfindFrom(int x, int y, int to_X, int to_Y, PathfindingConfig config){

        //IMPLEMENT CLOSEST MOVE and ATTEMPT TO MOVE OFFSCREEN
        int tI = transformToLinearSpace(to_X,to_Y);
        HashMap<Integer, FoundPositions> beenLocations = new HashMap<>();

        PriorityQueue<FoundPositions> sortedList = new PriorityQueue<>();

        FoundPositions found = new FoundPositions(x, y, 0, euclideanDistanceIndex(x,y,to_X,to_Y));

        found.parent = found;



        sortedList.add(found);

        //Some basic variables
        FoundPositions newPosition;
        int newIndex;
        int nX;
        int nY;
        int counter = config.maxIterations;

        do{
            //Take lowest score object
            found = sortedList.poll();


            //Try and move in x+1 direction
            nX = found.x + 1;
            nY = found.y;
            newIndex = transformToLinearSpace(nX, nY);
            if(withinBounds(nX,nY)) { //prevent it going outside grid
                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) { // if positiion already exits see if this path is better (lower cost)
                    beenLocations.get(newIndex).alter(euclideanDistanceIndex(nX, nY, to_X, to_Y), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) { // if it doesnt exist then we should create a new location
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost);
                    newPosition.parent = found;
                    sortedList.add(newPosition);


                }
            }
            nX = found.x - 1;
            nY = found.y;
            newIndex = transformToLinearSpace(nX, nY);
            if(withinBounds(nX,nY)) {

                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) {
                    beenLocations.get(newIndex).alter(euclideanDistanceIndex(nX, nY, to_X, to_Y), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) {
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost);
                    newPosition.parent = found;
                    sortedList.add(newPosition);
                }
            }

            nX = found.x;
            nY = found.y + 1;
            newIndex = transformToLinearSpace(nX, nY);
            if(withinBounds(nX,nY)) {

                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) {
                    beenLocations.get(newIndex).alter(euclideanDistanceIndex(nX, nY, to_X, to_Y), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) {
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost);
                    newPosition.parent = found;
                    sortedList.add(newPosition);
                }
            }

            nX = found.x;
            nY = found.y - 1;
            newIndex = transformToLinearSpace(nX, nY);
            if(withinBounds(nX,nY)) {

                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) {
                    beenLocations.get(newIndex).alter(euclideanDistanceIndex(nX, nY, to_X, to_Y), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) {
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost);
                    newPosition.parent = found;
                    sortedList.add(newPosition);
                }
            }

            counter--;
        }
        while (transformToLinearSpace(found.x, found.y) != tI && counter != 0); //while havent found the goal nor reached max iterations

        System.out.println(counter);
        List l_steps = new LinkedList<Vector2>();

        do{
            l_steps.add(new Vector2(found.x,found.y).add(gridsWorldPosition_BOTTOMLEFT)); //this makes the positions world relative
            found = found.parent;
        } while (found.x != found.parent.x || found.y != found.parent.y);

        Collections.reverse(l_steps);

        return l_steps;



    }
    //this needs to be updated on physics steps for objects with collisions]
    //x y coords need to be translated from world space to grid space first, -1 means either off grid or not relevant
    public void update_entity_on_grid(int past_x, int past_y, int current_x, int current_y){

        if(gridSpaces[transformToLinearSpace(past_x,past_y)] == occupationID.Blocked )
            return; //this is an error state Im not sure what to do here

        if(past_x != -1 && past_y != -1)
            gridSpaces[transformToLinearSpace(past_x,past_y)] = occupationID.Open;

        if(current_x != -1 && current_y != -1){
            if(gridSpaces[transformToLinearSpace(current_x,current_y)] == occupationID.Blocked)
                return; //this is an error state idk what to do here currently
            gridSpaces[transformToLinearSpace(current_x, current_y)] = occupationID.Blocked;

        }
    }

    private FoundPositions createNewPosition(FoundPositions pre, int x, int y, int gx, int gy, float stepSize){
        return new FoundPositions(x, y, pre.pathCost + stepSize,euclideanDistanceIndex(x,y,gx,gy));
    }
    private boolean overridePosition(HashMap<Integer,FoundPositions> map, int t, float p){
        return map.containsKey(t) && map.get(t).score() > p;
    }



    private float euclideanDistanceIndex(int ix, int iy, int jx, int jy){


        return (float) Math.sqrt((Math.pow((ix - jx),2) + Math.pow((iy - jy),2)));

    }



    private int transformToLinearSpace(int x, int z){
        return x + z * zSize;
    }
}
