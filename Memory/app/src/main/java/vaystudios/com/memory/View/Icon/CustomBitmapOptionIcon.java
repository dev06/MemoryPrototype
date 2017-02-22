package vaystudios.com.memory.View.Icon;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vaystudios.com.memory.R;

/**
 * Created by Devan on 2/21/2017.
 */

public class CustomBitmapOptionIcon extends ImageView {

    public CustomBitmapOptionIcon(Context context, AttributeSet attrs) {
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)getLayoutParams();
        params.width = 150;
        params.height = 150;
        setLayoutParams(params);
        setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.regular_oval));
        getDrawable().setTint(Color.WHITE);
    }

}
