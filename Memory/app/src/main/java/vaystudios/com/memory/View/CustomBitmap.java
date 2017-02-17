package vaystudios.com.memory.View;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/5/2017.
 */

public class CustomBitmap extends ImageView
{


    public Bitmap b;
    private static CustomBitmap instance;
    private boolean interact;
    private View view;
    private View parentView;
    private Activity c;
    private Bitmap bitmap;
    private Paint paint;

    private GestureDetector gestureDetector;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private boolean move;
    private float mScaleFactor = 1f;
    private float mAngle;
    private float px;
    private float py;
    private float sx, sy;
    private float tx, ty;
    private View[] canvasUI;


    private void Init()
    {
        view = (View)this;

        setOnTouchListener(new TouchListener());

        gestureDetector = new GestureDetector(getContext(), new GestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        rotationGestureDetector = new RotationGestureDetector(new RotationGestureListener(), this);
        setElevation(10);



        setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                if(interact)
                {
                    setVisibility(View.GONE);
                }
            }
        });

   }

    public CustomBitmap(Activity context, AttributeSet attrs, Bitmap bitmap, View parentView, boolean interact) {
        super(context, attrs, 0);
        Init();
        this.c = context;
        this.parentView = parentView;
        this.bitmap = bitmap;
        this.interact = interact;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvasUI = new View[2];
        canvasUI[0] = parentView.findViewById(R.id.btn_canvasOption);
        canvasUI[1] = parentView.findViewById(R.id.btn_canvasComplete);
        setImageDrawable(easyRound(bitmap, 50));
        setZ(-1);
    }


    public byte[] ToBytes()
    {
        byte[] bytes = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        bytes = stream.toByteArray();
        return bytes;
    }

    public void setInteract(boolean interact)
    {
        this.interact = interact;
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

        if(interact)
        {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
            layoutParams.width = (int)(bitmap.getWidth() * mScaleFactor);
            layoutParams.height = (int)(bitmap.getHeight() * mScaleFactor);
            setLayoutParams(layoutParams);
        }



    }


    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            if(interact)
            {
                mScaleFactor*= detector.getScaleFactor();
                mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor ,5.0f));
                invalidate();
            }
            return true;
        }
    }


    public class RotationGestureListener implements RotationGestureDetector.OnRotationGestureListener
    {

        @Override
        public void onRotation(RotationGestureDetector rotationDetector) {
            if(interact)
            {
                float angle = rotationDetector.getAngle();
                mAngle = angle;
                setRotation(mAngle);
            }
        }
    }


    public class TouchListener implements  View.OnTouchListener
    {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            gestureDetector.onTouchEvent(motionEvent);
            scaleGestureDetector.onTouchEvent(motionEvent);
            rotationGestureDetector.onTouchEvent(motionEvent) ;


            if(interact)
            {
                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        move = true;
                        sx = getX();
                        sy = getY();
                        px = motionEvent.getRawX();
                        py = motionEvent.getRawY();
                        if(instance == null)
                        {
                            instance = (CustomBitmap)view;
                        }


                        hideCanvasUI();
                        break;
                    }

                    case MotionEvent.ACTION_MOVE:
                    {
                        if(move && instance == view)
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
                            instance = (CustomBitmap)view;
                            hideCanvasUI();
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
                        if(instance != null && instance == view)
                        {
                            instance = null;

                        }
                        showCanvasUI();
                        break;
                    }
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


    private void hideCanvasUI()
    {
        canvasUI[0].setVisibility(View.INVISIBLE);
        canvasUI[1].setVisibility(View.INVISIBLE);

    }

    private void showCanvasUI()
    {
        canvasUI[0].setVisibility(View.VISIBLE);
        canvasUI[1].setVisibility(View.VISIBLE);


    }




}
