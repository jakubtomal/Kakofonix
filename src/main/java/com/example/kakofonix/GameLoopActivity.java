package com.example.kakofonix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kakofonix.GameLoop;

public class GameLoopActivity extends AppCompatActivity {
    GameLoop gameLoop;
    Difficulty difficulty;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = this.getIntent().getStringExtra("difficulty");
        difficulty = new Difficulty(this,mode);
        gameLoop = new GameLoop(this ,difficulty);

        setContentView(gameLoop);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game" , "Resume");
        super.onResume();
        gameLoop.resume();
    }

    @Override
    protected void onPause(){
        Log.d("Super/Game" , "Pause");
        super.onPause();
        gameLoop.pause();
    }






}

