package vaystudios.com.memory.CustomLayout;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import vaystudios.com.memory.View.Icon.CustomBitmapOptionIcon;

/**
 * Created by Devan on 2/21/2017.
 */

public class CustomBitmapEditTray extends HorizontalScrollView {

    private LinearLayout linearLayout;

    public CustomBitmapEditTray(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Init();
            }
        }, 100);
    }

    private void Init()
    {
        linearLayout = (LinearLayout)getChildAt(0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
        layoutParams.topMargin = 150;
        layoutParams.leftMargin = 20;
        setLayoutParams(layoutParams);
        setHorizontalScrollBarEnabled(false);
        CustomBitmapOptionIcon smoothEdge= new CustomBitmapOptionIcon(getContext(), null);
        smoothEdge.setPaddingRelative(15,15,15,15);
        linearLayout.addView(smoothEdge);

       setVisibility(View.INVISIBLE);

    }

}
