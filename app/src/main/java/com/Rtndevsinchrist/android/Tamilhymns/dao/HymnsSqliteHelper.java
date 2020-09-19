package com.Rtndevsinchrist.android.Tamilhymns.dao;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rtdevs on 24/7/13.
 */
public class HymnsSqliteHelper extends SQLiteOpenHelper {


    private static int version = 0;
    private static final String DATABASE_NAME = "hymns.sqlite";
    private static File DATABASE_FILE;

    private boolean mInvalidDatabaseFile = false;
    private boolean mIsUpgraded = false;
    private Context mContext;
    /**
     * number of users of the database connection.
     */
    private int mOpenConnections = 0;
    private static HymnsSqliteHelper mInstance;

    synchronized static public HymnsSqliteHelper getInstance(Context context) {

        try {
            version=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            Log.i(HymnsSqliteHelper.class.getName(),"version code of this app is: "+version+". using this version for SQLite.");

        } catch (PackageManager.NameNotFoundException e) {

            Log.e(HymnsSqliteHelper.class.getName(),"could not get the version number of this app!! " +
                    "This is a serious error!! \n"+e.getStackTrace());

        }

        if (mInstance == null) {
            mInstance = new HymnsSqliteHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private HymnsSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
        this.mContext = context;
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            if (db != null) {
                db.close();
            }
            DATABASE_FILE = context.getDatabasePath(DATABASE_NAME);
            if (mInvalidDatabaseFile) {
                copyDatabase();
            }
            if (mIsUpgraded) {
                doUpgrade();
            }
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mInvalidDatabaseFile = true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int old_version, int new_version) {
        mInvalidDatabaseFile = true;
        mIsUpgraded = true;
    }


    /**
     * called if a database upgrade is needed
     */
    private void doUpgrade() {


    }

    @Override
    public synchronized void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenConnections++;
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    /**
     * implementation to avoid closing the database connection while it is in
     * use by others.
     */
    @Override
    public synchronized void close() {
        mOpenConnections--;
        if (mOpenConnections == 0) {
            super.close();
        }
    }

    private void copyDatabase() {
        AssetManager assetManager = mContext.getResources().getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(DATABASE_NAME);
            out = new FileOutputStream(DATABASE_FILE);
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
        setDatabaseVersion();
        mInvalidDatabaseFile = false;
    }

    private void setDatabaseVersion() {
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(DATABASE_FILE.getAbsolutePath(), null,
                    SQLiteDatabase.OPEN_READWRITE);
            db.execSQL("PRAGMA user_version = " + version);
        } catch (SQLiteException e) {
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

}
