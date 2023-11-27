package org.gross.bibleperday.dto;

import org.gross.utils.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BiblePerDayDTO implements Serializable {

    private final Date date;
    private final String firstStandard;
    private final String secondStandard;
    private final String firstAdditional;
    private final String secondAdditional;
    private final String quoteNotFromBible;
    private final String quoteNotFromBibleReference;

    private final List<SpecialOccasionDTO> specialOccasionList;

    private BiblePerDayDTO(Date date, String firstStandard, String secondStandard, String firstAdditional, String secondAdditional,
            String quoteNotFromBible, String quoteNotFromBibleReference, List<SpecialOccasionDTO> specialOccasionList
    ) {
        this.date = date;
        this.firstStandard = firstStandard;
        this.secondStandard = secondStandard;
        this.firstAdditional = firstAdditional;
        this.secondAdditional = secondAdditional;
        this.quoteNotFromBible = quoteNotFromBible;
        this.quoteNotFromBibleReference = quoteNotFromBibleReference;
        this.specialOccasionList = specialOccasionList;
    }

    public Date getDate() {
        return date;
    }

    public String getFirstStandard() {
        return firstStandard;
    }

    public String getSecondStandard() {
        return secondStandard;
    }

    public String getFirstAdditional() {
        return firstAdditional;
    }

    public String getSecondAdditional() {
        return secondAdditional;
    }

    public String getQuoteNotFromBible() {
        return quoteNotFromBible;
    }

    public String getQuoteNotFromBibleReference() {
        return quoteNotFromBibleReference;
    }

    public List<SpecialOccasionDTO> getSpecialOccasionList() {
        return specialOccasionList;
    }

    @Override
    public String toString() {
        return "BiblePerDayDTO{" + "date=" + DateUtils.present(date) + ", firstStandard='" + firstStandard + '\'' + ", secondStandard='" + secondStandard + '\'' +
                ", firstAdditional='" + firstAdditional + '\'' + ", secondAdditional='" + secondAdditional + '\'' + ", quoteNotFromBible='" +
                quoteNotFromBible + '\'' + ", quoteNotFromBibleReference='" + quoteNotFromBibleReference + '\'' + ", specialOccasionList=" +
                specialOccasionList + '}';
    }

    public static class Builder {

        private final Date date;

        private String firstStandard = "";
        private String secondStandard = "";
        private String firstAdditional = "";
        private String secondAdditional = "";
        private String quoteNotFromBible = "";
        private String quoteNotFromBibleReference = "";
        private List<SpecialOccasionDTO> specialOccasionList = new ArrayList<>();

        public Builder(Date date) {
            this.date = date;
        }

        public Builder setFirstStandard(String firstStandard) {
            this.firstStandard = firstStandard;
            return this;
        }

        public Builder setSecondStandard(String secondStandard) {
            this.secondStandard = secondStandard;
            return this;
        }

        public Builder setFirstAdditional(String firstAdditional) {
            this.firstAdditional = firstAdditional;
            return this;
        }

        public Builder setSecondAdditional(String secondAdditional) {
            this.secondAdditional = secondAdditional;
            return this;
        }

        public Builder setQuoteNotFromBible(String quoteNotFromBible) {
            this.quoteNotFromBible = quoteNotFromBible;
            return this;
        }

        public Builder setQuoteNotFromBibleReference(String quoteNotFromBibleReference) {
            this.quoteNotFromBibleReference = quoteNotFromBibleReference;
            return this;
        }

        public Builder setSpecialOccasionList(List<SpecialOccasionDTO> specialOccasionList) {
            this.specialOccasionList = specialOccasionList;
            return this;
        }

        public BiblePerDayDTO build() {
            return new BiblePerDayDTO(date, firstStandard, secondStandard, firstAdditional, secondAdditional, quoteNotFromBible, quoteNotFromBibleReference, specialOccasionList);
        }

    }

}
