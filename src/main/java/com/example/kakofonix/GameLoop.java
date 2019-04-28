package com.example.kakofonix;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends SurfaceView implements Runnable {

    //test

    int zycie ;
    int trafienie = 0;
    int trafienie2 = 0;

    Thread thread = null;
    boolean running = true;
    double frame_per_second , frame_time_seconds , frame_time_ms , frame_time_ns;
    double tLF,tEOR,delta_t;
    double theta,theta_per_sec;

    // do rysowania
    Paint red_paintbrush_fill , blue_paintbrush_fill ,  green_paintbrush_fill , yellow_paintbrush_fill , black_paintbrush_fill;
    Paint red_paintbrush_stroke , blue_paintbrush_stroke ,  green_paintbrush_stroke , yellow_paintbrush_stroke , purple_paintbrush_stroke;
    Paint white_text,black_text , fill;

    RectF buttonBlue , buttonRed , buttonYellow;


    //grafika
    boolean CanDraw;
    SurfaceHolder surfaceHolder;
    Bitmap backGroundCheck;
    Bitmap ballImage;
    Bitmap bombImage;
    Canvas canvas;

    //muzyka
    MediaPlayer clickSound;
    MediaPlayer boomSound;
    MediaPlayer track;

    //ustawienia
    Difficulty difficulty;
    Random random;
    int appearChance;
    int speed;
    int score;
    int distanceBetweenBalls;
    List<Ball> Balls;

    public GameLoop(Context context , Difficulty difficulty){
        super(context);

        track = difficulty.getSong();
        track.start();
        track.setLooping(true);
        clickSound = MediaPlayer.create(getContext(),R.raw.click2);
        boomSound = MediaPlayer.create(getContext(),R.raw.boom003);

        backGroundCheck = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        ballImage = BitmapFactory.decodeResource(getResources(),R.drawable.blackpearl);
        bombImage = BitmapFactory.decodeResource(getResources(),R.drawable.bomb);
        CanDraw = false;
        surfaceHolder = getHolder();

        zycie = 3;
        speed = toPxs(difficulty.getBallsSpeed());
        score = 0;
        appearChance = difficulty.getChanceToAppearance();
        distanceBetweenBalls = toPxs(difficulty.getDistanceBetweenBalls());
        Balls = new ArrayList<Ball>();
        random = new Random();

        //buttons
        buttonBlue  = new RectF(toPxs(20),toPxs(420),toPxs(100),toPxs(500));
        buttonYellow = new RectF( toPxs(140),toPxs(420),toPxs(220),toPxs(500));
        buttonRed = new RectF(toPxs(260),toPxs(420),toPxs(340),toPxs(500));






        frame_per_second = 30;
        frame_time_seconds = 1 / frame_per_second;
        frame_time_ms = frame_time_seconds * 1000;
        frame_time_ns = frame_time_ms * 1000000;
    }



    @Override
    public void run() {
        while(true) {
            prepPaintBrushes();

            tLF = System.nanoTime();
            delta_t = 0;


            while (CanDraw) {


                if (!surfaceHolder.getSurface().isValid()) {
                    continue;
                }
                update();
                draw();


                tEOR = System.nanoTime();

                delta_t = frame_time_ns - (tEOR - tLF);


                try {

                    if (delta_t > 0) {

                        thread.sleep((long) (delta_t / 1000000));
                    }
                    ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                tLF = System.nanoTime();

            }
        }

    }

    private void reset()
    {
        CanDraw = true;
        zycie = 3;
        score = 0;
        Balls = new ArrayList<Ball>();
        track.seekTo(0);
        track.start();

    }

    private void EndGame()
    {
        boomSound.start();
        CanDraw = false;
        track.pause();
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawText("PRZEGRALES " ,toPxs(25),toPxs(200),black_text);
        canvas.drawText("SCORE " ,toPxs(140),toPxs(250),white_text);
        canvas.drawText(""+score ,toPxs(140),toPxs(270),white_text);
        surfaceHolder.unlockCanvasAndPost(canvas);
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() >= 0) reset();
                return false;
            }
        });

    }

    private void update(){

        int lane = random.nextInt(3);
        int roll = random.nextInt(101);
        float width =getResources().getDisplayMetrics().widthPixels;


        if(!Balls.isEmpty()){
            if ( toPxs((int)Balls.get(Balls.size()-1).top) >  distanceBetweenBalls ){
                if (roll < appearChance) {
                    if(roll < 10)
                    {
                        Balls.add(new Ball((int) (((width / 3) * lane) + (width / 3 / 2) - (ballImage.getWidth() / 2)), -100, 1));
                    }
                    else {
                        Balls.add(new Ball((int) (((width / 3) * lane) + (width / 3 / 2) - (ballImage.getWidth() / 2)), -100, 0));
                    }
                }
                else
                {
                    Balls.add(new Ball( -150, -100,0));
                }
            }
        }
        else{
            if (roll < appearChance) {
                if(roll < 10)
                {
                    Balls.add(new Ball((int) (((width / 3) * lane) + (width / 3 / 2) - (ballImage.getWidth() / 2)), -100, 1));
                }
                else {
                    Balls.add(new Ball((int) (((width / 3) * lane) + (width / 3 / 2) - (ballImage.getWidth() / 2)), -100, 0));
                }
            }
            else
            {
                Balls.add(new Ball( -150, -100,0));
            }
        }


        for( int i = 0 ; i < Balls.size() ; i++) {
            Balls.get(i).update(0, speed);
            if ( (int)Balls.get(i).top >getResources().getDisplayMetrics().heightPixels ){
                if(Balls.get(i).left > 0 && Balls.get(i).type == 0)
                {
                    zycie -=1;
                }
                Balls.remove(Balls.get(i));
            }
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float x = event.getX();
                float y = event.getY();

                for( int i = 0 ; i < Balls.size() ; i++) {
                    if (buttonBlue.contains(x,y) && buttonBlue.contains(Balls.get(i))){
                        if(Balls.get(i).type == 1)
                        {
                            boomSound.start();
                            zycie -= 1;
                        }
                        else if(Balls.get(i).type == 0)
                        {
                            clickSound.start();
                            score += 100;
                        }
                        Balls.remove(Balls.get(i));

                    }
                    else if (buttonBlue.contains(x,y) && RectF.intersects(buttonBlue , Balls.get(i))){
                        if(Balls.get(i).type == 1)
                        {
                            boomSound.start();
                            zycie -= 1;
                        }
                        else if(Balls.get(i).type == 0)
                        {
                            clickSound.start();
                            score += 50;
                        }
                        Balls.remove(Balls.get(i));
                    }


                    if (buttonRed.contains(x,y) && buttonRed.contains(Balls.get(i))) {
                        if(Balls.get(i).type == 1)
                        {
                            boomSound.start();
                            zycie -= 1;
                        }
                        else if(Balls.get(i).type == 0)
                        {
                            clickSound.start();
                            score += 100;
                        }
                        Balls.remove(Balls.get(i));
                    }

                    else if (buttonRed.contains(x,y) && RectF.intersects(buttonRed , Balls.get(i))){
                        if(Balls.get(i).type == 1)
                        {
                            boomSound.start();
                            zycie -= 1;
                        }
                        else if(Balls.get(i).type == 0)
                        {
                            clickSound.start();
                            score += 50;
                        }
                        Balls.remove(Balls.get(i));
                    }

                    if (buttonYellow.contains(x,y) && buttonYellow.contains(Balls.get(i))){
                        if(Balls.get(i).type == 1)
                        {
                            boomSound.start();
                            zycie -= 1;
                        }
                        else if(Balls.get(i).type == 0)
                        {
                            clickSound.start();
                            score += 100;
                        }
                        Balls.remove(Balls.get(i));
                    }

                    else if (buttonYellow.contains(x,y) && RectF.intersects(buttonYellow , Balls.get(i))){
                        if(Balls.get(i).type == 1)
                        {
                            boomSound.start();
                            zycie -= 1;
                        }
                        else if(Balls.get(i).type == 0)
                        {
                            clickSound.start();
                            score += 50;
                        }
                        Balls.remove(Balls.get(i));
                }

                }
                Log.d("touch listener", Float.toString(x)+","+Float.toString(y) );

                return false;
            }
        });

    }

    private void draw(){
        canvas = surfaceHolder.lockCanvas();
        canvas.drawBitmap(backGroundCheck,0,0,null);

        canvas.drawRoundRect(buttonBlue,180,180,fill);
        canvas.drawRoundRect(buttonYellow,180,180,fill);
        canvas.drawRoundRect(buttonRed,180,180,fill);

        for( int i = 0 ; i < Balls.size() ; i++){
            if(Balls.get(i).type == 0) {
                canvas.drawBitmap(ballImage, Balls.get(i).left, Balls.get(i).top, null);
            }
            else if(Balls.get(i).type == 1)
            {
                canvas.drawBitmap(bombImage, Balls.get(i).left, Balls.get(i).top, null);
            }
        }
        canvas.drawText("score: " + score,300,50,white_text);
        canvas.drawText("zycie: " + zycie,0,50,white_text);
        surfaceHolder.unlockCanvasAndPost(canvas);


        if(zycie < 1) EndGame();


    }



    private void prepPaintBrushes() {

        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        white_text = new Paint();
        white_text.setColor(Color.WHITE);
        white_text.setTextSize(60);

        black_text = new Paint();
        black_text.setColor(Color.BLACK);
        black_text.setTextSize(150);
        black_text.setShadowLayer(20,10,10,Color.RED);
        black_text.setFakeBoldText(true);

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        yellow_paintbrush_fill = new Paint();
        yellow_paintbrush_fill.setColor(Color.YELLOW);
        yellow_paintbrush_fill.setStyle(Paint.Style.FILL);

        black_paintbrush_fill = new Paint();
        black_paintbrush_fill.setColor(Color.BLACK);
        black_paintbrush_fill.setStyle(Paint.Style.FILL);


        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(10);

        purple_paintbrush_stroke = new Paint();
        purple_paintbrush_stroke.setColor(Color.rgb(70,2,115));
        purple_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        purple_paintbrush_stroke.setStrokeWidth(5);
        purple_paintbrush_stroke.setShadowLayer(15,0,0,Color.rgb(240,4,217));
        fill = purple_paintbrush_stroke;


        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setColor(Color.BLUE);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(10);

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setColor(Color.GREEN);
        green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        green_paintbrush_stroke.setStrokeWidth(10);

        yellow_paintbrush_stroke = new Paint();
        yellow_paintbrush_stroke.setColor(Color.YELLOW);
        yellow_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        yellow_paintbrush_stroke.setStrokeWidth(10);
    }

    private int toPxs(int dps){
        //konwersja odleglosci, ktora w efekcie jest dopasowana do telefonu
        return (int) (dps * getResources().getDisplayMetrics().density);
    }

    public void pause(){
        track.pause();
        CanDraw = false;
        thread = null;
        Log.d("Thread" , "Pausing thread..." + Thread.currentThread().getId());


    }

    public void resume() {
        track.start();
        CanDraw = true;
        Log.d("Thread" , "MAKING NEW");
        thread = new Thread(this);
        Log.d("Thread","STARTING NEW");
        thread.start();
        Log.d("Thread" ,  "STARTED");

    }

    private void stats(){
        /**
         Log.d("Frames_per_second" , Double.toString(frame_per_second));
         Log.d("Frame_time_seconds" , Double.toString(frame_time_seconds));
         Log.d("Frame_time_ms" , Double.toString(frame_time_ms));
         Log.d("Frame_time_ns" , Double.toString(frame_time_ns));
         Log.d("TLF" , Double.toString(tLF));
         Log.d("TEOR" , Double.toString(tEOR));
         Log.d("F_delta_t" , Double.toString(delta_t));
         Log.d("delta_t sec" , Double.toString(delta_t/1000000));
         Log.d("resolution width",Integer.toString(getResources().getDisplayMetrics().widthPixels));
         Log.d("resolution heigh",Integer.toString(getResources().getDisplayMetrics().heightPixels));
         Log.d("density",Float.toString(getResources().getDisplayMetrics().density));
         Log.d("density dpi",Integer.toString(getResources().getDisplayMetrics().densityDpi));
         */


        Log.d("--------","------------------");

    }
}