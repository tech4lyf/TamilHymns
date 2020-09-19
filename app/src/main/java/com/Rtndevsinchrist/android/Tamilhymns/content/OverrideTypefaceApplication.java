package com.Rtndevsinchrist.android.Tamilhymns.content;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * This is subclass of Application class. This contains all the application
 * level settings.
 * <p>
 * author Radheshyam
 * date 04/December/2018
 */
public class OverrideTypefaceApplication extends Application {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();

        overrideDefaultTypefaces();
    }

    /**
     * Method used to override the default typefaces with the custom fonts
     * for the application.
     * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void overrideDefaultTypefaces() {
        FontChanger.overrideDefaultFont(this, "DEFAULT", "fonts/tamilear.ttf");
        FontChanger.overrideDefaultFont(this, "MONOSPACE", "fonts/learning_curve_bold.ttf");
        FontChanger.overrideDefaultFont(this, "Tamil", "fonts/tamilear.ttf");
        FontChanger.overrideDefaultFont(this, "SANS_SERIF", "fonts/learning_curve_regular.ttf");
    }
}