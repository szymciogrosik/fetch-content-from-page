package org.gross.bibleperday.dto;

public class ContemplationDTO {

    private final String bibleReference;
    private final String text;
    private final String textReference;

    private ContemplationDTO(String bibleReference, String text, String textReference) {
        this.bibleReference = bibleReference;
        this.text = text;
        this.textReference = textReference;
    }

    public String getBibleReference() {
        return bibleReference;
    }

    public String getText() {
        return text;
    }

    public String getTextReference() {
        return textReference;
    }

    @Override
    public String toString() {
        return "ContemplationDTO{" + "bibleReference='" + bibleReference + '\'' + ", text='" + text + '\'' + ", textReference='" + textReference + '\'' + '}';
    }

    public static class Builder {
        private String bibleReference;
        private String text;
        private String textReference;

        public Builder setBibleReference(String bibleReference) {
            this.bibleReference = bibleReference;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextReference(String textReference) {
            this.textReference = textReference;
            return this;
        }

        public ContemplationDTO build() {
            return new ContemplationDTO(bibleReference, text, textReference);
        }

    }

}
