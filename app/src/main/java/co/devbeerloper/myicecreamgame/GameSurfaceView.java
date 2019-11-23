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
    private  Men men;
    private Paint paint;
    private Paint paintStart;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private  int times=0;
    private int score=0;
    private kid[] kids = new  kid[5];
    private Cloud[] clouds= new  Cloud[3];
    boolean active=false;
    boolean menTouched=false;
    int screenWith=0;
    int level=1;

    /**
     * Contructor
     * @param context
     */
    public GameSurfaceView(Context context, float screenWith, float screenHeight) {
        super(context);
        this.screenWith=(int)screenWith;
        icecreamCar = new IceCreamCar(context, screenWith, screenHeight);
        paint = new Paint();
        paintStart= new Paint();
        paintStart.setTextSize(100);
        holder = getHolder();
        isPlaying = true;
        for (int i=0;i<5;i=i+1){
         kids[i]= new kid(context, screenWith, screenHeight);
        }
        for (int i=0;i<3;i=i+1){
            clouds[i]= new Cloud(context, screenWith, screenHeight);
        }
            men= new Men(context, screenWith, screenHeight);

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
        if(active){
            icecreamCar.updateInfo();
            for(int i=0; i < times/100 ;i=i+1) {
                this.score+= kids[i].updateInfo(icecreamCar.getPositionX(), icecreamCar.getPositionY(),level);
                if(this.score<0){
                    active=false;
                    score=0;
                }
            }
            for(int i=0; i < times/200 ;i=i+1) {
                clouds[i].updateInfo();
            }
                this.score+= men.updateInfo(icecreamCar.getPositionX(), icecreamCar.getPositionY(),level);


        }else {
            score=0;
            level=0;
            times=0;
                men.setPositionX(screenWith);

            for (int i=0;i<5;i=i+1){
                kids[i].setPositionX(screenWith);
            }
        }
        if((score/20)+1>level){
            level=(score/20)+1;
        }
    }

    private void paintFrame() {
        if (holder.getSurface().isValid()){
            if(times<500){
                times=times+1;
            }
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.CYAN);
            canvas.drawText("Score "+this.score+"", 40,40,paint);
            canvas.drawText("Level "+this.level+"", this.screenWith-400,40,paint);
            if(!active){canvas.drawText("You lose", 650,400,paint);}
            paint.setTextSize(40);
            canvas.drawBitmap(icecreamCar.getSpriteIcecreamCar(),icecreamCar.getPositionX(),icecreamCar.getPositionY(),paint);
            for(int i=0;i<times/100;i=i+1) {
                canvas.drawBitmap(kids[i].getSpritekid(),kids[i].getPositionX(),kids[i].getPositionY(),paint);
            }
            for(int i=0;i<times/200;i=i+1) {
                canvas.drawBitmap(clouds[i].getSpritecloud(),clouds[i].getPositionX(),clouds[i].getPositionY(),paint);
            }
                canvas.drawBitmap(men.getSpritemen(),men.getPositionX(),men.getPositionY(),paint);

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
        this.active=true;
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                icecreamCar.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                icecreamCar.setJumping(true);
                break;
        }
        return true;
    }


}