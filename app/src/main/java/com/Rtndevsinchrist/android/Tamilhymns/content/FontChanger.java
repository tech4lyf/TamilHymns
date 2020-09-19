package com.Rtndevsinchrist.android.Tamilhymns.content;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;

/**
 * Class used to replace default typeface with custom typeface
 * <p>
 * author Radheshyam
 * date 04/December/2018
 */
public class FontChanger {

    /**
     * Method used to override the default fonts with custom fonts.
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void overrideDefaultFont(Context context,
                                           String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    /**
     * This method uses reflection to access the typeface information
     * and then override the same.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void replaceFont(String staticTypefaceFieldName,
                                    final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}