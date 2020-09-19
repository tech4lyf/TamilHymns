package com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters;

import android.content.Context;

import com.Rtndevsinchrist.android.Tamilhymns.content.FaveButton;
import com.Rtndevsinchrist.android.Tamilhymns.logbook.HymnRecord;
import com.Rtndevsinchrist.android.Tamilhymns.logbook.LogBook;
import com.Rtndevsinchrist.android.Tamilhymns.search.IndexViewHolder;
import com.Rtndevsinchrist.android.Tamilhymns.search.SearchAdapter;

/**
 * Created by rtdevs on 27/1/15.
 */
public class FavoritesAdapter extends SearchAdapter {

    private final HymnRecord[] faveLogBookList;

    public FavoritesAdapter(Context context, int layout) {
        super(context, layout);
        faveLogBookList = new LogBook(context, FaveButton.FAVE_LOG_BOOK_FILE).getOrderedRecordList();

    }

    @Override
    public void provisionHolder(final IndexViewHolder holder, int position) {
        HymnRecord record = faveLogBookList[position];
        holder.list_item.setText(record.getHymnId() + " - " + record.getFirstLine());
        holder.imageView.setImageResource(context.getResources().getIdentifier(record.getHymnGroup().toLowerCase(), "drawable", context.getPackageName()));
        holder.hymnNo = record.getHymnId();
    }

    @Override
    public int getItemCount() {
        return faveLogBookList.length;
    }
}
