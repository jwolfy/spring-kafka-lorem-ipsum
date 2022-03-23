package jwolfy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Entity
@Table(name = "calculation_result")
public class CalculationResult {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "freq_word")
    private String freqWord;

    @Column(name = "avg_paragraph_size")
    private Integer avgParagraphSize;

    @Column(name = "avg_paragraph_processing_time")
    private Long avgParagraphProcessingTime;

    @Column(name = "total_processing_time")
    private Long totalProcessingTime;

    @JsonIgnore
    @CreatedDate
    private Instant createdDate;

    public CalculationResult() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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
                "id=" + id +
                ", freqWord='" + freqWord + '\'' +
                ", avgParagraphSize=" + avgParagraphSize +
                ", avgParagraphProcessingTime=" + avgParagraphProcessingTime +
                ", totalProcessingTime=" + totalProcessingTime +
                ", createdDate=" + createdDate +
                '}';
    }
}
