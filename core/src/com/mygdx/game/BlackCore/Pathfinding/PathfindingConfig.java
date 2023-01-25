package com.mygdx.game.BlackCore.Pathfinding;


public class PathfindingConfig {


    //Cost of being X units away from goal
    public float DistanceCost = 1;
    //Cost of the path
    public float PathMutliplier = 1;
    //cost of taking an adjacent step non diagonal
    public float StepCost = 1;
    //Cost  of moving diagonaly
    public float DiagonalCost= 1;
    public int maxIterations= 1;
}
