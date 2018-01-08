package de.berufsschule_freising.powers;

import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.Arrays;

import de.berufsschule_freising.powers.applogic.GridController;
import de.berufsschule_freising.powers.interfaces.GridDataInteract;
import de.berufsschule_freising.powers.interfaces.GridDataSupplier;

/**
 * Created by cami on 08.01.18.
 */

public class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {

    GridDataInteract gdi;
    GridDataSupplier gds;

    public CustomGestureDetector(GridDataInteract gdi, GridDataSupplier gds) {
        this.gdi = gdi;
        this.gds = gds;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        int[][] temp = new int[gds.getGrid().length][];
        for (int i = 0; i < gds.getGrid().length; i++) {
            temp[i] = gds.getGrid()[i].clone();
        }

        if(Math.abs(e2.getX() - e1.getX()) > Math.abs(e2.getY() - e1.getY())) {
            if (e1.getX() < e2.getX()) {
                gdi.action(GridController.Direction.right);
            }

            else if (e1.getX() > e2.getX()) {
                gdi.action(GridController.Direction.left);
            }
        }
        else {
            if (e1.getY() < e2.getY()) {
                gdi.action(GridController.Direction.down);
            }

            else if (e1.getY() > e2.getY()) {
                gdi.action(GridController.Direction.up);
            }
        }

        /*for(int i = 0; i < temp.length; i++) {
            for(int l = 0; l < temp[i].length; l++) {
                if (temp[i][l] != gds.getGrid()[i][l]) {
                    return true;
                }
            }
        }*/

        if(!Arrays.deepEquals(temp, gds.getGrid())) {
            gdi.generateItem();
        }

        return true;
    }

}
