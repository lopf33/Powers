package de.berufsschule_freising.powers.guiitems;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;

/**
 * Created by cami on 30.11.17.
 */

public class GridItem extends android.support.v7.widget.AppCompatTextView {


    private int value;

    public GridItem(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        this.value = 2;
    }

    public void setValue(int value) {
        this.value = value;
        setText(String.valueOf(value));
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

    @Override
    public Drawable getBackground() {
        super.setBackgroundColor(Color.RED);
        return super.getBackground();
    }

}
