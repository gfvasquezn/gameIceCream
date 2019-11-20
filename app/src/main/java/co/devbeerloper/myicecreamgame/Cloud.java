package co.devbeerloper.myicecreamgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Cloud {


    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public static final int SPRITE_SIZE_WIDTH =120;
    public static final int SPRITE_SIZE_HEIGTH=100;
    public static final float GRAVITY_FORCE=10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    int puntos=0;
    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spritekid;
    private boolean primeraVez;


    public Cloud(Context context, float screenWidth, float screenHeigth){

        speed = 1;
        positionX = screenWidth-200;
        positionY = (screenWidth/2)-200;
        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
        spritekid  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spritekid.getWidth()/2);
        this.maxY = screenHeigth - spritekid.getHeight();
    }

    public Cloud(Context context, float initialX, float initialY, float screenWidth, float screenHeigth){

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
        speed += 5;
        this.positionX-=10;

        if(this.positionX<0){
            primeraVez=false;
            this.puntos-=1;
            System.out.println("Puntos: "+ puntos);
            this.positionX= this.maxX;
            this.positionY =  (float)Math.random()*400;
        }


    }
}

