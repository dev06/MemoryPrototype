package vaystudios.com.memory.View;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/5/2017.
 */

public class CustomBitmap extends ImageView
{
    View view;
    View parentView;
    Activity c;
    Bitmap bitmap;
    Paint paint;

    private GestureDetector gestureDetector;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;


    private float mScaleFactor = 1f;
    private float mAngle;
    float px;
    float py;
    float sx, sy;
    float tx, ty;
//    float mPosX;
//    float mPosY;
//    float mLastTouchX;
//    float mLastTouchY;
//    float mLastGestureX;
//    float mLastGestureY;
//    private static final int INVALID_POINTER_ID = -1;
//    private int mActivePointerId = INVALID_POINTER_ID;

    ImageView deleteButton;

    private void Init()
    {
        setOnTouchListener(new TouchListener());
        view = (View)this;
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        rotationGestureDetector = new RotationGestureDetector(new RotationGestureListener(), this);
        deleteButton = (ImageView)parentView.findViewById(R.id.btn_canvasDelete);


        setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {


                setVisibility(View.GONE);


            }
        });

   }

    public CustomBitmap(Activity context, AttributeSet attrs, Bitmap bitmap, View parentView) {
        super(context, attrs);
        this.c = context;
        this.parentView = parentView;
        this.bitmap = bitmap;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        setImageDrawable(easyRound(bitmap, 50));

        Init();

    }


    public Drawable easyRound(Bitmap source, int pixels)
    {
        RoundedBitmapDrawable drawable =  RoundedBitmapDrawableFactory.create(getContext().getResources(), source);
        drawable.setCornerRadius(pixels);
        drawable.setAntiAlias(true);
        drawable.setColorFilter(new ColorFilter());
        return drawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        setRotation(mAngle);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
        layoutParams.width = (int)(bitmap.getWidth() * mScaleFactor);
        layoutParams.height = (int)(bitmap.getHeight() * mScaleFactor);

        setLayoutParams(layoutParams);

//
//        setScaleY(mScaleFactor);
//        setScaleX(mScaleFactor);
    }

//    @Override
//    public void onClick(View view) {
//
//        //view.showContextMenu();
//
//    }

    boolean move;
//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//
//        gestureDetector.onTouchEvent(motionEvent);
//        scaleGestureDetector.onTouchEvent(motionEvent);
//        rotationGestureDetector.onTouchEvent(motionEvent) ;
//
//        switch(motionEvent.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//            {
//                move = true;
//                sx = getX();
//                sy = getY();
//                px = motionEvent.getRawX();
//                py = motionEvent.getRawY();
//                break;
//            }
//
//            case MotionEvent.ACTION_MOVE:
//            {
//               if(move)
//                {
//                    float x_cord = motionEvent.getRawX() ;
//                    float y_cord = motionEvent.getRawY();
//
//                    float dx = x_cord - px;
//                    float dy = y_cord - py;
//
//                    float vx = sx + dx;
//                    float vy = sy + dy;
//                    tx = vx;
//                    ty = vy;
//                    setX(tx);
//                    setY(ty);
//                }
//
//                break;
//
//            }
//
//            case MotionEvent.ACTION_POINTER_DOWN:
//            {
//                move = true;
//                sx = getX();
//                sy = getY();
//                px = motionEvent.getRawX();
//                py = motionEvent.getRawY();
//                break;
//            }
//
//            case MotionEvent.ACTION_POINTER_UP:
//            {
//                move = false;
//                break;
//            }
//            case MotionEvent.ACTION_UP:
//            {
//                move = false;
//                break;
//            }
//        }
//        return true;
//    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor*= detector.getScaleFactor();
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor ,5.0f));

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


    public class TouchListener implements  View.OnTouchListener
    {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            gestureDetector.onTouchEvent(motionEvent);
            scaleGestureDetector.onTouchEvent(motionEvent);
            rotationGestureDetector.onTouchEvent(motionEvent) ;

//
//            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//                case MotionEvent.ACTION_DOWN: {
//                    if (!scaleGestureDetector.isInProgress()) {
//                        final float x = motionEvent.getX();
//                        final float y = motionEvent.getY();
//
//                        mLastTouchX = x;
//                        mLastTouchY = y;
//
//                        mActivePointerId = motionEvent.getPointerId(0);
//                    }
//                    break;
//                }
//
//                case MotionEvent.ACTION_POINTER_DOWN: {
//                    if (!scaleGestureDetector.isInProgress()) {
//                        final float gx = scaleGestureDetector.getFocusX();
//                        final float gy = scaleGestureDetector.getFocusY();
//
//                        mLastGestureX = gx;
//                        mLastGestureY = gy;
//                    }
//                    break;
//                }
//
//                case MotionEvent.ACTION_MOVE: {
//                    if (!scaleGestureDetector.isInProgress()) {
//                        final int pointerIndex = motionEvent.findPointerIndex(mActivePointerId);
//                        final float x = motionEvent.getX(pointerIndex);
//                        final float y = motionEvent.getY(pointerIndex);
//
//                        final float dx = x - mLastTouchX;
//                        final float dy = y - mLastTouchY;
//
//                        mPosX += dx;
//                        mPosY += dy;
//
//                        invalidate();
//
//                        mLastTouchX = x;
//                        mLastTouchY = y;
//                    } else {
//                        final float gx = scaleGestureDetector.getFocusX();
//                        final float gy = scaleGestureDetector.getFocusY();
//
//                        final float gdx = gx - mLastGestureX;
//                        final float gdy = gy - mLastGestureY;
//
//                        mPosX += gdx;
//                        mPosY += gdy;
//
//                        // SOMETHING NEEDS TO HAPPEN RIGHT HERE.
//
//                        invalidate();
//
//                        mLastGestureX = gx;
//                        mLastGestureY = gy;
//                    }
//
//                    break;
//                }
//
//                case MotionEvent.ACTION_POINTER_UP: {
//
//                    final int pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
//                    final int pointerId = motionEvent.getPointerId(pointerIndex);
//                    if (pointerId == mActivePointerId) {
//                        // This was our active pointer going up. Choose a new
//                        // active pointer and adjust accordingly.
//                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//
//                        mLastTouchX = motionEvent.getX(newPointerIndex);
//                        mLastTouchY = motionEvent.getY(newPointerIndex);
//
//                        mActivePointerId = motionEvent.getPointerId(newPointerIndex);
//                    } else {
//                        final int tempPointerIndex = motionEvent.findPointerIndex(mActivePointerId);
//
//                        mLastTouchX = motionEvent.getX(tempPointerIndex);
//                        mLastTouchY = motionEvent.getY(tempPointerIndex);
//                    }
//
//                    break;
//                }
//            }

            switch(motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    move = true;
                    sx = getX();
                    sy = getY();
                    px = motionEvent.getRawX();
                    py = motionEvent.getRawY();
                    break;
                }

                case MotionEvent.ACTION_MOVE:
                {
                    if(move)
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

                }

                case MotionEvent.ACTION_POINTER_DOWN:
                {
                    move = true;
                    sx = getX();
                    sy = getY();
                    px = motionEvent.getRawX();
                    py = motionEvent.getRawY();
                    break;
                }

                case MotionEvent.ACTION_POINTER_UP:
                {
                    move = false;
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    move = false;
                    break;
                }
            }
            return true;
        }
    }


    private class GestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            view.showContextMenu();
            return true;
        }
    }


    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);

    }




}
