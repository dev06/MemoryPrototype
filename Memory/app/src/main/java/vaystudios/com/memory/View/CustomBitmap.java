package vaystudios.com.memory.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Base64InputStream;
import android.util.Base64OutputStream;
import android.util.Log;
import android.util.Xml;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ByteArrayPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.Gesture.DragListener;
import vaystudios.com.memory.Util.Gesture.RotationListener;
import vaystudios.com.memory.Util.Gesture.ScaleListener;
import vaystudios.com.memory.Util.MiscIO.Transform;
import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/5/2017.
 */

public class CustomBitmap extends ImageView
{

    public Transform transform;
    private static float ScaleLimit = 3.0f;
    public boolean interact;
    public Bitmap bitmap;
    private Paint paint;
    private View view;
    private DragListener dragListener;
    private ScaleListener scaleListener;
    private RotationListener rotationListener;
    private GestureDetector gestureDetector;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    public boolean inScale;


    private int location[] = new int[2];
    public CustomBitmap(Activity context, AttributeSet attrs, Bitmap bitmap, View parentView, boolean interact) {
        super(context, attrs, 0);
        this.bitmap = bitmap;
        this.interact = interact;
        this.view = this;
        Init();
    }

    public CustomBitmap(Context context, AttributeSet attrs, String bytes, Transform  transform, boolean interact) {
        super(context, attrs, 0);
        this.bitmap =  StringToBitmap(bytes);
        this.interact = interact;
        this.view = this;
        Init();
        SetTransform(transform);
    }



    private void SetTransform(Transform transform)
    {

        setX(transform.getX());
        setY(transform.getY());
        setScaleX(transform.getSx());
        setScaleY(transform.getSy());
        setRotation(transform.getRot());
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



    public void SetTransform()
    {
        if(transform == null)
        {
            transform = new Transform();
        }

        if(location == null)
        {
            location = new int[2];
        }



        transform.setX(X());
        transform.setY(Y());
        transform.setSx(getScaleX());
        transform.setSy(getScaleY());
        transform.setRot(getRotation());

    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }


    public String ToBitmapString()
    {

        Log.d("PROCESS", "STEP 2 -> " + "Converting String");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("PROCESS", "STEP 3 -> " + "Converting Finished");
        return result;
    }

    public Bitmap StringToBitmap(String encodedString)
    {
        byte[] data = Base64.decode(encodedString, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }



    public class TouchListener implements  View.OnTouchListener
    {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            dragListener.onTouch(view, motionEvent);
            getLocationOnScreen(location);
            scaleGestureDetector.onTouchEvent(motionEvent);
            rotationGestureDetector.onTouchEvent(motionEvent);
            gestureDetector.onTouchEvent(motionEvent);

            Log.d("TRANSFORM", X() + " " + Y() + " " + getScaleX() + " " + getScaleY());
            SetTransform();
            return view.isClickable();
        }
    }

    public void setInteract(boolean interact)
    {
        this.interact = interact;
    }


    public class GestureListener extends  GestureDetector.SimpleOnGestureListener
    {

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            ViewGroup viewGroup = (ViewGroup)getParent();
            viewGroup.removeView(view);
            return true;
        }
    }


    public float X()
    {
        return location[0];
    }

    public float Y()
    {
        return location[1];
    }






}
