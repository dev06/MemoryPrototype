package vaystudios.com.memory.Util.Gesture;

import android.content.Context;
import android.util.Log;
import android.view.View;

import vaystudios.com.memory.Util.RotationGestureDetector;

/**
 * Created by Devan on 2/18/2017.
 */

public class RotationListener implements RotationGestureDetector.OnRotationGestureListener {

    private Context context;
    private View view;

    public RotationListener(Context context, View view)
    {
        this.context = context;
        this.view = view;
    }




    @Override
    public void onRotation(RotationGestureDetector rotationDetector) {
        float angle = rotationDetector.getAngle();
        view.setRotation(angle);


    }
}
