package com.Rtndevsinchrist.android.Tamilhymns;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.Rtndevsinchrist.android.Tamilhymns.entities.Hymn;

public class HymnSwitcher extends Activity {

    public void switchHymn(String hymnid)
    {
        Intent intent = new Intent(getBaseContext(), HymnsActivity.class);
        intent.setData(Uri.parse(hymnid));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent,-1);

    }
}
