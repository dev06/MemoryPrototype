package vaystudios.com.memory.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import vaystudios.com.memory.MainActivity;

/**
 * Created by Devan on 2/4/2017.
 */

public class CanvasView extends View {

    Rect r;
    Paint p;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        p = new Paint();
        p.setStyle(Paint.Style.FILL);
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i =0 ;i < MainActivity.bitmaps.size(); i++)
        {
            Matrix m = new Matrix();
            m.setScale(1,1);
            CustomBitmap b = MainActivity.bitmaps.get(i);

          //  canvas.drawBitmap(b.bitmap, b.x, b.y, p);
        }
    }


//    float x;
//    float y;
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch(event.getAction())
//        {
//
//
//            case MotionEvent.ACTION_MOVE:
//            {
//                x = event.getX();
//                y = event.getY();
//                invalidate();
//                break;
//            }
//
//
//
//        }
//
//        return true;
//
//    }
//
//



}
