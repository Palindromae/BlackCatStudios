package com.mygdx.game.BlackCore.Pathfinding;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.*;

public class GridPartition {

    occupationID[] gridSpaces;


    private int xSize,zSize;
    public float sizeOfGridSpaces;
    private Vector2 gridsWorldPosition_BOTTOMLEFT;


    public GridPartition(GridSettings settings){

        xSize = (int)(settings.XSizeOfFloor / settings.scaleOfGrid);
        zSize = (int)(settings.ZSizeOfFloor / settings.scaleOfGrid);

        sizeOfGridSpaces = settings.scaleOfGrid;
        gridSpaces = new occupationID[xSize * zSize];

        for (int i = 0; i < gridSpaces.length;i++)
            gridSpaces[i] = occupationID.Open;

        gridsWorldPosition_BOTTOMLEFT = new Vector2(0,0);
    }

    public Vector3 translateToLocal(int x, int z){
        return new Vector3(translateToLocal(x,gridsWorldPosition_BOTTOMLEFT.x), 0, translateToLocal(z,gridsWorldPosition_BOTTOMLEFT.y));
    }

    private int translateToLocal(float i, float offset){
        if (i == -1)
            return -1;

        int b = (int) Math.round((i -offset)/sizeOfGridSpaces);

        return (b<0)? -1 : b; //if outside of bounds default to -1
    }

