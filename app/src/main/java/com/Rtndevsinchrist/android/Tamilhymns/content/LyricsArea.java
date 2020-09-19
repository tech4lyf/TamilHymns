package com.Rtndevsinchrist.android.Tamilhymns.content;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.Rtndevsinchrist.android.Tamilhymns.HymnGroup;
import com.Rtndevsinchrist.android.Tamilhymns.HymnsActivity;
import com.Rtndevsinchrist.android.Tamilhymns.R;
import com.Rtndevsinchrist.android.Tamilhymns.entities.Hymn;
import com.Rtndevsinchrist.android.Tamilhymns.entities.Stanza;
import com.Rtndevsinchrist.android.Tamilhymns.search.SearchActivity;
import com.Rtndevsinchrist.android.Tamilhymns.style.HymnTextFormatter;
import com.Rtndevsinchrist.android.Tamilhymns.style.Theme;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Lemuel Cantos
 * @since 22/2/2020
 */
public class LyricsArea extends ContentComponent<NestedScrollView> {
    private static float fontSize;
    private final TextView subjectHeader;
    private final ViewGroup stanzaView;
    private final TextView composerView;
    private final TextView lyricHeader;
    private final SharedPreferences sharedPreferences;
    private final Theme theme;
    SearchActivity searchActivity = new SearchActivity();
    TextView tvSimilar, tvTime, tvKey, tvRelated;
    String rel;
    private int columnNo = 0;
    private LinearLayout currentTextLinearLayout;


    public LyricsArea(Hymn hymn, Fragment parentFragment, NestedScrollView view) {
        super(hymn, parentFragment, view);

        // Load saved data
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        fontSize = Float.parseFloat(sharedPreferences.getString("FontSize", "18f"));
        theme = Theme.isNightModePreferred(sharedPreferences.getBoolean("nightMode", false));

        // remove placeholder because it only contains dummy lyrics
        stanzaView = view.findViewById(getRid("stanzaView"));
        stanzaView.removeView(stanzaView.getChildAt(0));

        subjectHeader = view.findViewById(getRid("subjectHeader"));
        subjectHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        lyricHeader = view.findViewById(getRid("lyricHeader"));
        lyricHeader.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        composerView = view.findViewById(getRid("composer"));
        composerView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);


