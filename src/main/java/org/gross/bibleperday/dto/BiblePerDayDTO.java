package org.gross.bibleperday.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BiblePerDayDTO implements Serializable {

    private Date date;
    private String firstStandard;
    private String secondStandard;
    private String firstAdditional;
    private String secondAdditional;
    private String quoteNotFromBible;
    private String quoteNotFromBibleReference;

    private List<SpecialOccasionDTO> specialOccasionList;

    private ContemplationDTO contemplationDTO;

    public BiblePerDayDTO() {
    }

    private BiblePerDayDTO(Date date, String firstStandard, String secondStandard, String firstAdditional, String secondAdditional,
            String quoteNotFromBible, String quoteNotFromBibleReference, List<SpecialOccasionDTO> specialOccasionList, ContemplationDTO contemplationDTO
    ) {
        this.date = date;
        this.firstStandard = firstStandard;
        this.secondStandard = secondStandard;
        this.firstAdditional = firstAdditional;
        this.secondAdditional = secondAdditional;
        this.quoteNotFromBible = quoteNotFromBible;
        this.quoteNotFromBibleReference = quoteNotFromBibleReference;
        this.specialOccasionList = specialOccasionList;
        this.contemplationDTO = contemplationDTO;
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

    public ContemplationDTO getContemplationDTO() {
        return contemplationDTO;
    }

    @Override
    public String toString() {
        return "BiblePerDayDTO{" + "date=" + date + ", firstStandard='" + firstStandard + '\'' + ", secondStandard='" + secondStandard + '\'' +
                ", firstAdditional='" + firstAdditional + '\'' + ", secondAdditional='" + secondAdditional + '\'' + ", quoteNotFromBible='" +
                quoteNotFromBible + '\'' + ", quoteNotFromBibleReference='" + quoteNotFromBibleReference + '\'' + ", specialOccasionList=" +
                specialOccasionList + ", contemplationDTO=" + contemplationDTO + '}';
    }

    @JsonIgnore
    public List<String> getBibleReferences() {
        List<String> bibleReferences = new ArrayList<>();
        bibleReferences.add(firstStandard);
        bibleReferences.add(secondStandard);
        bibleReferences.add(firstAdditional);
        bibleReferences.add(secondAdditional);

        for (SpecialOccasionDTO specialOccasion : specialOccasionList) {
            bibleReferences.add(specialOccasion.getMainQuote());
            bibleReferences.add(specialOccasion.getPsalm());
            bibleReferences.add(specialOccasion.getApostolicLesson());
            bibleReferences.addAll(specialOccasion.getSermonTextList());
            bibleReferences.add(specialOccasion.getOldTestament());
            bibleReferences.add(specialOccasion.getGospel());
        }

        if (contemplationDTO != null) {
            bibleReferences.add(contemplationDTO.getBibleReference());
        }

        return bibleReferences;
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

        private ContemplationDTO contemplationDTO;

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

        public Builder setContemplationDTO(ContemplationDTO contemplationDTO) {
            this.contemplationDTO = contemplationDTO;
            return this;
        }

        public BiblePerDayDTO build() {
            return new BiblePerDayDTO(date, firstStandard, secondStandard, firstAdditional, secondAdditional, quoteNotFromBible, quoteNotFromBibleReference, specialOccasionList, contemplationDTO);
        }

    }

}
