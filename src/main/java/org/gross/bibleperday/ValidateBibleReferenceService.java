package org.gross.bibleperday;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidateBibleReferenceService {

    private static final List<String> ALLOWED_SHORTCUTS =
            Arrays.asList("Mż", "Joz", "Sdz", "Rt", "Sm", "Krl", "Krn", "Ezd", "Ne", "Est", "Hi", "Ps", "Prz", "Kz", "Pnp", "Iz", "Jr", "Tr", "Ez", "Dn", "Oz",
                          "Jl", "Am", "Ab", "Jon", "Mi", "Na", "Ha", "So", "Ag", "Za", "Ml", "Mt", "Mk", "Łk", "J", "Dz", "Rz", "Kor", "Ga", "Ef", "Flp", "Kol",
                          "Tes", "Tm", "Tt", "Flm", "Hbr", "Jk", "P", "J", "Jud", "Obj");

    public List<String> getInvalidBibleReferences(List<String> allReferences) {
        List<String> invalidateReferences = new ArrayList<>();
        for (String reference : allReferences) {
            if (!isValidReference(reference)) {
                invalidateReferences.add(reference);
            }
        }
        return invalidateReferences;
    }

    private boolean isValidReference(String reference) {
        String[] refElements = reference.split(" ");
        int currentIndex = 0;
        String first = refElements[currentIndex];

        if (isPureNumber(first)) {
            int bookPart = Integer.parseInt(first);
            if (bookPart < 1 || bookPart > 5) {
                logErrorMessage("Invalid book number", first, reference);
                return false;
            } else {
                currentIndex++;
            }
        }

        return isValidReferenceWithoutBookPartNumber(refElements, currentIndex, reference);
    }

    private boolean isValidReferenceWithoutBookPartNumber(String[] refElements, int currentIndex, String fullReference) {
        String bookRef = refElements[currentIndex];
        if (ALLOWED_SHORTCUTS.stream().noneMatch(shortcut -> shortcut.equals(bookRef))) {
            logErrorMessage("Invalid shortcut name", bookRef, fullReference);
            return false;
        }
        currentIndex++;

        String chapterAndVerse = refElements[currentIndex];
        if (!isPureNumber(chapterAndVerse)) {
            String[] spitedChanderVerse = chapterAndVerse.split(",");
            // Chapter number
            String chapterNumber = spitedChanderVerse[0];
            if (!isPureNumber(chapterNumber)) {
                logErrorMessage("Chapter is not a number", chapterNumber, fullReference);
                return false;
            }
            // Verse validation
            String verse = spitedChanderVerse[1];
            if (!isPureNumber(verse)) {
                String[] spitedVerse = verse.split("-");
                if (spitedVerse.length != 2) {
                    logErrorMessage("Verse contain invalid number of elements, verse", verse, fullReference);
                    return false;
                }
                if (!isPureNumber(spitedVerse[0])) {
                    logErrorMessage("Sub verse 1 is not pure number", spitedVerse[0], fullReference);
                    return false;
                }
                if (!isPureNumber(spitedVerse[1])) {
                    logErrorMessage("Sub verse 2 is not pure number", spitedVerse[1], fullReference);
                    return false;
                }
            }
        }
        currentIndex++;

        // Check if there is nothing more
        if (refElements.length != currentIndex) {
            logErrorMessage("There is something more after last allowed element, refElements length", refElements.length + "", fullReference);
            return false;
        }
        return true;
    }

    private boolean isPureNumber(String possibleNumber) {
        try {
            Integer.parseInt(possibleNumber);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private void logErrorMessage(String errorMessage, String partOfReference, String fullReference) {
        System.out.println(errorMessage + ": " + partOfReference + ". Full reference: " + fullReference);
    }

}
