package co.devbeerloper.myicecreamgame;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurfaceView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private IceCreamCar icecreamCar;
    private kid kid;
    private Paint paint;
     private Paint paintStart;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;

    /**
     * Contructor
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        icecreamCar = new IceCreamCar(context, screenWith, screenHeight);
        kid = new kid(context, screenWith, screenHeight);
        paint = new Paint();
        paintStart= new Paint();
        paintStart.setTextSize(100);
        holder = getHolder();
        isPlaying = true;
    }

    /**
     * Method implemented from runnable interface
     */
    @Override
    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();
        }

    }

    private void updateInfo() {
        icecreamCar.updateInfo ();
        kid.updateInfo(icecreamCar.getPositionX(),icecreamCar.getPositionY());

        if((float)Math.random()*10==1){
          new kid(super.getContext(), 10, 10);
        }
    }



    

    private void paintFrame() {
        if (holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.CYAN);
            canvas.drawText("Score "+kid.score+"", 40,40,paint);
            if(!kid.started){canvas.drawText(kid.texto, 650,400,paint);}
            paint.setTextSize(40);

            canvas.drawBitmap(icecreamCar.getSpriteIcecreamCar(),icecreamCar.getPositionX(),icecreamCar.getPositionY(),paint);

            canvas.drawBitmap(kid.getSpritekid(),kid.getPositionX(),kid.getPositionY(),paint);
            holder.unlockCanvasAndPost(canvas);
        }

    }


    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    /**
     * Detect the action of the touch event
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        kid.started=true;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                System.out.println("TOUCH UP - STOP JUMPING");
                icecreamCar.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                System.out.println("TOUCH DOWN - JUMP");
                icecreamCar.setJumping(true);
                break;
        }
        return true;
    }


}