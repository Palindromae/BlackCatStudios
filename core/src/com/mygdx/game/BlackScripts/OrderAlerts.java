package com.mygdx.game.BlackScripts;

import com.mygdx.game.BlackCore.BlackScripts;
import com.mygdx.game.MyGdxGame;

public class OrderAlerts extends BlackScripts {
    public static boolean alertOn = false;
    public static boolean bigger = true;

    public static void checkIfToShowAlert(){
        if(MyGdxGame.orderPage.transform.position.x !=200 && !alertOn){
            changeAlertState();
            alertOn = true;
        }
    }

    public static void changeAlertState(){
        MyGdxGame.orderAlert.negateVisibility();
    }

}
