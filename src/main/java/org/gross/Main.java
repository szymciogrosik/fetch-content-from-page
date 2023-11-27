package org.gross;

import org.gross.bibleperday.BiblePerDayService;
import org.gross.bibleperday.dto.BiblePerDayDTO;
import org.gross.utils.FileUtils;
import org.gross.utils.JsonUtils;

import java.util.Collections;
import java.util.List;

public class Main {

    public static final String FILE_NAME = "BPD_2023.json";

    public static void main(String...args) {
        BiblePerDayService biblePerDayService = new BiblePerDayService();
        List<BiblePerDayDTO> biblePerDayList = biblePerDayService.downloadContentForYear(2023);

        String jsonString = JsonUtils.parse(biblePerDayList);
        FileUtils.writeToFile(Collections.singletonList(jsonString), "FILE_NAME");
    }

}