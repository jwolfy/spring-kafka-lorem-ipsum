package jwolfy.service;

import jwolfy.domain.CalculationResult;
import jwolfy.controller.ParagraphLength;
import jwolfy.domain.ParagraphStats;
import jwolfy.domain.ParagraphSummaryCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Encapsulates a lookup service which fetches request results and a kafka producer for sending results to a topic
 */
@Component
public class WordProcessorFacade {
    private static final Logger log = LoggerFactory.getLogger(WordProcessorFacade.class);
    private final TextLookupService textLookupService;
    private final KafkaTemplate<String, CalculationResult> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    @Autowired
    public WordProcessorFacade(TextLookupService textLookupService, KafkaTemplate<String, CalculationResult> kafkaTemplate) {
        this.textLookupService = textLookupService;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Accumulates result with calculated values for the given request, sends it to a Kafka topic and returns it
     * @param paragraphNum max number of paragraphs
     * @param paragraphLength length of paragraphs
     * @return populated CalculationResult object
     */
    public CalculationResult getAndProduceResult(int paragraphNum, ParagraphLength paragraphLength) {
        long start = System.nanoTime();

        List<CompletableFuture<String>> futures = createCompletableFutures(paragraphNum, paragraphLength);
        CalculationResult calculationResult = futures.stream()
                .map(CompletableFuture::join)
                .map(this::calculateParagraphStats)
                .flatMap(List::stream)
                .collect(new ParagraphSummaryCollector());

        long end = System.nanoTime();
        long totalProcessingTime = end - start;
        log.info("Total request processing time: {} ns", totalProcessingTime);

        calculationResult.setTotalProcessingTime(totalProcessingTime);

        kafkaTemplate.send(topic, calculationResult.getFreqWord(), calculationResult);

        return calculationResult;
    }

    private List<ParagraphStats> calculateParagraphStats(String s) {
        String[] paragraphs = s.split("\\s*\\n\\s*");
        List<ParagraphStats> stats = new ArrayList<>();

        for (String paragraph : paragraphs) {
            long start = System.nanoTime();
            String text = paragraph.replaceAll("<p>|</p>|\\?|;|,|\\.", "");
            String[] words = text.split(" ");
            long end = System.nanoTime();
            stats.add(new ParagraphStats(Arrays.asList(words), end - start));
        }
        return stats;
    }

    private List<CompletableFuture<String>> createCompletableFutures(int paragraphNum, ParagraphLength paragraphLength) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        int counter = 1;
        while (counter <= paragraphNum) {
            CompletableFuture<String> future = textLookupService.fetchText(counter, paragraphLength);
            futures.add(future);
            counter++;
        }
        return futures;
    }
}
