package de.berufsschule_freising.powers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.berufsschule_freising.powers.firebase.User;

/**
 * Created by cami on 07.03.18.
 */

public class UserSorter {

    private List<User> data;

    public UserSorter(List<User> data)
    {
        this.data = data;
    }

    public List<User> getSortedUsers()
    {
        Collections.sort(data, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.compareTo(o2);
            }
        });

        return data;
    }
}
