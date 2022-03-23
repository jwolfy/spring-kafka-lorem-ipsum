package jwolfy.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * Response object for the main REST endpoint
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CalculationResult {
    private String freqWord;
    private Integer avgParagraphSize;
    private Long avgParagraphProcessingTime;
    private Long totalProcessingTime;

    public CalculationResult(String freqWord, Integer avgParagraphSize, Long avgParagraphProcessingTime) {
        this.freqWord = freqWord;
        this.avgParagraphSize = avgParagraphSize;
        this.avgParagraphProcessingTime = avgParagraphProcessingTime;
    }

    public String getFreqWord() {
        return freqWord;
    }

    public void setFreqWord(String freqWord) {
        this.freqWord = freqWord;
    }

    public Integer getAvgParagraphSize() {
        return avgParagraphSize;
    }

    public void setAvgParagraphSize(Integer avgParagraphSize) {
        this.avgParagraphSize = avgParagraphSize;
    }

    public Long getAvgParagraphProcessingTime() {
        return avgParagraphProcessingTime;
    }

    public void setAvgParagraphProcessingTime(Long avgParagraphProcessingTime) {
        this.avgParagraphProcessingTime = avgParagraphProcessingTime;
    }

    public Long getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(Long totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }

    @Override
    public String toString() {
        return "CalculationResult{" +
                "freqWord='" + freqWord + '\'' +
                ", avgParagraphSize=" + avgParagraphSize +
                ", avgParagraphProcessingTime=" + avgParagraphProcessingTime +
                ", totalProcessingTime=" + totalProcessingTime +
                '}';
    }
}
