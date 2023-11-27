package org.gross;

import org.gross.bibleperday.BiblePerDayService;
import org.gross.bibleperday.dto.BiblePerDayDTO;
import org.gross.utils.FileUtils;
import org.gross.utils.JsonUtils;

import java.util.Collections;
import java.util.List;

public class Main {

    public static final int YEAR = 2023;
    public static final String FILE_NAME = "BPD_" + YEAR + ".json";

    public static void main(String...args) {
        BiblePerDayService biblePerDayService = new BiblePerDayService();
        List<BiblePerDayDTO> biblePerDayList = biblePerDayService.downloadContentForYear(YEAR);

        String jsonString = JsonUtils.parse(biblePerDayList);
        FileUtils.writeToFile(Collections.singletonList(jsonString), FILE_NAME);
    }

}