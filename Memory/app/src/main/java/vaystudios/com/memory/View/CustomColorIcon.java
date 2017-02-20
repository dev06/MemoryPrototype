package vaystudios.com.memory.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import vaystudios.com.memory.CustomLayout.CustomColorLayout;
import vaystudios.com.memory.MainActivity;
import vaystudios.com.memory.R;

/**
 * Created by Devan on 2/18/2017.
 */

public class CustomColorIcon extends ImageView {


    private int iconColor;
    private CustomText customText;
    public CustomColorIcon(Context context, AttributeSet attrs, int color) {
        super(context, attrs);
        this.iconColor = color;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
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

        getDrawable().setTint(iconColor);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustomText.GetEditText() == null) return;
                customText = CustomText.GetEditText();
                customText.SetTextColor(iconColor);
            }
        });
    }
}
