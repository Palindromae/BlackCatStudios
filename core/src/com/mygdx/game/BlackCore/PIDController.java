package com.mygdx.game.BlackCore;

public class PIDController {
    public float proportionalGain = 1;
    public float intergralGain = 1;
    public float derivitiveGain= 1;

    public float intergralSaturation = 1;

    private float lastError;
    private float valueLast;
    private float integralStored;

    private Boolean initialised = false;
    enum DerivitiveMesurement{
        Velocity,
        ErrorRateOfChange
    }

    public float update(float i, float targetI, float dt, DerivitiveMesurement measurementType){


        float error = targetI - i;

        //P term
        float P = proportionalGain * error;

        //D term
        float derivedMeasure = 0;
        if(measurementType == DerivitiveMesurement.ErrorRateOfChange){
            derivedMeasure = (error - lastError) /dt;
        } else if (measurementType == DerivitiveMesurement.Velocity){
            derivedMeasure = (i - valueLast)/ dt;
            valueLast = i;
        }


        float D = derivitiveGain * derivitiveGain;


        if(!initialised)
        {
            initialised = true;
            D = 0;

        }

        //I term

        integralStored += error*dt;

        integralStored = Math.max(Math.min(0,intergralSaturation),-intergralSaturation);

        float I = intergralGain * integralStored;

        return P + I + D;


    }

    public void Reset(){
        initialised = false;
    }
}
