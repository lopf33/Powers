package de.berufsschule_freising.powers.adapters;

import android.content.Context;
import android.widget.GridView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import de.berufsschule_freising.powers.guiitems.GridItem;

/**
 * Created by cami on 09.11.17.
 */

public class DataAdapter extends BaseAdapter {

    private Context context;
    private int sizeX,sizeY;

    public DataAdapter(Context context, int sizeX, int sizeY) {
        this.context = context;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    public int getCount() {
        return sizeX*sizeY;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridItem gridItem = new GridItem(context);
        gridItem.setValue(2);
        if (convertView == null) {
            gridItem.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            gridItem.setPadding(8, 8, 8, 8);
        } else {
            gridItem = (GridItem) convertView;
        }
        return gridItem;

    }
}
