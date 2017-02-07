package vaystudios.com.memory.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/5/2017.
 */

public class CustomBitmap extends View implements RotationGestureDetector.OnRotationGestureListener
{
    Context c;
    Bitmap bitmap;
    Paint paint;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1f;


    float px;
    float py;
    float sx, sy;
    private void Init()
    {

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        rotationGestureDetector = new RotationGestureDetector(this );
        final int screenWidth = getResources().getDisplayMetrics().widthPixels;
        final int screenHeight = getResources().getDisplayMetrics().heightPixels;
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
                        float x_cord = motionEvent.getRawX() ;
                        float y_cord = motionEvent.getRawY();


                        float dx = x_cord - px;
                        float dy = y_cord - py;


                        float vx = sx + dx;
                        float vy = sy + dy;

                        int bx = (int)vx;
                        int by = (int)vy;
                        int bxs = (int)(bitmap.getWidth() * mScaleFactor);
                        int bys = (int)(bitmap.getHeight() * mScaleFactor);

                        if(bx + bxs >= screenWidth)
                        {
                            bx = screenWidth - bxs;
                        }

                        if(bx < 0)
                        {
                            bx = 0;
                        }

                        if(by + bys >= screenHeight)
                        {
                            by = screenHeight - bys;
                        }
                        if(by < 0)
                        {
                            by = 0;
                        }
                        setX(bx);
                        setY(by);

                        break;

                    case MotionEvent.ACTION_DOWN:
                        sx = getX();
                        sy = getY();
                        px = motionEvent.getRawX();
                        py = motionEvent.getRawY();

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public CustomBitmap(Context context, AttributeSet attrs, Bitmap bitmap) {
        super(context, attrs);
        this.c = context;
        this.bitmap = bitmap;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix m = new Matrix();
        m.postRotate(-mAngle, bitmap.getWidth() / 2, bitmap.getHeight()/ 2);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.drawBitmap(bitmap,  m, paint);


        canvas.restore();
    }
    private float mAngle;
    @Override
    public void OnRotation(RotationGestureDetector rotationDetector) {
        float angle = rotationDetector.getAngle();
        mAngle = angle;
        Log.d("Vay", "Angle - > " + angle);
    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor*= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor ,.9f));
            invalidate();
            return true;
        }
    }




}
