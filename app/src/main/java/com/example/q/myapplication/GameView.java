package com.example.q.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.MainThread;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import static com.example.q.myapplication.mainThread.canvas;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private mainThread thread;
    private CharacterSprite characterSprite;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new mainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        characterSprite = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.ball));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        characterSprite.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            characterSprite.draw(canvas);
        }
    }

}
