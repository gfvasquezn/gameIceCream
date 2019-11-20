package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class kid {

    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public static final int SPRITE_SIZE_WIDTH =120;
    public static final int SPRITE_SIZE_HEIGTH=100;
    public static final float GRAVITY_FORCE=10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    int score=0;
    private float maxY;
    private float maxX;
    int kidPased=0;
    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spritekid;
    boolean started=false;
    String texto="Touch to start";
    private int level=1;

    public kid(Context context, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = screenWidth-200;
        positionY = (screenWidth/2)-200;
        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.kid);
        spritekid  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spritekid.getWidth()/2);
        this.maxY = screenHeigth - spritekid.getHeight();
    }

    public kid(Context context, float initialX, float initialY, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = initialX;
        positionY = initialY;

        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.kid);
        spritekid  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spritekid.getWidth()/2);
        this.maxY = screenHeigth - spritekid.getHeight();

    }

    public static float getInitX() {
        return INIT_X;
    }

    public static float getInitY() {
        return INIT_Y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpritekid() {
        return spritekid;
    }

    public void setSpritekid(Bitmap spritekid) {
        this.spritekid = spritekid;
    }


    /**
     * Control the position and behaviour of the icecream car
     */
    public void updateInfo (float a, float b) {
        if(this.started){
            if(this.score%10==0&&(level<this.score/10)){
                level=this.score/10;
            }
            speed += 5*level;
            this.positionX-=10;


            if(this.score<0){
                started=false;
                score=0;
                texto="You lose";

            }

            if(a+60>this.positionX && a-60<this.positionX ){
                if(b+60>this.positionY && b-60<this.positionY ){
                    this.score+=1;
                    this.positionX= this.maxX;
                    this.positionY =  (float)Math.random()*400;
                }
            }

            if(this.positionX<0){
                this.score-=1;
                this.positionX= this.maxX;
                this.positionY =  (float)Math.random()*400;
            }


        }

    }
}
