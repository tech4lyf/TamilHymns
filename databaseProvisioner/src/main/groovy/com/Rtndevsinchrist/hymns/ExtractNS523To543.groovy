package com.Rtndevsinchrist.hymns

import com.lemuelinchrist.hymns.lib.Constants
import com.lemuelinchrist.hymns.lib.Dao
import com.lemuelinchrist.hymns.lib.HymnalNetExtractor
import com.lemuelinchrist.hymns.lib.beans.HymnsEntity

/**
 * Created by lemuelcantos on 6/7/16.
 */
class ExtractNS523To543 {
    public static void  main(arg) {
        println 'hello'
        Dao dao = new Dao();

        for (int x = 523; x<=543; x++) {
            HymnsEntity hymn = HymnalNetExtractor.convertWebPageToHymn(Constants.HYMNAL_NET_NEWSONGS, ""+x, 'NS', ""+x);
            dao.save(hymn);
        }


    }
}
