package vaystudios.com.memory.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.Gesture.MoveGestureDetector;
import vaystudios.com.memory.Util.Gesture.RotateGestureDetector;
import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/5/2017.
 */

public class CustomBitmap extends ImageView implements View.OnClickListener
{
    View v;
    Activity c;
    Bitmap bitmap;
    Paint paint;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private float mScaleFactor = 1f;
    private float mAngle;
    boolean dragging = false;
    float px;
    float py;
    float sx, sy;

    float tx, ty;

    private void Init()
    {
        v = (View)this;

        setScaleType(ScaleType.FIT_CENTER);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        rotationGestureDetector = new RotationGestureDetector(new RotationGestureListener(), this);

        // setOnClickListener(this);
        //   setClickable(true);
        //      c.registerForContextMenu(v);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                scaleGestureDetector.onTouchEvent(motionEvent);
                rotationGestureDetector.onTouchEvent(motionEvent) ;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();

                params.width = (int)(bitmap.getWidth() * mScaleFactor);
                params.height = (int)(bitmap.getHeight() * mScaleFactor);
                setLayoutParams(params);

                switch(motionEvent.getAction())
                {



                case MotionEvent.ACTION_MOVE:

                        if(motionEvent.getPointerCount() != motionEvent.getPointerId(motionEvent.getActionIndex())
                              )
                        {


                            float x_cord = motionEvent.getRawX() ;
                            float y_cord = motionEvent.getRawY();



                            float dx = x_cord - px;
                            float dy = y_cord - py;


                            float vx = sx + dx;
                            float vy = sy + dy;

                            tx = vx;
                            ty = vy;

                            setX(tx);
                            setY(ty);

                        }


                    break;
                case MotionEvent.ACTION_DOWN:


                    //  Log.d("VAY",motionEvent.getPointerCount() + " " +  motionEvent.getPointerId(motionEvent.getActionIndex()));
                    if(motionEvent.getPointerCount() != motionEvent.getPointerId(motionEvent.getActionIndex()))
                    {

                        sx = getX();
                        sy = getY();
                        px = motionEvent.getRawX();
                        py = motionEvent.getRawY();
                    }


                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                 //   Log.d("VAY",motionEvent.getPointerCount() + " " +  motionEvent.getPointerId(motionEvent.getActionIndex()));

                    break;
                case MotionEvent.ACTION_POINTER_UP:




                    break;

                case MotionEvent.ACTION_UP:


                    params.width = (int)(bitmap.getWidth() * mScaleFactor);
                    params.height = (int)(bitmap.getHeight() * mScaleFactor);
                    setLayoutParams(params);

                    break;

                default:
                    break;
            }
                return true;
            }
        });

    }

    public CustomBitmap(Activity context, AttributeSet attrs, Bitmap bitmap) {
        super(context, attrs);
        this.c = context;

        this.bitmap = bitmap;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Init();


        setImageDrawable(easyRound(bitmap, 50));

    }



    public Drawable easyRound(Bitmap source, int pixels)
    {
        RoundedBitmapDrawable drawable =  RoundedBitmapDrawableFactory.create(getContext().getResources(), source);
        drawable.setCornerRadius(pixels);
        return drawable;
    }


    @Override
    protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
        setRotation(mAngle);
       // setBackgroundColor(Color.CYAN);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.restore();
    }

    @Override
    public void onClick(View view) {

        //view.showContextMenu();

    }


    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor*= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor ,2));
            invalidate();
            return true;
        }
    }


    public class RotationGestureListener implements RotationGestureDetector.OnRotationGestureListener
    {

        @Override
        public void onRotation(RotationGestureDetector rotationDetector) {
            float angle = rotationDetector.getAngle();
            mAngle = angle;
        }
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
        c.getMenuInflater().inflate(R.menu.art_option_menu, menu);
    }
}
