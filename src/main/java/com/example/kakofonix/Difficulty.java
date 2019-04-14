package com.example.kakofonix;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.Serializable;

public class Difficulty {

    private MediaPlayer song;
    private int ballsSpeed;
    private int chanceToAppearance;
    private int distanceBetweenBalls;

    public Difficulty(Context context , String mode)
    {
        switch (mode)
        {
            case "easy":
            {
                song = MediaPlayer.create(context , R.raw.track01);
                ballsSpeed = 2;
                chanceToAppearance = 50;
                distanceBetweenBalls = 50;
                break;
            }
            case "medium":
            {
                song = MediaPlayer.create(context , R.raw.track02);
                ballsSpeed = 4;
                chanceToAppearance = 70;
                distanceBetweenBalls = 50;
                break;
            }

            case "hard":
            {
                song = MediaPlayer.create(context , R.raw.track03);
                ballsSpeed = 10;
                chanceToAppearance = 90;
                distanceBetweenBalls = 50;
                break;
            }
        }
    }

    public MediaPlayer getSong() {
        return song;
    }

    public int getBallsSpeed() {
        return ballsSpeed;
    }

    public int getChanceToAppearance() {
        return chanceToAppearance;
    }

    public int getDistanceBetweenBalls() {
        return distanceBetweenBalls;
    }
}
