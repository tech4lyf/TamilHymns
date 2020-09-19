package com.Rtndevsinchrist.android.Tamilhymns.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.Rtndevsinchrist.android.Tamilhymns.R;

/**
* Created by rtdevs on 27/1/15.
*/
public class IndexViewHolder extends RecyclerView.ViewHolder {
    public TextView list_item;
    public ImageView imageView;
    public String hymnNo;

    public IndexViewHolder(View view) {
        super(view);
        list_item = (TextView) view.findViewById(R.id.hymnTitle);
        imageView = (ImageView) view.findViewById(R.id.hymnGroupImage);
    }

}
