package vaystudios.com.memory.View;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import vaystudios.com.memory.CustomLayout.CustomBitmapEditTray;
import vaystudios.com.memory.CustomLayout.CustomColorLayout;
import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.Gesture.DragListener;
import vaystudios.com.memory.Util.Gesture.RotationListener;
import vaystudios.com.memory.Util.Gesture.ScaleListener;
import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/5/2017.
 */

public class CustomBitmap extends ImageView
{

    private static float ScaleLimit = 3.0f;
    private boolean interact;
    public Bitmap bitmap;
    private Paint paint;
    private View view;
    private DragListener dragListener;
    private ScaleListener scaleListener;
    private RotationListener rotationListener;
    private GestureDetector gestureDetector;
    private CustomBitmapEditTray customBitmapEditTray;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    public boolean inScale;
    public float edge;


    public CustomBitmap(Activity context, AttributeSet attrs, Bitmap bitmap, View parentView, boolean interact) {
        super(context, attrs, 0);
        this.bitmap = bitmap;
        this.interact = interact;
        this.view = this;
        Init();
    }


    private void Init()
    {
        inScale = true;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        dragListener = new DragListener(getContext());
        scaleListener = new ScaleListener(getContext(), this, ScaleLimit);
        rotationListener = new RotationListener(getContext(), this);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), scaleListener);
        rotationGestureDetector = new RotationGestureDetector(rotationListener, this);
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
        setOnTouchListener(new TouchListener());
        setElevation(10);
        setImageDrawable(easyRound(bitmap, 50));
        setZ(-1);




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
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }


    public class TouchListener implements  View.OnTouchListener
    {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            dragListener.onTouch(view, motionEvent);
            scaleGestureDetector.onTouchEvent(motionEvent);
            rotationGestureDetector.onTouchEvent(motionEvent);
            gestureDetector.onTouchEvent(motionEvent);
            return view.isClickable();
        }
    }

    public void setInteract(boolean interact)
    {
        this.interact = interact;
    }


    public class GestureListener extends  GestureDetector.SimpleOnGestureListener
    {

//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            if(customBitmapEditTray != null)
//            {
//                customBitmapEditTray.setVisibility(View.INVISIBLE);
//            }
//            return true;
//        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if(customBitmapEditTray == null)
            {
                ViewGroup viewGroup = (ViewGroup)getParent();
                customBitmapEditTray = (CustomBitmapEditTray) viewGroup.findViewById(R.id.canvasEdit_bitmapTray);
            }

            inScale = !inScale;
            customBitmapEditTray.setVisibility(customBitmapEditTray.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
            return true;
        }
    }


}
