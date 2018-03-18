package de.berufsschule_freising.powers.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.berufsschule_freising.powers.R;
import de.berufsschule_freising.powers.firebase.User;

/**
 * Created by cami on 06.03.18.
 */

public class CustomListAdapter extends ArrayAdapter<User> {

    private List<User> data;
    private User currentUser;
    private Context context;

    public CustomListAdapter(Context context, List<User> data, User currentUser)
    {
        super(context, R.layout.row_item, data);
        this.data = data;
        this.currentUser = currentUser;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.row_item, null);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.rounded_corner);
        if(data.get(position).getName().equals(currentUser.getName()))
        {
            drawable.setColorFilter(Color.rgb(155,255,155), PorterDuff.Mode.SRC);
        }
        else {
            drawable.setColorFilter(Color.rgb(245,245,245), PorterDuff.Mode.SRC);
        }
        v.setBackground(drawable);
        TextView userView = (TextView) v.findViewById(R.id.user);
        TextView scoreView = (TextView) v.findViewById(R.id.score);
        userView.setText(position+1 + ". " + data.get(position).getName());
        scoreView.setText(String.valueOf(data.get(position).getHighScore()));
        return v;
    }

}
