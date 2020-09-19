package com.Rtndevsinchrist.android.Tamilhymns.search.fragments;

import com.Rtndevsinchrist.android.Tamilhymns.search.TabFragment;
import com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters.CategoryAdapter;
import com.Rtndevsinchrist.android.Tamilhymns.R;
import com.Rtndevsinchrist.android.Tamilhymns.search.HymnCursorAdapter;

/**
 * Created by rtdevs on 1/11/15.
 */
public class CategoryTabFragment extends TabFragment {

    @Override
    public int getSearchTabIndex() {
        return 3;
    }

    @Override
    public String getTabName() {
        return "Subjects";
    }

    @Override
    public boolean canBeSearched() {
        return true;
    }

    @Override
    public void setSearchFilter(String filter) {
        if(filter!=null && filter.isEmpty()) {
            mRecyclerView.setAdapter(new CategoryAdapter(container.getContext(),
                    dao.getByCategory(selectedHymnGroup, ""), R.layout.recyclerview_hymn_list));

        } else {
            ((HymnCursorAdapter) mRecyclerView.getAdapter()).setNewCursor(dao.getByCategory(selectedHymnGroup, filter));
        }
    }

    @Override
    public int getIcon() {
        return android.R.drawable.ic_menu_directions;
    }
}
