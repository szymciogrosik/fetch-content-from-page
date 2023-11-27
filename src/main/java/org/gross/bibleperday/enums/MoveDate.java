package org.gross.bibleperday.enums;

public enum MoveDate {
    PREVIOUS(0),
    NEXT(1);

    private final int index;

    MoveDate(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
