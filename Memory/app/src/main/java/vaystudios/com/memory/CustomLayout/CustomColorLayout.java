package vaystudios.com.memory.CustomLayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import vaystudios.com.memory.View.CustomColorIcon;

/**
 * Created by Devan on 2/18/2017.
 */

public class CustomColorLayout extends HorizontalScrollView {

    public static int[] ColorTray =
            {
                    Color.BLACK,
                    Color.GRAY,
                    Color.LTGRAY,
                    Color.GREEN,
                    Color.BLUE,
                    Color.YELLOW,
                    Color.CYAN,
                    Color.MAGENTA,
                    Color.RED,
                    Color.WHITE


            };


    private LinearLayout linearLayout;

    public CustomColorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Init();
            }
        }, 100);
        setZ(1);
    }



    private void Init()
    {
        linearLayout = (LinearLayout)getChildAt(0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
        layoutParams.topMargin = 150;
        layoutParams.leftMargin = 20;
        setLayoutParams(layoutParams);
        setHorizontalScrollBarEnabled(false);
        for(int i = 0;i < ColorTray.length; i++)
        {
            CustomColorIcon customColorIcon = new CustomColorIcon(getContext(), null, ColorTray[i]);
            customColorIcon.setPaddingRelative(15,15,15,15);
            linearLayout.addView(customColorIcon);
        }

        setVisibility(INVISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
