package de.berufsschule_freising.powers.guiitems;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import java.util.HashMap;

import de.berufsschule_freising.powers.R;

/**
 * Created by cami on 30.11.17.
 */

public class GridItem extends android.support.v7.widget.AppCompatTextView {

    private static final HashMap<Integer, Integer> colors = new HashMap<>();
    private int value;
    private Context context;

    public GridItem(Context context) {
        super(context);
        this.context = context;
        setGravity(Gravity.CENTER);
        setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner));

        colors.put(2, Color.rgb(0,191,255));
        colors.put(4, Color.rgb(0,255,255));
        colors.put(8, Color.rgb(0,255,128));
        colors.put(16, Color.rgb(192,255,0));
        colors.put(32, Color.rgb(255,255,0));
        colors.put(64, Color.rgb(255,191,0));
        colors.put(128, Color.rgb(255,128,0));
        colors.put(256, Color.rgb(255,0,0));
        colors.put(512, Color.rgb(255,128,0));
        colors.put(1024, Color.rgb(255,0,255));
        colors.put(2048, Color.rgb(191,0,255));
    }

    public void setValue(int value) {
        this.value = value;
        if(value != 0) {
            setText(String.valueOf(value));
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.rounded_corner);
            drawable.setColorFilter(colors.get(value), PorterDuff.Mode.SRC);
            setBackground(drawable);
            //super.setBackgroundColor(colors.get(value));
            //setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corner));
        }

    }

    public int getValue() {
        return this.value;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }
}
