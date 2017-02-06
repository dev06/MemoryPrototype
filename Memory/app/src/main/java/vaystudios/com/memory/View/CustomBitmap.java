package vaystudios.com.memory.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Devan on 2/5/2017.
 */

public class CustomBitmap extends View
{

    Bitmap bitmap;
    Paint paint;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1f;



    float px;
    float py;
    float sx, sy;
    private void Init()
    {
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                scaleGestureDetector.onTouchEvent(motionEvent);

                switch(motionEvent.getAction())
                {

                    case MotionEvent.ACTION_MOVE:
                        float x_cord =motionEvent.getRawX() ;
                        float y_cord = motionEvent.getRawY();

                        float dx = x_cord - px;
                        float dy = y_cord - py;


                        float vx = sx + dx;
                        float vy = sy + dy;
                        setX(vx);
                        setY(vy);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        sx = getX();
                        sy = getY();
                        px = motionEvent.getRawX();
                        py = motionEvent.getRawY();

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
        this.bitmap = bitmap;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix m = new Matrix();
        m.postScale(1,1);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.drawBitmap(bitmap,  m, paint);
        canvas.restore();
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
