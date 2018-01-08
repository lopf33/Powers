package de.berufsschule_freising.powers.guiitems;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

/**
 * Created by cami on 30.11.17.
 */

public class GridItem extends android.support.v7.widget.AppCompatTextView {

    private static final int GOAL = 256;
    private int value;

    public GridItem(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
    }

    public void setValue(int value) {
        this.value = value;
        setText(String.valueOf(value));
        if(value != 0) {
            int r = (value < 128) ? (Math.round(255f/128f*value)) : 255;
            int g = (value < 128) ? 255 : 255 - (Math.round(255f/128f*(value-128)));
            super.setBackgroundColor(Color.rgb(r,g,0));
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
