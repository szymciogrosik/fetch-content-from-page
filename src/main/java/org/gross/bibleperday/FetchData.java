package org.gross.bibleperday;

public class FetchData {

    public static final int FIRST_DAY_OF_THE_MONTH = 1;
    public static final int LAST_DAY_OF_THE_MONTH = 0; // 0 means that the end day of the month
    public static final int FIRST_MONTH = 1;
    public static final int LAST_MONTH = 12;
    public static final int YEAR = 2023;

    public static void main(String...args) {
        BiblePerDayDataService biblePerDayDataService = new BiblePerDayDataService();
        biblePerDayDataService.downloadContentForDates(
                YEAR, FIRST_MONTH, LAST_MONTH, FIRST_DAY_OF_THE_MONTH, LAST_DAY_OF_THE_MONTH
        );
    }

}