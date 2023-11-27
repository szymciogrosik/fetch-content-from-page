package org.gross.utils;

import org.apache.commons.lang3.StringUtils;

public class PrintUtils {

    private static final String SIGN = "-";
    private static final int DEFAULT_SIGNS_PER_LINE = 100;

    private PrintUtils() {
    }

    public static void printMessageWithAutoFill(String message) {
        printLineWithAutoFill(StringUtils.EMPTY);
        printLineWithAutoFill(message);
        printLineWithAutoFill(StringUtils.EMPTY);
    }

    private static void printLineWithAutoFill(String message) {
        boolean messageBlank = StringUtils.isBlank(message);
        int messageLengthWithTwoBlankSigns = messageBlank ? 0 : (message.length() + 2);
        int singsPerLine = messageLengthWithTwoBlankSigns < DEFAULT_SIGNS_PER_LINE ? DEFAULT_SIGNS_PER_LINE : messageLengthWithTwoBlankSigns + 2;
        int restLineSize = singsPerLine - messageLengthWithTwoBlankSigns;
        int restLineLeftSize = (int) Math.floor(((double)restLineSize)/2);
        int restLineRightSize = restLineSize - restLineLeftSize;

        StringBuilder line = new StringBuilder();

        line.append(SIGN.repeat(Math.max(0, restLineLeftSize)));
        if (!messageBlank) {
            line.append(" ").append(message).append(" ");
        }
        line.append(SIGN.repeat(Math.max(0, restLineRightSize)));

        System.out.println(line);
    }

}
