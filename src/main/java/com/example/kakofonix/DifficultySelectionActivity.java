package com.example.kakofonix;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.Serializable;

public class DifficultySelectionActivity extends AppCompatActivity {

    //private Difficulty difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficult_selection);
    }

    public void ChooseDifficulty(View view)
    {
        Intent intent;
        switch (view.getId())
        {

            case R.id.easybutton:
            {
                intent = new Intent(this, GameLoopActivity.class);
                intent.putExtra("difficulty" ,  "easy");
                startActivity(intent);
                break;

            }

            case R.id.mediumbutton:
            {
                intent = new Intent(this, GameLoopActivity.class);
                intent.putExtra("difficulty",  "medium");
                startActivity(intent);
                break;

            }

            case R.id.hardbutton:
            {
                intent = new Intent(this, GameLoopActivity.class);
                intent.putExtra("difficulty",  "hard");
                startActivity(intent);
                break;

            }

        }
    }
}
