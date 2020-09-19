package com.Rtndevsinchrist.android.Tamilhymns.search.fragments;

import android.text.InputType;
import android.util.Log;

import com.Rtndevsinchrist.android.Tamilhymns.dao.HymnsDao;
import com.Rtndevsinchrist.android.Tamilhymns.search.searchadapters.HymnNumberAdapter;
import com.Rtndevsinchrist.android.Tamilhymns.R;
import com.Rtndevsinchrist.android.Tamilhymns.search.TabFragment;


public class HymnNumberTabFragment extends TabFragment {

    public void setSearchFilter(String filter) {

        // if user didn't input anything in the search, the default behavior is to list only a certain group of hymns
        if (selectedHymnGroup != null && filter.equals("")) {
            Log.d(this.getClass().getName(), "selectedHymnGroup=" + selectedHymnGroup);
            mRecyclerView.setAdapter(new HymnNumberAdapter(container.getContext(),
                    dao.getByFirstLineOrderBy(selectedHymnGroup,null, HymnsDao.ORDER_BY_HYMN_NUMBER), R.layout.recyclerview_hymn_list));

        } else {
            mRecyclerView.setAdapter(new HymnNumberAdapter(container.getContext(),
                    dao.getByHymnNo(selectedHymnGroup, filter)
                    , R.layout.recyclerview_hymn_list));
        }


    }

    @Override
    public boolean canBeSearched() {
        return true;
    }

    @Override
    public int getSearchTabIndex() {
        return 0;
    }

    @Override
    public String getTabName() {
        return "  Hymn Numbers";
    }

    @Override
    public int getInputType() {
        return InputType.TYPE_CLASS_PHONE;
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_dialpad_tab;
//        return android.R.drawable.ic_dialog_dialer;
    }
}
