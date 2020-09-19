package com.Rtndevsinchrist.android.Tamilhymns.search.fragments;

import com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters.HistoryAdapter;
import com.Rtndevsinchrist.android.Tamilhymns.R;
import com.Rtndevsinchrist.android.Tamilhymns.search.TabFragment;

/**
 * Created by rtdevs on 29/1/15.
 */
public class HistoryTabFragment extends TabFragment {

    @Override
    public int getSearchTabIndex() {
        return 6;
    }

    @Override
    public String getTabName() {
        return "History";
    }

    @Override
    public void setSearchFilter(String filter) {
        mRecyclerView.setAdapter(new HistoryAdapter(container.getContext(),R.layout.recyclerview_hymn_list));
    }

    @Override
    public boolean canBeSearched() {
        return false;
    }

    @Override
    public int getIcon() {
        return android.R.drawable.ic_menu_recent_history;
    }

}
