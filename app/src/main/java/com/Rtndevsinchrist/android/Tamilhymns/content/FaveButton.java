package com.Rtndevsinchrist.android.Tamilhymns.content;

import android.view.View;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

import com.Rtndevsinchrist.android.Tamilhymns.entities.Hymn;
import com.Rtndevsinchrist.android.Tamilhymns.R;
import com.Rtndevsinchrist.android.Tamilhymns.logbook.LogBook;

/**
 * @author Lemuel Cantos
 * @since 22/2/2020
 */
public class FaveButton extends ContentComponent<ImageButton> {
    public static final String FAVE_LOG_BOOK_FILE = "faveLogBook";
    private boolean faved=false;
    private LogBook faveLogBook;

    public FaveButton(final Hymn hymn, Fragment parentFragment, ImageButton imageButton) {
        super(hymn, parentFragment, imageButton);
        this.faveLogBook = new LogBook(context, FAVE_LOG_BOOK_FILE);

        if(faveLogBook.contains(hymn.getHymnId())) {
            faved=true;
            imageButton.setImageResource(R.drawable.ic_favorite_white_48dp);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(faved){
                    unfave();
                } else {
                    fave();
                }
                faved =!faved;
            }
        });
    }

    private void fave() {
        view.setImageResource(R.drawable.ic_favorite_white_48dp);
        faveLogBook.log(hymn);
    }

    private void unfave() {
        view.setImageResource(R.drawable.ic_favorite_border_white_48dp);
        faveLogBook.removeAndSave(hymn);
    }
}