    private int translateToLocalUpper(float i, float offset){
        if (i == -1)
            return -1;

        int b = (int) Math.ceil((i -offset)/sizeOfGridSpaces);

        return (b<0)? -1 : b; //if outside of bounds default to -1
    }
    public boolean withinBound(int i, int b){
        return  i >= 0 && i<b;
    }
    public boolean withinBounds(int x, int y){

        return withinBound(x,xSize) && withinBound(y, zSize);
    }
    public List<Vector2> pathfindFromWorldCoord(float x, float y, float to_X, float to_Y,PathfindingConfig config, DistanceCalculator calc){
        return pathfindFrom(translateToLocal(x,gridsWorldPosition_BOTTOMLEFT.x),translateToLocal(y,gridsWorldPosition_BOTTOMLEFT.y),translateToLocal(to_X,gridsWorldPosition_BOTTOMLEFT.x),translateToLocal(to_Y,gridsWorldPosition_BOTTOMLEFT.y),config,calc);
    }
    public List<Vector2> pathfindFrom(int x, int y, int to_X, int to_Y, PathfindingConfig config,DistanceCalculator calc){


        //IMPLEMENT CLOSEST MOVE and ATTEMPT TO MOVE OFFSCREEN
        int tI = transformToLinearSpace(to_X,to_Y);
        System.out.println(to_X + " : " + to_Y + " : : "+ gridSpaces[tI] + " : " + tI);

        HashMap<Integer, FoundPositions> beenLocations = new HashMap<>();

        PriorityQueue<FoundPositions> sortedList = new PriorityQueue<>();

        FoundPositions found = new FoundPositions(x, y, 0, DistanceIndex(x,y,to_X,to_Y,calc));

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
            if(withinBounds(nX,nY)&& gridSpaces[newIndex] == occupationID.Open) { //prevent it going outside grid
                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) { // if positiion already exits see if this path is better (lower cost)
                    beenLocations.get(newIndex).alter(DistanceIndex(nX, nY, to_X, to_Y,calc), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) { // if it doesnt exist then we should create a new location
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost, config.DistanceCost,calc);
                    newPosition.parent = found;
                    sortedList.add(newPosition);
                    beenLocations.put(newIndex,newPosition);


                }
            }
            nX = found.x - 1;
            nY = found.y;
            newIndex = transformToLinearSpace(nX, nY);
            if(withinBounds(nX,nY)&& gridSpaces[newIndex] == occupationID.Open) {

                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) {
                    beenLocations.get(newIndex).alter(DistanceIndex(nX, nY, to_X, to_Y,calc), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) {
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost, config.DistanceCost,calc);
                    newPosition.parent = found;
                    sortedList.add(newPosition);
                    beenLocations.put(newIndex,newPosition);

                }
            }

            nX = found.x;
            nY = found.y + 1;
            newIndex = transformToLinearSpace(nX, nY);
            if(withinBounds(nX,nY)&& gridSpaces[newIndex] == occupationID.Open) {

                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) {
                    beenLocations.get(newIndex).alter(DistanceIndex(nX, nY, to_X, to_Y,calc), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) {
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost, config.DistanceCost,calc);
                    newPosition.parent = found;
                    sortedList.add(newPosition);
                    beenLocations.put(newIndex,newPosition);
                }
            }

            nX = found.x;
            nY = found.y - 1;
            newIndex = transformToLinearSpace(nX, nY);
            if(withinBounds(nX,nY) && gridSpaces[newIndex] == occupationID.Open) {

                if (overridePosition(beenLocations, newIndex, found.pathCost + config.StepCost)) {
                    beenLocations.get(newIndex).alter(DistanceIndex(nX, nY, to_X, to_Y,calc), found.pathCost + config.StepCost, found);
                } else if (!beenLocations.containsKey(newIndex)) {
                    newPosition = createNewPosition(found, nX, nY, to_X, to_Y, config.StepCost, config.DistanceCost,calc);
                    newPosition.parent = found;
                    sortedList.add(newPosition);
                    beenLocations.put(newIndex,newPosition);

                }
            }

            counter--;
        }
        while (transformToLinearSpace(found.x, found.y) != tI && counter != 0 &&  sortedList.size() > 0); //while havent found the goal nor reached max iterations

        if(found.x != to_X || found.y != to_Y)
        {
            //get closet found position
            float currentDistance = 1000;
            for (FoundPositions fp: beenLocations.values()
                 ) {

                float dist = DistanceIndex(fp.x,fp.y,to_X,to_Y,calc);

                if(dist<currentDistance){
                    found = fp;
                    currentDistance = dist;
                }

            }
        }

        System.out.println(counter);
        List l_steps = new LinkedList<Vector2>();

        do{
            l_steps.add(new Vector2(found.x,found.y).add(gridsWorldPosition_BOTTOMLEFT).scl(sizeOfGridSpaces)); //this makes the positions world relative
            found = found.parent;
        } while (found.x != found.parent.x || found.y != found.parent.y);

        Collections.reverse(l_steps);

        return l_steps;



    }
    //this needs to be updated on physics steps for objects with collisions]
    //x y coords need to be translated from world space to grid space first, -1 means either off grid or not relevant


    public void update_entity_on_grid_from_world(int past_x, int past_y, int current_x, int current_y)
    {
            update_entity_on_grid(
                    translateToLocal(past_x,gridsWorldPosition_BOTTOMLEFT.x),
                    translateToLocal(past_y,gridsWorldPosition_BOTTOMLEFT.y),
                    translateToLocal(current_x,gridsWorldPosition_BOTTOMLEFT.x),
                    translateToLocal(current_y,gridsWorldPosition_BOTTOMLEFT.y)
            );
    }
    void update_entity_on_grid(int past_x, int past_y, int current_x, int current_y){

       // if(gridSpaces[transformToLinearSpace(current_x,current_y)] == occupationID.Blocked )
      //      return; //this is an error state Im not sure what to do here

        if(past_x != -1 && past_y != -1)
            gridSpaces[transformToLinearSpace(past_x,past_y)] = occupationID.Open;

        if(current_x != -1 && current_y != -1)
            gridSpaces[transformToLinearSpace(current_x, current_y)] = occupationID.Blocked;
    }

    public void place_static_object_on_grid_from_world(float x, float y, float width, float height, occupationID ID)
    {


        width = (float) (Math.ceil(width/sizeOfGridSpaces));
        height = (float) (Math.ceil(height/sizeOfGridSpaces));

        place_static_object_on_grid(
                translateToLocal((int)Math.ceil(x), gridsWorldPosition_BOTTOMLEFT.x),
                translateToLocal((int)Math.ceil(y), gridsWorldPosition_BOTTOMLEFT.y),
                (int) width, (int) height,ID);

    }
    public void place_static_object_on_grid(int x, int y, int width, int height, occupationID ID){

        for (int ix = 0; ix < width; ix++) {
            for (int iy = 0; iy < height; iy++) {
                System.out.println(Math.min(xSize-1,x + ix) + " : " + Math.min(zSize-1,iy + y) + " : " + transformToLinearSpace(Math.min(xSize-1,x + ix), Math.min(zSize-1,iy + y)));
                gridSpaces[transformToLinearSpace(Math.min(xSize-1,x + ix), Math.min(zSize-1,iy + y))] = ID;
            }
        }

    }

    private FoundPositions createNewPosition(FoundPositions pre, int x, int y, int gx, int gy, float stepSize,float distScale, DistanceCalculator calc){
        return new FoundPositions(x, y, pre.pathCost + stepSize,distScale*DistanceIndex(x,y,gx,gy,calc));
    }
    private boolean overridePosition(HashMap<Integer,FoundPositions> map, int t, float p){
        return map.containsKey(t) && map.get(t).pathCost > p;
    }

    private float DistanceIndex(int ix, int iy, int jx, int jy,DistanceCalculator calc){
        if(calc == DistanceCalculator.Euler)
        return  euclideanDistanceIndex(ix,iy,jx,jy);

        return manhattenDistanceIndex(ix,iy,jx,jy);
    }



    private float euclideanDistanceIndex(int ix, int iy, int jx, int jy){

        double a  = ix - jx;
        a = a*a;
        double b = iy - jy;
        b = b*b;
        a += b;
        a = Math.sqrt(a);

        return (float)a;
       // return (float) Math.sqrt((Math.pow((float)(),2.0f) + Math.pow((float)(iy - jy),2.0f)));

    }

    private float manhattenDistanceIndex(int ix, int iy, int jx, int jy){
        return Math.abs((ix-jx)) + Math.abs(iy - jy);
    }



    private int transformToLinearSpace(int x, int z){
        return x + z * xSize;
    }
}
