package com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;

import com.Rtndevsinchrist.android.Tamilhymns.HymnGroup;
import com.Rtndevsinchrist.android.Tamilhymns.NoSuchHymnGroupException;
import com.Rtndevsinchrist.android.Tamilhymns.search.HymnCursorAdapter;
import com.Rtndevsinchrist.android.Tamilhymns.search.IndexViewHolder;

/**
 * Created by rtdevs on 14/03/18.
 */
public class LyricsAdapter extends HymnCursorAdapter {

    public LyricsAdapter(Context context, Cursor cursor, int layout) {
        super(context, cursor, layout);
    }

    @Override
    protected void provisionHolderUsingCursor(IndexViewHolder indexViewHolder) {
        String parentHymn = cursor.getString(cursor.getColumnIndex("parent_hymn"));
        String no = cursor.getString(cursor.getColumnIndex("no"));
        String text = cursor.getString(cursor.getColumnIndex("text"));

        StringBuilder lyricText = new StringBuilder();
        lyricText.append("<b>");
        lyricText.append(parentHymn);
        lyricText.append(" - ");
        lyricText.append(no);
        lyricText.append("</b><br/>");
        lyricText.append(text.trim());

        //removeAndSave trailing <br/>
        lyricText.reverse().delete(0,5).reverse();

        indexViewHolder.list_item.setText(Html.fromHtml(lyricText.toString()));

        String hymnGroup = null;
        try {
            hymnGroup = HymnGroup.getHymnGroupFromID(parentHymn).toString();
            indexViewHolder.imageView.setImageResource(context.getResources().getIdentifier(hymnGroup.toLowerCase(), "drawable", context.getPackageName()));

            indexViewHolder.hymnNo = parentHymn;
        } catch (NoSuchHymnGroupException e) {
            e.printStackTrace();
        }


    }
}
