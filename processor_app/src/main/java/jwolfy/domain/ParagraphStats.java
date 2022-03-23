package jwolfy.domain;

import java.util.List;

/**
 * Container that holds summary statistics for a paragraph
 */
public class ParagraphStats {
    private final List<String> words;
    private final Long paragraphProcessingTime;

    public ParagraphStats(List<String> words, Long paragraphProcessingTime) {
        this.words = words;
        this.paragraphProcessingTime = paragraphProcessingTime;
    }

    public List<String> getWords() {
        return words;
    }

    public Long getParagraphProcessingTime() {
        return paragraphProcessingTime;
    }

    @Override
    public String toString() {
        return "ParagraphStats{" +
                "freqWord='" + words + '\'' +
                ", paragraphProcessingTime=" + paragraphProcessingTime +
                '}';
    }
}
