package de.berufsschule_freising.powers.firebase;

import android.support.annotation.NonNull;

/**
 * Created by cami on 06.03.18.
 */

public class User implements Comparable<User>{

    private String name;
    private int highScore;

    public User() {};

    public User(String name)
    {
        this.name = name;
    }

    public User(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public int compareTo(@NonNull User o) {
        return o.getHighScore() - this.getHighScore();
    }
}
