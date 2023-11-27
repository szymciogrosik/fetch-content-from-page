package org.gross.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

    private DateUtils() {
    }

    public static Date parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Date '" + dateString + "' cannot be parsed!", e);
        }
    }

    public static String present(Date date) {
        return DATE_FORMATTER.format(date);
    }

}
