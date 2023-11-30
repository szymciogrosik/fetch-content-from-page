package org.gross.bibleperday;

import org.apache.commons.lang3.StringUtils;
import org.gross.bibleperday.dto.BiblePerDayContainer;
import org.gross.bibleperday.dto.BiblePerDayDTO;
import org.gross.utils.FileUtils;
import org.gross.utils.JsonUtils;

import java.io.File;
import java.util.List;

public class ValidateContent {

    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 12;
    private static final int YEAR = 2023;

    private static final String PATH_PREFIX = "bibleperday/" + YEAR + "/";

    public static void main(String[] args) {
        for (int month = FIRST_MONTH; month <= LAST_MONTH; month++) {
            File file = FileUtils.getFile(PATH_PREFIX + BiblePerDayDownloadService.getFileName(month, YEAR));
            BiblePerDayContainer biblePerDayList = JsonUtils.deserialize(file, BiblePerDayContainer.class);
            List<String> allBibleReferences =
                    biblePerDayList.getBiblePerDayList().stream()
                                   .map(BiblePerDayDTO::getBibleReferences)
                                   .flatMap(List::stream)
                                   .filter(StringUtils::isNotBlank)
                                   .toList();

            ValidateBibleReferenceService validateService = new ValidateBibleReferenceService();
            List<String> invalidReferences = validateService.getInvalidBibleReferences(allBibleReferences);

            if (!invalidReferences.isEmpty()) {
                FileUtils.writeToFile(invalidReferences, getInvalidReferencesFileName(month));
            }
        }
    }

    private static String getInvalidReferencesFileName(int month) {
        return "Invalidate_ref_" + month + "_" + YEAR + ".txt";
    }

}
