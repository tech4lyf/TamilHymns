package com.Rtndevsinchrist.hymns;

import com.Rtndevsinchrist.hymns.lib.Dao;
import com.Rtndevsinchrist.hymns.lib.beans.HymnsEntity;
import com.Rtndevsinchrist.hymns.lib.beans.StanzaEntity;
import groovy.util.logging.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Lemuel Cantos
 * @since 15/6/2019
 */
@Slf4j
public class ProvisionJapanese {
    File japaneseFile;
    File japaneseSupplementFile;
    private int x = 0;
    private String line;
    private List<String> lines;
    private static StringBuilder logString = new StringBuilder();
    private StringBuilder errors = new StringBuilder();

    public static void main(String[] args) throws Exception {
        ProvisionJapanese japanese = new ProvisionJapanese();
        try {
            japanese.provision();
        } finally {
            log(logString.toString());
        }
    }

    public void provision() throws Exception {
        Dao dao = new Dao();
        japaneseFile = new File(this.getClass().getResource("/japanese.txt").getPath());
        japaneseSupplementFile = new File(this.getClass().getResource("/japaneseSupplement.txt").getPath());

        lines = Files.lines(japaneseFile.toPath()).collect(Collectors.toList());
        Integer hymnNumber = 0;
        Integer stanzaCounter = 0;
        Integer stanzaOrderCounter = 0;
        HymnsEntity hymn = null;
        StringBuilder stanzaBuilder = null;

        for (x = 0; hymnNumber != 780; x++) {
            line = lines.get(x).trim();

            while ((x + 1) != lines.size() &&
                    lines.get(++x).trim().isEmpty()) {

            }

            String relatedLine = lines.get(x + 1).trim();
            logString.append(++hymnNumber + " ");
            System.out.println("processing Hymn#" + hymnNumber);
            logString.append(relatedLine);
            logString.append("\n");

            Integer englishRelated = null;
            if (relatedLine.contains("英")) {
                englishRelated = Integer.parseInt(relatedLine
                        .substring(relatedLine.indexOf("英") + 1).replaceAll("[^\\d]", ""));
            }

            String categoryLine = lines.get(x).trim();
            assert categoryLine.contains("―");

            hymn = new HymnsEntity();
            hymn.setId("J" + hymnNumber);
            hymn.setHymnGroup("J");
            hymn.setNo(hymnNumber.toString());
            hymn.setMainCategory(categoryLine.split("―")[0].trim());
            hymn.setSubCategory(categoryLine.split("―")[1].trim());
            if (englishRelated != null) {
                hymn.setParentHymn("E" + englishRelated);
            }

            x++;
            x++;
            line = convertJapCharacters(x);
            if (!line.equals("1") && !line.equals("note")) {
                throw new RuntimeException("line "
                        + "after hymn "
                        + "number "
                        + "should "
                        + "be first "
                        + "stanza but "
                        + "is: " + line);
            }

            stanzaCounter = 0;
            int order = 0;
            hymn.setStanzas(new ArrayList<>());
            while (true) {

                StanzaEntity stanza = null;
                if (!line.equals("note") && !line.equals("(復)")) {
                    stanzaCounter++;
                    System.out.println("stanza " + stanzaCounter);
                    int no = Integer.parseInt(line);
                    if (no != stanzaCounter) {
                        if (no == 1) {
                            stanzaCounter = 1;
                        } else {
                            throw new RuntimeException(line + "!= "
                                    + "stnazacounter: " + stanzaCounter);
                        }
                    }
                    stanza = createNewStanza(hymn, ++order);
                    if (hymn.getFirstStanzaLine() == null) {
                        hymn.setFirstStanzaLine(line);
                    }
                } else if (line.equals("(復)") && hymn.getFirstChorusLine() == null) {
                    stanza = createNewStanza(hymn, ++order);
                    hymn.setFirstChorusLine(line);
                } else {
                    stanza = createNewStanza(hymn, ++order);
                }
                while (true) {
                    line = lines.get(++x).trim();
                    if (line.isEmpty()) {
                        break;
                    }
                    stanza.setText(stanza.getText() + line + "<br/>");
                }

                if (isStartOfHymn()) {
                    break;
                }
                line = convertJapCharacters(++x);

            }

            logString.append(hymn.toString() + "\n");
            dao.save(hymn);
        }

        log(logString.toString());
    }

    private StanzaEntity createNewStanza(HymnsEntity hymn, int order) {
        StanzaEntity stanza = new StanzaEntity();
        hymn.getStanzas().add(stanza);
        if (line.equals("(復)")) {
            stanza.setNo("chorus");
        } else {
            stanza.setNo(line);
        }
        stanza.setOrder(order);
        stanza.setParentHymn(hymn);
        line = lines.get(++x).trim();
        stanza.setText(line + "<br/>");
        return stanza;
    }

    private String convertJapCharacters(int x) {
        return lines.get(x)
                .replace("　", "")
                .replace("１", "1")
                .replace("２", "2")
                .replace("３", "3")
                .replace("４", "4")
                .replace("５", "5")
                .replace("６", "6")
                .replace("７", "7")
                .replace("８", "8")
                .replace("９", "9")
                .replace("０", "0")
                .trim();
    }

    public boolean isStartOfHymn() {
        return line.isEmpty() &&
                (x + 1) != lines.size() &&
                lines.get(x + 1).trim().isEmpty();
    }

    public static void log(String text) {
        try {
            Files.write(Paths.get("TEST.txt"), text.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
