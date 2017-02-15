package vaystudios.com.memory.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import vaystudios.com.memory.R;
import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/10/2017.
 */

public class CustomText extends EditText {

    private static CustomText instance;
    private GestureDetector gestureDetector;
    private RotationGestureDetector rotationGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private boolean interact;
    private boolean move;
    private float mScaleFactor = 1f;
    private float mAngle;
    private float px;
    private float py;
    private float sx, sy;
    private float tx, ty;
    private float scale = 50;
    private float saveRot;
    private float saveX;
    private float saveY;
    private float saveScaleX;
    private float saveScaleY;
    private View[] canvasUI;
    private Paint paint;
    private Display display;
    private DisplayMetrics metrics = new DisplayMetrics();
    private View parentView;
    public CustomText(Context context, AttributeSet attrs, View parentView, boolean interact) {
        super(context, attrs);
        this.interact = interact;
        this.parentView = parentView;
        canvasUI= new View[2];
        canvasUI[0] = parentView.findViewById(R.id.btn_canvasOption);
        canvasUI[1] = parentView.findViewById(R.id.btn_canvasComplete);

        Typeface face= Typeface.createFromAsset(context.getAssets(), "arial.ttf");
        this.setTypeface(face);

        setTextColor(Color.BLACK);


        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        display.getMetrics(metrics);


        setX(0);
        setY(0);



        setHint("New Text");
        setTextSize(30);
        Init();

        setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
        setSingleLine(false);
        setHorizontallyScrolling(false);

    }


    private void Init()
    {
        setOnTouchListener(new TouchListener());
        gestureDetector = new GestureDetector(getContext(), new GestureListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        rotationGestureDetector = new RotationGestureDetector(new RotationGestureListener(), this);

        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        setBackground(null);

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
                return true;
            }

        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(keyboardActive)
            setX(metrics.widthPixels / 2 - getWidth() / 2);

    }



    private float previousScale;
    float newScale = 20.0f;
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            if(interact)
            {
                mScaleFactor*= detector.getScaleFactor();
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor ,3.0f));
                newScale += -(previousScale - mScaleFactor) * 100.0f;
                previousScale = mScaleFactor;



                if(!keyboardActive)
                {
                    setScaleX(newScale / 100.0f);
                    setScaleY(newScale / 100.0f);
                }

                invalidate();
            }


            return true;
        }

    }


    public class RotationGestureListener implements RotationGestureDetector.OnRotationGestureListener
    {

        @Override
        public void onRotation(RotationGestureDetector rotationDetector) {
            float angle = rotationDetector.getAngle();
            if(!keyboardActive && interact)
            {
                mAngle = angle;
                setRotation(mAngle);
            }
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

                saveRot = mAngle;
                saveScaleX = getScaleX();
                saveScaleY = getScaleY();
                setRotation(0);
                setScaleX(1.0f);
                setScaleY(1.0f);

                setY(500);
                setCursorVisible(true);
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);

                setSelection(getText().length());
                keyboardActive = true;
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
            keyboardActive = false;
            setRotation(saveRot);
            setSelection(0);
            setX(saveX);
            setY(saveY);
            setScaleX(saveScaleX);
            setScaleY(saveScaleY);
            this.clearFocus();
        }

    }


    boolean keyboardActive;

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
                            instance = (CustomText) view;
                        }

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
                            if(!keyboardActive)
                                setX(tx);
                            setY(ty);
                            instance = (CustomText) view;

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

            return instance == view;
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
            if(!keyboardActive)
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

            return true;
        }
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
