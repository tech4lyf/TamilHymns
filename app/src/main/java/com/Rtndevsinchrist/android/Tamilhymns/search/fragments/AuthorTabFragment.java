package com.Rtndevsinchrist.android.Tamilhymns.search.fragments;

import com.Rtndevsinchrist.android.Tamilhymns.search.TabFragment;
import com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters.AuthorAdapter;
import com.Rtndevsinchrist.android.Tamilhymns.R;

/**
 * Created by rtdevs on 1/11/15.
 */
public class AuthorTabFragment extends TabFragment {

    @Override
    public int getSearchTabIndex() {
        return 4;
    }

    @Override
    public String getTabName() {
        return "  Authors";
    }

    @Override
    public boolean canBeSearched() {
        return true;
    }

    @Override
    public void setSearchFilter(String filter) {
        mRecyclerView.setAdapter(new AuthorAdapter(container.getContext(),
                dao.getByAuthorsOrComposers(filter)
                , R.layout.recyclerview_hymn_list));
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_account_multiple_grey600_24dp;
    }
}
