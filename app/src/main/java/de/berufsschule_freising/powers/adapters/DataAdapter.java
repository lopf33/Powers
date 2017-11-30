package de.berufsschule_freising.powers.adapters;

import android.content.Context;
import android.widget.GridView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import de.berufsschule_freising.powers.guiitems.GridItem;
import de.berufsschule_freising.powers.interfaces.GridDataSupplier;

/**
 * Created by cami on 09.11.17.
 */

public class DataAdapter extends BaseAdapter {

    private Context context;
    private GridDataSupplier dataSupplier;

    public DataAdapter(Context context, GridDataSupplier dataSupplier) {
        this.context = context;
        this.dataSupplier = dataSupplier;
    }

    @Override
    public int getCount() {
        return dataSupplier.getSizeX()*dataSupplier.getSizeY();
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
        int xpos, ypos = 0;
        GridItem gridItem = new GridItem(context);
        while(position - (dataSupplier.getSizeX()-1) > 0) {
            ypos++;
            position = position - dataSupplier.getSizeX();
        }
        xpos = position;

        gridItem.setValue(dataSupplier.itemAt(xpos, ypos));
        if (convertView == null) {
            gridItem.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            gridItem.setPadding(8, 8, 8, 8);
        } else {
            gridItem = (GridItem) convertView;
        }
        return gridItem;

    }
}
