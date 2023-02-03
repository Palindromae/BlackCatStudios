package com.mygdx.game.BlackCore.Pathfinding;


import java.util.Comparator;

public class FoundPositions implements Comparable<FoundPositions> {
    public int x, y;
    public float heuristics;
    public float pathCost;

    public FoundPositions parent;
    public void alter(float new_heuristics, float new_pathcost, FoundPositions new_parent){
        heuristics = new_heuristics;
        pathCost = new_pathcost;
        parent = new_parent;
    }


    public float score(){
        return heuristics + pathCost;
    }

    public FoundPositions(int x, int y, float pathCost, float heuristics){
        this.x = x;
        this.y = y;
        this.heuristics = heuristics;
        this.pathCost = pathCost;
    }


    @Override
    public int compareTo(FoundPositions o) {
        if (score()>o.score())
            return 1;
        if (score()<o.score())
            return -1;
        return  0;    }
}
