package vaystudios.com.memory.Util.Gesture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import vaystudios.com.memory.View.CustomBitmap;
import vaystudios.com.memory.View.CustomText;

/**
 * Created by Devan on 2/18/2017.
 */

public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
{
    private float divisor = .4f;
    private float scaleFactor = 1.0f;
    private Context context;
    private View view;
    private float scaleLimit;
    private float updatedScale;
    private float pUpdated;
    private float previousScale;
    private float newScale = 1;
    public ScaleListener(Context context, View view, float scaleLimit)
    {
        this.scaleLimit = scaleLimit;
        this.context = context;
        this.view = view;

        if(view instanceof CustomText)
        {
            CustomText text =(CustomText)view;
            if (text.interact)
            {
                newScale += (scaleFactor - previousScale);
                view.setScaleX(newScale * divisor);
                view.setScaleY(newScale * divisor);
                previousScale = scaleFactor;
            }
        }

        if(view instanceof  CustomBitmap)
        {
            CustomBitmap bitmap = (CustomBitmap)view;
            if(bitmap.interact)
            {
                newScale += (scaleFactor - previousScale);
                view.setScaleX(newScale * divisor);
                view.setScaleY(newScale * divisor);
                previousScale = scaleFactor;
            }
        }
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFactor *= detector.getScaleFactor();

        scaleFactor = Math.max(.05f, Math.min(scaleFactor, scaleLimit / divisor));



        newScale += (scaleFactor - previousScale);
        updatedScale+=(newScale - pUpdated);
        view.setScaleX(updatedScale * divisor);
        view.setScaleY(updatedScale * divisor);
        previousScale = scaleFactor;
        pUpdated = newScale;
        view.invalidate();
        return true;
    }
}
