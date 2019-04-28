package com.example.kakofonix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class GameLoopActivity extends AppCompatActivity {

    GameLoop gameLoop;
    Difficulty difficulty;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game_loop);
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
