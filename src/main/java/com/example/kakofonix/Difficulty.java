package com.example.kakofonix;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.Serializable;
import java.util.List;

public class Difficulty {

    private MediaPlayer song;
    private int ballsSpeed;
    private int chanceToAppearance;
    private int distanceBetweenBalls;
    private String mode;

    public Difficulty(Context context , String mode)
    {
        switch (mode)
        {
            case "easy":
            {
                song = MediaPlayer.create(context , R.raw.track01);
                ballsSpeed = 7;
                chanceToAppearance = 50;
                distanceBetweenBalls = 100;
                this.mode = mode;
                break;
            }
            case "medium":
            {
                song = MediaPlayer.create(context , R.raw.track02);
                ballsSpeed = 10;
                chanceToAppearance = 70;
                distanceBetweenBalls = 100;
                this.mode = mode;
                break;
            }

            case "hard":
            {
                song = MediaPlayer.create(context , R.raw.track03);
                ballsSpeed = 12;
                chanceToAppearance = 90;
                distanceBetweenBalls = 100;
                this.mode = mode;
                break;
            }
        }
    }

    public MediaPlayer getSong() {
        return song;
    }

    //public MediaPlayer getSongs(){return songs[0];}

    public int getBallsSpeed() {
        return ballsSpeed;
    }

    public int getChanceToAppearance() {
        return chanceToAppearance;
    }

    public int getDistanceBetweenBalls() {
        return distanceBetweenBalls;
    }

    public String getMode() {
        return mode;
    }
}
