package org.gross;

import org.gross.bibleperday.BiblePerDayService;
import org.gross.bibleperday.dto.BiblePerDayDTO;
import org.gross.utils.DateUtils;
import org.gross.utils.FileUtils;
import org.gross.utils.JsonUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Main {

    public static final int FIRST_DAY_OF_THE_MONTH = 1;
    public static final int LAST_DAY_OF_THE_MONTH = 0; // 0 means that the end day of the month
    public static final int FIRST_MONTH = 1;
    public static final int LAST_MONTH = 12;
    public static final int YEAR = 2023;

    public static void main(String...args) {
        for (int month = FIRST_MONTH; month <= LAST_MONTH; month++) {
            BiblePerDayService biblePerDayService = new BiblePerDayService();
            List<BiblePerDayDTO> biblePerDayList = biblePerDayService.downloadContentForYear(getStartDate(month), getEndDate(month));

            String jsonString = JsonUtils.parse(biblePerDayList);
            FileUtils.writeToFile(Collections.singletonList(jsonString), getFileName(month));
        }
    }

    private static Date getStartDate(int month) {
        return getDate(FIRST_DAY_OF_THE_MONTH, month);
    }

    private static Date getEndDate(int month) {
        if (LAST_DAY_OF_THE_MONTH != 0) {
            return getDate(LAST_DAY_OF_THE_MONTH, month);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStartDate(month));
        int lastDayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);;
        return getDate(lastDayInMonth, month);
    }

    private static Date getDate(int day, int month) {
        return DateUtils.parse(day + "." + month + "." + YEAR);
    }

    private static String getFileName(int month) {
        return "BPD_" + month + "_" + YEAR + ".json";
    }

}