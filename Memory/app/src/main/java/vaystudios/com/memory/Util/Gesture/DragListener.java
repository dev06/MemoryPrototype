package vaystudios.com.memory.Util.Gesture;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import vaystudios.com.memory.View.CustomBitmap;

/**
 * Created by Devan on 2/18/2017.
 */

public class DragListener implements View.OnTouchListener{

    private static View draggingView;
    private Context context;
    private boolean move;
    private float px;
    private float py;
    private float sx, sy;
    private float tx, ty;

    public DragListener(Context context)
    {
        this.context = context;
    }


    @Override
    public boolean onTouch(View v, MotionEvent motionEvent)
    {
            if(draggingView == null)
            {
                v.setClickable(true);
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);

            }else
            {
                if(draggingView != v) {
                    v.setClickable(false);
                    v.setFocusable(false);
                    v.setFocusableInTouchMode(false);
                }
            }

            switch(motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    move = true;
                    sx = v.getX();
                    sy = v.getY();
                    px = motionEvent.getRawX();
                    py = motionEvent.getRawY();

                    if(draggingView == null)
                    {
                        draggingView = v;
                    }

                    break;
                }

                case MotionEvent.ACTION_MOVE:
                {

                    if(move && draggingView == v)
                    {
                        float x_cord = motionEvent.getRawX() ;
                        float y_cord = motionEvent.getRawY();

                        float dx = x_cord - px;
                        float dy = y_cord - py;

                        float vx = sx + dx;
                        float vy = sy + dy;
                        tx = vx;
                        ty = vy;

                        v.setX(tx);
                        v.setY(ty);

                    }

                    break;

                }

                case MotionEvent.ACTION_POINTER_DOWN:
                {
                    move = true;
                    sx = v.getX();
                    sy = v.getY();
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
                    if(draggingView == v)
                    {
                        draggingView = null;
                    }
                    move = false;
                    break;
                }

        }
        return true;
    }
}
