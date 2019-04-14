package com.example.kakofonix;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //setSupportActionBar(myToolbar);
    }

    public void dupa()
    {

    }

    public void ChooseButton(View view)
    {
        switch (view.getId())
        {
            case R.id.startbutton:
            {
                Intent intent = new Intent(this, DifficultySelectionActivity.class);
                startActivity(intent);
                break;

            }
        }
    }

    @Override
    public void onPause(){super.onPause();}

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.mian_menu,menu);
        return true;
    }
}
