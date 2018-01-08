package de.berufsschule_freising.powers.interfaces;

import de.berufsschule_freising.powers.applogic.GridController;

/**
 * Created by cami on 08.01.18.
 */

public interface GridDataInteract {

    void generateItem();
    void action(GridController.Direction direction);

}
