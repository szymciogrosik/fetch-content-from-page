package org.gross.bibleperday;

import org.apache.commons.lang3.StringUtils;
import org.gross.bibleperday.dto.BiblePerDayContainer;
import org.gross.bibleperday.dto.BiblePerDayDTO;
import org.gross.utils.FileUtils;
import org.gross.utils.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ValidateContent {

    private static final int FIRST_MONTH = 1;
    private static final int LAST_MONTH = 12;
    private static final int YEAR = 2023;

    private static final String PATH_PREFIX = "bibleperday/" + YEAR + "/";

    private static final String INVALID_REFS_FILE_NAME_PREFIX = "Invalidate_ref";
    private static final String ALL_REFS_FILE_NAME_PREFIX = "All_pure_references.txt";

    public static void main(String[] args) {
        List<String> allPureReferences = new ArrayList<>();

        for (int month = FIRST_MONTH; month <= LAST_MONTH; month++) {
            File file = FileUtils.getFile(PATH_PREFIX + BiblePerDayDownloadService.getFileName(month));
            BiblePerDayContainer biblePerDayContainer = JsonUtils.deserialize(file, BiblePerDayContainer.class);
            List<String> bibleReferencesPerMonth =
                    biblePerDayContainer.getBiblePerDayList().stream()
                                   .map(BiblePerDayDTO::getBibleReferences)
                                   .flatMap(List::stream)
                                   .filter(StringUtils::isNotBlank)
                                   .toList();
            allPureReferences.addAll(bibleReferencesPerMonth);

            ValidateBibleReferenceService validateService = new ValidateBibleReferenceService();
            List<String> invalidReferences = validateService.getInvalidBibleReferences(bibleReferencesPerMonth);

            if (!invalidReferences.isEmpty()) {
                FileUtils.writeToFile(invalidReferences, getInvalidReferencesFileName(INVALID_REFS_FILE_NAME_PREFIX, month));
            }
        }

        FileUtils.writeToFile(allPureReferences, ALL_REFS_FILE_NAME_PREFIX);
    }

    private static String getInvalidReferencesFileName(String filePrefix, int month) {
        return filePrefix + "_" + month + "_" + YEAR + ".txt";
    }

}
