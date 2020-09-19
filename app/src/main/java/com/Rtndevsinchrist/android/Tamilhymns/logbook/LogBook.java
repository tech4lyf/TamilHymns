package com.Rtndevsinchrist.android.Tamilhymns.logbook;

import android.content.Context;
import android.util.Log;

import com.Rtndevsinchrist.android.Tamilhymns.entities.Hymn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rtdevs on 7/12/14.
 */
public class LogBook {

    private final Context context;
    private final String filename;
    private Set<HymnRecord> logBook = new HashSet<HymnRecord>();


    public LogBook(Context context, String filename) {
        this.context = context;
        this.filename = filename;

        // get logbook from saved file
        try {
            logBook = (Set<HymnRecord>)InternalStorage.readObject(this.context, filename);
        } catch (FileNotFoundException e) {
            Log.i(LogBook.class.getName(),"No logbook file found. Must be first time use. Creating one.");
            logBook = new HashSet<>();
        } catch(InvalidClassException e) {
            Log.e(LogBook.class.getName(),"Incompatible class! creating new record \n " + e);
            logBook = new HashSet<>();
        } catch (Exception e) {
            Log.e(LogBook.class.getName(),"Error reading history log book! \n " + e);
            logBook = new HashSet<>();
        }
    }

    public void log(Hymn hymn) {

        // Remove existing record in the log if any (exiting record means record with the same hymnId)
        HymnRecord record = remove(hymn);

        // Add the new record. if there was an existing one, it is removed in the previous line and replace with one having the current time.
        logBook.add(record);
        Log.d(this.getClass().getName(),"hymn logged: " +hymn.getHymnId());

        // persist logBook
        try {
            InternalStorage.writeObject(context,filename,logBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HymnRecord[] getOrderedRecordList() {
        HymnRecord[] hymnRecords = logBook.toArray(new HymnRecord[0]);
        Arrays.sort(hymnRecords);

        if (hymnRecords.length>101) {
            return Arrays.copyOfRange(hymnRecords, 0, 100);
        } else {
            return hymnRecords;
        }
    }

    public String exportHymnList() {
        StringBuilder list = new StringBuilder();
        for(HymnRecord record: logBook) {
            list.append(record.getHymnId());
            list.append(",");
        }
        return list.toString();
    }

    public boolean contains(String hymnId) {
        HymnRecord record = new HymnRecord(hymnId,null,null,null);
        return logBook.contains(record);
    }

    public void removeAndSave(Hymn hymn) {
        HymnRecord record = remove(hymn);
        // persist logBook
        try {
            InternalStorage.writeObject(context,filename,logBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(this.getClass().getName(),"hymn removed: " +hymn.getHymnId());
    }

    private HymnRecord remove(Hymn hymn) {
        // *** new Date() initializes a Date object with the current time.
        String firstLine;

        // some hymns do not have stanzas, just chorus, so we need this code
        if (hymn.getFirstStanzaLine()==null || hymn.getFirstStanzaLine().isEmpty()) {
            firstLine = hymn.getFirstChorusLine();
        } else {
            firstLine = hymn.getFirstStanzaLine();
        }

        HymnRecord record = new HymnRecord(hymn.getHymnId(), hymn.getGroup(), firstLine, new Date());

        // Remove existing record in the log if any (exiting record means record with the same hymnId)
        logBook.remove(record);

        return record;
    }
}
