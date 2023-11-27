package org.gross.bibleperday.enums;

public enum Speed {
    FAST(50),
    MEDIUM(500),
    SLOW(1000);

    private final long durationMillis;

    Speed(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

}
