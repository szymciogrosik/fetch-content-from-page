package org.gross.bibleperday;

import org.gross.bibleperday.dto.BiblePerDayDTO;
import org.gross.bibleperday.dto.serializer.BpdSerializer;
import org.gross.utils.FileUtils;

import java.io.File;
import java.util.List;

public class ValidateContent {

    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 1;
    private static final int YEAR = 2023;

    private static final String PATH_PREFIX = "bibleperday/" + YEAR + "/";
    private static final BpdSerializer SERIALIZER = new BpdSerializer();

    public static void main(String[] args) {
        for (int month = FIRST_MONTH; month <= LAST_MONTH; month++) {
            File file = FileUtils.getFile(PATH_PREFIX + BiblePerDayDownloadService.getFileName(month, YEAR));
            List<BiblePerDayDTO> biblePerDayList = SERIALIZER.defaultDeserialize(file);
            List<String> allBibleReferences = biblePerDayList.stream().map(BiblePerDayDTO::getBibleReferences).flatMap(List::stream).toList();

            ValidateBibleReferenceService validateService = new ValidateBibleReferenceService();
            List<String> invalidReferences = validateService.getInvalidBibleReferences(allBibleReferences);

            FileUtils.writeToFile(invalidReferences, getInvalidReferencesFileName(month, YEAR));
        }
    }

    private static String getInvalidReferencesFileName(int month, int year) {
        return "Invalidate_ref_" + month + "_" + year + ".txt";
    }

}
