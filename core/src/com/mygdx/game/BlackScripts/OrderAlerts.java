package com.mygdx.game.BlackScripts;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.BlackCore.DisplayOrders;
import com.mygdx.game.MyGdxGame;

public class OrderAlerts extends BlackScripts {
    public static boolean alertOn = false;
    public static boolean bigger = true;

    public static void checkIfToShowAlert(){
        if(MyGdxGame.orderPage.transform.position.x !=200 && !alertOn && !DisplayOrders.allSeen){
            if(MyGdxGame.orderAlert.getVisibility() == false){
                changeAlertState();
                alertOn = true;
            }
        }else if(DisplayOrders.allSeen){
            if(MyGdxGame.orderAlert.getVisibility()){
                changeAlertState();
                alertOn = false;
            }
        }
    }

    public static void changeAlertState(){
        MyGdxGame.orderAlert.negateVisibility();
    }

}
