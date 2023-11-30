package org.gross.bibleperday.dto;

import java.util.List;

public class BiblePerDayContainer {

    private List<BiblePerDayDTO> biblePerDayList;

    public BiblePerDayContainer() {
    }

    public BiblePerDayContainer(List<BiblePerDayDTO> biblePerDayList) {
        this.biblePerDayList = biblePerDayList;
    }

    public List<BiblePerDayDTO> getBiblePerDayList() {
        return biblePerDayList;
    }

}
