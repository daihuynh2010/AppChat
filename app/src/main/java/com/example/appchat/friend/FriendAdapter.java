package com.example.appchat.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.appchat.R;
import com.example.appchat.user.Users;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Users> {
    private int resourceLayout;
    private Context mContext;
    ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    public FriendAdapter(Context context, int resource, List<Users> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Users p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.name);
            String icon = "N";
            if (tt1 != null) {
                tt1.setText(p.displayName);
                icon = p.displayName.substring(0,1);
            }

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(icon, colorGenerator.getRandomColor());

            ImageView image = (ImageView) v.findViewById(R.id.avata);
            image.setImageDrawable(drawable);
        }

        return v;
    }
}
