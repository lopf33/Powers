package de.berufsschule_freising.powers.interfaces;

/**
 * Created by cami on 30.11.17.
 */

public interface GridDataSupplier {

    int getSizeX();
    int getSizeY();
    int[][] getGrid();
    int itemAt(int x, int y);
    boolean isGameOver();

}
