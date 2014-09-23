package team.kyb.animationAPI;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class FallingObject extends View {

	public float mX;
    public float mY;
    public final int mR;
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public float vX;
    public float vY;
    
    private Bitmap bitmap;
    
    public android.graphics.PointF mBallPos, mBallVelocity;
    
    private Random rd = new Random();
    
    int a = rd.nextInt();
    int red = rd.nextInt();
    int g = rd.nextInt();
    int b = rd.nextInt();
    
    //construct new ball object
    public FallingObject(Context context, float x, float y, int r, Bitmap bm) {
        super(context);
        //color hex is [transparency][red][green][blue]       
        mPaint.setARGB(a, red, g, b); //not transparent. color is green
        this.mX = x;
        this.mY = y;
        this.mR = r; //radius
        this.bitmap = bm;
    }
    	
    //called by invalidate()	
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(1);
        mPaint.setARGB(a, red, g, b);
        canvas.drawBitmap(bitmap, mX, mY, mPaint);
    } 
    
    public void resetY() {
    	this.mY = 10;
    }
}
