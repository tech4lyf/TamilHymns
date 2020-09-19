package com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;

import com.Rtndevsinchrist.android.Tamilhymns.dao.HymnsDao;
import com.Rtndevsinchrist.android.Tamilhymns.search.HymnCursorAdapter;
import com.Rtndevsinchrist.android.Tamilhymns.search.IndexViewHolder;

/**
 * Created by rtdevs on 31/10/15.
 */
public class AuthorAdapter extends HymnCursorAdapter {

    public AuthorAdapter(Context context, Cursor cursor, int layout) {
        super(context, cursor, layout);
    }

    @Override
    protected void provisionHolderUsingCursor(IndexViewHolder indexViewHolder) {

        String first_line = cursor.getString(cursor.getColumnIndex("first_stanza_line"));
        if(first_line==null || first_line.isEmpty()) {
            first_line = cursor.getString(cursor.getColumnIndex("first_chorus_line"));
        }

        String author_composer = cursor.getString(cursor.getColumnIndex("author_composer"));
        if (author_composer.trim().equals("*")) {
            author_composer="* LSM";
        }
        if (author_composer.trim().equals("+")) {
            author_composer="+ Translated";
        }
        String categoryText = "<b>" + author_composer + "</b>"
                + "<br/>" + cursor.getString(cursor.getColumnIndex("_id")) + " - " + first_line;
        indexViewHolder.list_item.setText(Html.fromHtml(categoryText));

        String hymnGroup = cursor.getString(cursor.getColumnIndex(HymnsDao.HymnFields.hymn_group.toString()));
        indexViewHolder.imageView.setImageResource(context.getResources().getIdentifier(hymnGroup.toLowerCase(), "drawable", context.getPackageName()));

        indexViewHolder.hymnNo = cursor.getString(cursor.getColumnIndex("_id"));

    }
}
