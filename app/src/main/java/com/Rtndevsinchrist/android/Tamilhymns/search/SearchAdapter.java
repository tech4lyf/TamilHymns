package com.Rtndevsinchrist.android.Tamilhymns.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by rtdevs on 31/10/15.
 */
public abstract class SearchAdapter extends RecyclerView.Adapter<IndexViewHolder> {

    protected final Context context;
    protected final int layout;

    public SearchAdapter(Context context, int layout) {
        this.context=context;
        this.layout=layout;

    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout, viewGroup, false);


        return new IndexViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final IndexViewHolder holder, int position) {

        provisionHolder(holder,position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, ""+holder.hymnNo, Toast.LENGTH_SHORT).show();
                ((SearchActivity)context).createIntentAndExit(holder.hymnNo);

            }
        });


    }

    protected abstract void provisionHolder(IndexViewHolder holder, int position);


}
