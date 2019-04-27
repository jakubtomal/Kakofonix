package com.example.kakofonix;

import android.graphics.Rect;
import android.graphics.RectF;

public class Ball  extends RectF{
    //public int x;
   // public int y;
    public int type;

    public Ball(int x , int y , int type ) {
        super(x,y,x+100,y+100);
        this.type = type;
    }


    public void update(  int dx , int dy){
        this.top += dy;
        this.left += dx;
        this.bottom +=dy;
        this.right += dx;
    }

    }