        tvSimilar = view.findViewById(R.id.tvSimilar);
        tvKey = view.findViewById(R.id.tvKey);
        tvTime = view.findViewById(R.id.tvTime);
        tvRelated = view.findViewById(R.id.tvRelated);

    }

    private int getRid(String lyricHeader) {
        return context.getResources().getIdentifier(lyricHeader, "id", context
                .getPackageName());
    }

    public void displayLyrics() {
        try {
            Log.d(this.getClass().getSimpleName(), "Displaying lyrics");

            //if hymn is still null, it means the user entered a hymn number that doesn't exist
            if (hymn == null) return;

            // ########################### Build Header
            boolean headerContentPresent = false;
            StringBuilder text = new StringBuilder();
            if (isNotEmpty(hymn.getMainCategory())) {
                headerContentPresent = true;
                text.append("<b>" + hymn.getMainCategory() + "</b>");
            }
            if (isNotEmpty(hymn.getSubCategory())) {
                headerContentPresent = true;
                text.append("<br/>" + hymn.getSubCategory());
            }
            if (hymn.isNewTune()) text.append("<br/>(New Tune)");
            subjectHeader.setText(Html.fromHtml(text.toString()));

            text = new StringBuilder();
            // **** tune header
            if (isNotEmpty(hymn.getMeter())) {
                headerContentPresent = true;
                text.append("<br/>Similar Tune: ");
                text.append(hymn.getMeter());
            }
            if (isNotEmpty(hymn.getTime())) {
                headerContentPresent = true;
                text.append("<br/>Time: ");
                text.append(hymn.getTime());
            }
            if (isNotEmpty(hymn.getKey())) {
                headerContentPresent = true;
                text.append("<br/>Key: " + hymn.getKey());
            }
            if (isNotEmpty(hymn.getVerse())) {
                headerContentPresent = true;
                text.append("<br/>Verses: ");
                text.append(hymn.getVerse());
            }


            // ** build related
            List<String> related = hymn.getRelated();
            if (related != null && related.size() != 0) {
                headerContentPresent = true;
                text.append("<br/>Related: ");
                StringBuilder relatedConcat = new StringBuilder();
                for (String r : related) {
                    relatedConcat.append(", ");
                    relatedConcat.append(r);
                }

                if (relatedConcat.length() > 2) {
                    text.append(relatedConcat.substring(2));
                    rel = relatedConcat.substring(2);
                }
            }

            if (!headerContentPresent) {
                View mainHeaderContainer = view.findViewById(getRid("mainHeaderContainer"));
                mainHeaderContainer.setVisibility(View.GONE);
            } else {
                // Use text.substring to remove the leading <br/>
                String header;
                if (text.length() > 5) {
                    header = text.substring(5);
                    Log.e("test", text.toString());

                    if (hymn.getMeter() != null && hymn.getMeter() != "null") {

                        tvSimilar.setText("Similar Tune: " + hymn.getMeter());
                        tvSimilar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "" + hymn.getMeter(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(view.getContext(), HymnsActivity.class);
                                intent.putExtra("hymnid", hymn.getMeter());
                                view.getContext().startActivity(intent);

                            }
                        });
                    } else {

                        tvSimilar.setTextColor(Color.parseColor("#686F74"));
                        tvSimilar.setText("Similar Tune: N/A");
                    }
                    tvTime.setText("Time: " + hymn.getTime());
                    tvKey.setText("Key: " + hymn.getKey());
                    tvRelated.setText("Related: " + rel);


                } else {
                    header = "";
                }
                lyricHeader.setText(Html.fromHtml(header + "test"));
            }

            // ######################## Build Lyric Text

            String chorusText = "";
            text = new StringBuilder();
            ArrayList<Stanza> stanzas = hymn.getStanzas();
            if (stanzas.get(0).getNo().equals("beginning-note")) {
                text.append("<i>" + stanzas.get(0).getText() + "</i><br/>");
                stanzas.remove(stanzas.get(0));
            }
            for (Stanza stanza : stanzas) {
                Log.d(this.getClass().getSimpleName(), "Looping stanza: " + stanza.getNo());
                if (stanza.getNo().equals("chorus")) {
//                    text.append("<b>##" + stanza.getNo() + "##</b><br/>");
                    if (isNotEmpty(stanza.getNote()))
                        text.append("<i>@@(" + stanza.getNote() + ")@@</i>");
                    chorusText = "<i>@@" + stanza.getText() + "@@</i>";
                    text.append(chorusText);
                    buildLyricViewAndAttach(text, hymn.getGroup());
                } else if (stanza.getNo().equals("end-note") || stanza.getNo().equals("note")) {
                    text.append("<i>" + stanza.getText() + "</i>");
                    buildLyricViewAndAttach(text, hymn.getGroup());
                } else {
                    // append stanza
                    text.append("<b>##" + stanza.getNo() + "##</b><br/>");
                    text.append(stanza.getText());
                    buildLyricViewAndAttach(text, hymn.getGroup());

                    // append chorus after every stanza
                    if (hymn.getChorusCount() == 1 && !chorusText.isEmpty()) {
                        buildLyricViewAndAttach(new StringBuilder(chorusText), hymn.getGroup());
                    }
                }
                text = new StringBuilder();
            }

            // remove unused textview if uneven
            // if column is odd
            if (columnNo % 2 != 0) {
                currentTextLinearLayout.removeViewAt(1);
            }

            // #################### Build Footer
            text = new StringBuilder();
            if (isNotEmpty(hymn.getAuthor()) || isNotEmpty(hymn.getComposer())) {
                text.append("Author: " + hymn.getAuthor() + "<br/>");
                text.append("Composer: " + hymn.getComposer());
                composerView.setText(Html.fromHtml(text.toString()));
            } else {
                ((ViewGroup) composerView.getParent()).removeView(composerView);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(), "Exception Thrown!!");
            Log.e(this.getClass().getSimpleName(), e.toString());
        }

    }

    private void buildLyricViewAndAttach(StringBuilder text, String selectedHymnGroup) {
        Log.i(this.getClass().getSimpleName(), text.toString());

        // add colors to text
        CharSequence formattedLyrics = HymnTextFormatter.format(Html.fromHtml(text.toString()), theme.getTextColor(HymnGroup.valueOf(selectedHymnGroup)));

        TextView view;
        // if column is odd
        if (++columnNo % 2 != 0) {
            currentTextLinearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.stanza_linear_layout, null);
            stanzaView.addView(currentTextLinearLayout);
            // left column on landscape mode
            view = (TextView) currentTextLinearLayout.getChildAt(0);
        } else {
            // right column on landscape mode
            view = (TextView) currentTextLinearLayout.getChildAt(1);
        }
        view.setText(formattedLyrics);

        // stylize...
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        view.setTextColor(theme.getTextColor());
        view.setBackgroundColor(theme.getTextBackgroundColor());
    }

    private boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }


}
