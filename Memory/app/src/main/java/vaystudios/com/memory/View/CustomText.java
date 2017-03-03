package vaystudios.com.memory.View;

import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.inputmethodservice.KeyboardView;
import android.os.Handler;
import android.text.InputType;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import vaystudios.com.memory.CustomLayout.CustomColorLayout;
import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.Gesture.DragListener;
import vaystudios.com.memory.Util.Gesture.RotationListener;
import vaystudios.com.memory.Util.Gesture.ScaleListener;
import vaystudios.com.memory.Util.MiscIO.Transform;
import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/10/2017.
 */

public class CustomText extends EditText {


    public Transform transform;
    public String text;
    private static boolean isEditting;
    private static CustomText inEditText;
    private GestureDetector gestureDetector;
    private Context context;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    public boolean interact;
    private View v;
    private long keyboardDelay = 200;
    private float saveRot;
    private float saveX;
    private float saveY;
    private float saveScaleX;
    private float saveScaleY;
    private Display display;
    private DisplayMetrics metrics = new DisplayMetrics();
    private DragListener dragListener;
    private ScaleListener scaleListener;
    private RotationListener rotationListener;
    private Window window;
    private View decorView;
    private CustomColorLayout customColorLayout;
    private int location[] = new int[2];
    public CustomText(Context context, AttributeSet attrs, View parentView, boolean interact)
    {
        super(context, attrs);
        this.interact = interact;
        this.context = context;
        this.v = this;
        SetTransfrom(new Transform());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Init();
            }
        }, 100);
    }

    public CustomText(Context context, AttributeSet attrs, String text, Transform transform, boolean interact)
    {
        super(context, attrs);
        this.interact = interact;
        this.context = context;
        this.v = this;
        this.text = text;
        SetTransfrom(transform);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Init();
            }
        }, 100);
        Toast.makeText(getContext(), getWidth() + " " + getHeight(), Toast.LENGTH_LONG).show();
    }


    private void Init()
    {

        setText(text);
        dragListener = new DragListener(getContext());
        scaleListener = new vaystudios.com.memory.Util.Gesture.ScaleListener(getContext(), this, 3.0f);
        rotationListener = new RotationListener(getContext(), this);
        gestureDetector = new GestureDetector(new GestureListener());
        setOnTouchListener(new TouchListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), scaleListener);
        rotationGestureDetector = new RotationGestureDetector(rotationListener, this);
        setOnEditorActionListener(new EditorAction());

        window = ((MainActivity)context).getWindow();
        decorView = window.getDecorView();
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);

        display = wm.getDefaultDisplay();
        display.getMetrics(metrics);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "arial.ttf");
        this.setTypeface(face);
        setBackground(null);
        setTextSize(30);
        setTextColor(Color.WHITE);
        setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        setSingleLine(false);
        setHorizontallyScrolling(false);
        saveX = metrics.widthPixels / 2 - getWidth() / 2;
        showKeyboard();

    }


    private void SetTransfrom(Transform transform)
    {
        Log.d("TEST", "Received -> " + transform.getX() + " x " + transform.getY());


        setX(transform.getX());
        setY(transform.getY());
        setScaleX(transform.getSx());
        setScaleY(transform.getSy());
        setRotation(transform.getRot());
      //  setWidth((int)transform.getWidth());
      //  setHeight((int)transform.getHeight());
    }


    public void SetTransform()
    {
        if(transform == null)
            transform= new Transform();

        if(location == null)
            location = new int[2];


        transform.setX((float)X());
        transform.setY((float)Y());
        transform.setSx(getScaleX());
        transform.setSy(getScaleY());
        transform.setRot(getRotation());
      //  transform.setWidth(getWidth());
       // transform.setHeight(getHeight());


    }



    private void setDecorView()
    {
        if(decorView == null)
            return;

        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }



    public void setInteract(boolean interact)
    {
        this.interact = interact;
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {

        if(keyboardActive && interact)
        {
            if(keyCode == KeyEvent.KEYCODE_BACK)
            {
                hideKeyboard();
                return isClickable();
            }

        }
        return isClickable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackgroundColor(Color.CYAN);
         if(keyboardActive)
         {
             setX(metrics.widthPixels / 2 - getWidth() / 2);
         }

    }


    private void showKeyboard()
    {
        if(interact)
        {

            requestFocus();
            if(isFocused())
            {

                saveX = getX();
                saveY = getY();

                saveRot = getRotation();
                saveScaleX = getScaleX();
                saveScaleY = getScaleY();

                ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(this,
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f),
                        PropertyValuesHolder.ofFloat("scaleY",  1.0f),
                        PropertyValuesHolder.ofFloat("translationY", 500.0f),
                        PropertyValuesHolder.ofFloat("translationX", metrics.widthPixels / 2 - getWidth() / 2),
                        PropertyValuesHolder.ofFloat("rotation", Math.abs(getRotation()) > 180 ? 360 : 0));
                scaleDown.setDuration(keyboardDelay);
                scaleDown.start();

                setCursorVisible(true);
                setSelection(getText().length());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                        keyboardActive = true;
                    }
                },keyboardDelay);

                isEditting = true;
                inEditText = this;

                if(customColorLayout == null)
                {
                    ViewGroup viewGroup = (ViewGroup)getParent();
                    customColorLayout = (CustomColorLayout)viewGroup.findViewById(R.id.canvasEdit_colorLayout);
                }

                   customColorLayout.setVisibility(VISIBLE);
            }
        }
    }

    private void hideKeyboard()
    {
        if(interact)
        {
            InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            setCursorVisible(false);

            setSelection(0);




            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(this,
                    PropertyValuesHolder.ofFloat("scaleX", saveScaleX),
                    PropertyValuesHolder.ofFloat("scaleY", saveScaleY),
                    PropertyValuesHolder.ofFloat("translationX", saveX),
                    PropertyValuesHolder.ofFloat("translationY", saveY),
                    PropertyValuesHolder.ofFloat("rotation", saveRot));
            scaleDown.setDuration(keyboardDelay);
            scaleDown.start();

            keyboardActive = false;
            clearFocus();
            setDecorView();
            isEditting = false;
            inEditText = null;
            text = getText().toString();
            if(customColorLayout != null)
            {
                customColorLayout.setVisibility(INVISIBLE);
            }

            if(getText().length() <=0)
            {
                setVisibility(View.GONE);
            }
        }
    }

    public float X()
    {

        return getX();
    }

    public float Y()
    {
        return getY();
    }



    boolean keyboardActive;

    public class TouchListener implements  View.OnTouchListener
    {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            gestureDetector.onTouchEvent(motionEvent);
            if(keyboardActive == false)
            {
                getLocationInWindow(location);
                dragListener.onTouch(view, motionEvent);
                rotationGestureDetector.onTouchEvent(motionEvent) ;
                scaleGestureDetector.onTouchEvent(motionEvent);
                //   Log.d("TEXT", "Location -> " + X() + " x " + Y() + " | Scale -> " + getScaleX() + " x " + getScaleY() );
                //    Log.d("TEXT_CAL", "Location -> " + X() + " / " + MainActivity.DISPLAY_WIDTH + " = " + (X() / MainActivity.DISPLAY_WIDTH) + "%");


                Log.d("TEXT_CAL", getWidth() + " " + getHeight());

                // Log.d("TEXT_CAL", "Location -> " + Y() + " / " + MainActivity.DISPLAY_HEIGHT + " = " + (Y() + " / " + MainActivity.DISPLAY_HEIGHT) + "%");

                SetTransform();


            }

            return view.isClickable();
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
            if(!keyboardActive && !isEditting)
            {
                showKeyboard();
            }

            return true;
        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            if(keyboardActive)
            {
                hideKeyboard();
            }

            return isClickable();
        }
    }

    public static CustomText GetEditText()
    {
        return inEditText;
    }

    public void SetTextColor(int color)
    {
        setTextColor(color);
    }




    private class EditorAction implements  OnEditorActionListener
    {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

            if(i == EditorInfo.IME_ACTION_DONE)
            {
                hideKeyboard();
                return true;
            }
            return false;
        }


    }


}
