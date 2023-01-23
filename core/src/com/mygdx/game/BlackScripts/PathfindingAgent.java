package com.mygdx.game.BlackScripts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.RayPoint;

import java.util.List;

public class PathfindingAgent extends BlackScripts
{
    private List<Vector2> currentPath;
    private float Speed = 1.5f;

    private Vector3 PrevPosition = new Vector3(0,0,0);
    private Vector3 NextPosition= new Vector3(0,0,0);

    public void updatePath(List<Vector2> path){
        currentPath = path;


        DequeueNext();


        PrevPosition.set(gameObject.transform.position);
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

        Vector3 rDir = new Vector3(NextPosition);
        rDir.sub(PrevPosition);
        RayPoint rp = NearestPointOnLine(PrevPosition, rDir,gameObject.transform.position);
        float nt = PrevPosition.dst(NextPosition);
        if(rp.t >= nt )
            DequeueNext();

        float dst = NextPosition.dst(gameObject.transform.position);

        dst = Math.min(dst,Speed);
        Vector3 dir = new Vector3(0,0,0);
        dir.set(NextPosition).sub(gameObject.transform.position).nor();

        Vector3 p = new Vector3(0,0,0);
        p.set(gameObject.transform.position);
        p.mulAdd(dir,dst);
        gameObject.transform.UpdatePosition(p);

    }
}
