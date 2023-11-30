package org.gross.bibleperday.dto;

import org.gross.bibleperday.enums.Occasion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SpecialOccasionDTO implements Serializable {

    private Occasion occasion;
    private String title;
    private String mainQuote;
    private String psalm;
    private List<String> worshipSongs;
    private String apostolicLesson;
    private List<String> sermonTextList;
    private String oldTestament;
    private String gospel;

    public SpecialOccasionDTO() {
    }

    private SpecialOccasionDTO(Occasion occasion, String title, String mainQuote, String psalm, List<String> worshipSongs, String apostolicLesson,
            List<String> sermonTextList, String oldTestament, String gospel) {
        this.occasion = occasion;
        this.title = title;
        this.mainQuote = mainQuote;
        this.psalm = psalm;
        this.worshipSongs = worshipSongs;
        this.apostolicLesson = apostolicLesson;
        this.sermonTextList = sermonTextList;
        this.oldTestament = oldTestament;
        this.gospel = gospel;
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public String getTitle() {
        return title;
    }

    public String getMainQuote() {
        return mainQuote;
    }

    public String getPsalm() {
        return psalm;
    }

    public List<String> getWorshipSongs() {
        return worshipSongs;
    }

    public String getApostolicLesson() {
        return apostolicLesson;
    }

    public List<String> getSermonTextList() {
        return sermonTextList;
    }

    public String getOldTestament() {
        return oldTestament;
    }

    public String getGospel() {
        return gospel;
    }

    @Override
    public String toString() {
        return "SpecialOccasionDTO{" + "occasion=" + occasion + ", title='" + title + '\'' + ", mainQuote='" + mainQuote + '\'' + ", psalm='" + psalm + '\'' +
                ", worshipSong='" + worshipSongs + '\'' + ", apostolicLesson='" + apostolicLesson + '\'' + ", sermonText='" + sermonTextList + '\'' +
                ", oldTestament='" + oldTestament + '\'' + ", gospel='" + gospel + '\'' + '}';
    }

    public static class Builder {

        private Occasion occasion;
        private String title = "";
        private String mainQuote = "";
        private String psalm = "";
        private List<String> worshipSongs = new ArrayList<>();
        private String apostolicLesson = "";
        private List<String> sermonTextList = new ArrayList<>();
        private String oldTestament = "";
        private String gospel = "";

        public Builder setOccasion(Occasion occasion) {
            this.occasion = occasion;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMainQuote(String mainQuote) {
            this.mainQuote = mainQuote;
            return this;
        }

        public Builder setPsalm(String psalm) {
            this.psalm = psalm;
            return this;
        }

        public Builder setWorshipSongs(List<String> worshipSongs) {
            this.worshipSongs = worshipSongs;
            return this;
        }

        public Builder setApostolicLesson(String apostolicLesson) {
            this.apostolicLesson = apostolicLesson;
            return this;
        }

        public Builder setSermonTextList(List<String> sermonTextList) {
            this.sermonTextList = sermonTextList;
            return this;
        }

        public Builder setOldTestament(String oldTestament) {
            this.oldTestament = oldTestament;
            return this;
        }

        public Builder setGospel(String gospel) {
            this.gospel = gospel;
            return this;
        }

        public SpecialOccasionDTO build() {
            return new SpecialOccasionDTO(occasion, title, mainQuote, psalm, worshipSongs, apostolicLesson, sermonTextList, oldTestament, gospel);
        }

    }

}
