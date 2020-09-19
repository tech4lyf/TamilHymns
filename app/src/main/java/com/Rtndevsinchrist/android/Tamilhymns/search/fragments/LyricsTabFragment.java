package com.Rtndevsinchrist.android.Tamilhymns.search.fragments;

import com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters.LyricsAdapter;
import com.Rtndevsinchrist.android.Tamilhymns.R;
import com.Rtndevsinchrist.android.Tamilhymns.search.TabFragment;

/**
 * @author Lemuel Cantos
 * @since 13/3/2018
 */
public class LyricsTabFragment extends TabFragment {

    @Override
    public int getSearchTabIndex() {
        return 5;
    }

    @Override
    public String getTabName() {
        return "All Lyrics";
    }


    @Override
    public boolean canBeSearched() {
        return true;
    }

    @Override
    public void setSearchFilter(String filter) {
        mRecyclerView.setAdapter(new LyricsAdapter(container.getContext(),
                dao.getByLyricText(selectedHymnGroup, filter)
                , R.layout.recyclerview_hymn_list));    }

    @Override
    public int getIcon() {
        return R.drawable.ic_receipt_grey;
    }
}
