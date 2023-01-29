package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.RayPoint;

import java.util.List;

public class PathfindingAgent extends BlackScripts
{
    private List<Vector2> currentPath;
    private float Speed = 1.2f;

    public boolean UpdateMap = false;

    private Vector3 PrevPosition = new Vector3(0,0,0);
    private Vector3 NextPosition= new Vector3(0,0,0);

    public void updatePath(List<Vector2> path){
        currentPath = path;


        DequeueNext();

        Vector3 p = new Vector3(gameObject.transform.position);
        p.y =0;
        PrevPosition.set(p);
    }

    public void DequeueNext(){
        if(currentPath.size() == 0)
        {
            PrevPosition.set(NextPosition);
            return;
        }


        PrevPosition = NextPosition;
        Vector2 v2 = currentPath.get(0);
        NextPosition = new Vector3(v2.x,0,v2.y);
        currentPath.remove(0);
    }

    public Vector3 getNextPosition(){
        return new Vector3(NextPosition);
    }
    public Vector3 getCurrentPosition(){
        return new Vector3(PrevPosition);
    }
    public int pathLength(){
        if(currentPath == null)
            return 0;
        return currentPath.size();
    }
    //linePnt - point the line passes through
//lineDir - unit vector in direction of line, either direction works
//pnt - the point to find nearest on line for
    public static RayPoint NearestPointOnLine(Vector3 linePnt, Vector3 lineDir, Vector3 pnt)
    {

        lineDir = lineDir.nor();//this needs to be a unit vector

        Vector3 v = new Vector3(0,0,0);
        v.add(pnt).sub(linePnt);
        float d = v.dot(lineDir);
        RayPoint rayPoint = new RayPoint();
        rayPoint.pos = new Vector3(0,0,0);
        rayPoint.pos.set(linePnt).mulAdd(lineDir,d);
        rayPoint.t = d;
        return rayPoint;
    }

    @Override
    public void Update(float dt){
        move(dt);
    }

    void move(float dt)
    {
        if(currentPath == null || (currentPath.size()==0 && PrevPosition == NextPosition))
        {
            return;
        }
        Vector3 simulatedPosition = new Vector3(gameObject.transform.position);
        simulatedPosition.y  =0;

        Vector3 rDir = new Vector3(NextPosition);
        rDir.sub(PrevPosition);
        RayPoint rp = NearestPointOnLine(PrevPosition, rDir,simulatedPosition);
        float nt = PrevPosition.dst(NextPosition);
        if(rp.t >= nt )
            DequeueNext();

        float dst = NextPosition.dst(simulatedPosition);

        dst = Math.min(dst,Speed);
        Vector3 dir = new Vector3(0,0,0);
        dir.set(NextPosition).sub(simulatedPosition).nor();

        Vector3 p = new Vector3(0,0,0);
        p.set(simulatedPosition);
        p.mulAdd(dir,dst);

        p.y = 3;

        if(UpdateMap)
          gameObject.transform.UpdatePosition(p);
        else
            gameObject.transform.position.set(p);

    }
}
