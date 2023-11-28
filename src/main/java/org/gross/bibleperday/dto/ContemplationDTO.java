package org.gross.bibleperday.dto;

import java.util.List;

public class ContemplationDTO {

    private final String bibleReference;
    private final List<String> textList;
    private final String textReference;

    private ContemplationDTO(String bibleReference, List<String> textList, String textReference) {
        this.bibleReference = bibleReference;
        this.textList = textList;
        this.textReference = textReference;
    }

    public String getBibleReference() {
        return bibleReference;
    }

    public List<String> getText() {
        return textList;
    }

    public String getTextReference() {
        return textReference;
    }

    @Override
    public String toString() {
        return "ContemplationDTO{" + "bibleReference='" + bibleReference + '\'' + ", text='" + textList + '\'' + ", textReference='" + textReference + '\'' + '}';
    }

    public static class Builder {
        private String bibleReference;
        private List<String> textList;
        private String textReference;

        public Builder setBibleReference(String bibleReference) {
            this.bibleReference = bibleReference;
            return this;
        }

        public Builder setTextList(List<String> textList) {
            this.textList = textList;
            return this;
        }

        public Builder setTextReference(String textReference) {
            this.textReference = textReference;
            return this;
        }

        public ContemplationDTO build() {
            return new ContemplationDTO(bibleReference, textList, textReference);
        }

    }

}
