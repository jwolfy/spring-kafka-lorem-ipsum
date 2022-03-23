package jwolfy.domain;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Custom collector that calculates average paragraph size, average processing time and the most frequent word
 * for the provided {@link ParagraphStats} objects
 */
public class ParagraphSummaryCollector implements Collector<ParagraphStats, List<ParagraphStats>, CalculationResult> {
    private final List<String> words = new ArrayList<>();
    private Long processingTime = 0L;

    @Override
    public Supplier<List<ParagraphStats>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<ParagraphStats>, ParagraphStats> accumulator() {
        return (list, stats) -> {
            words.addAll(stats.getWords());
            processingTime += stats.getParagraphProcessingTime();
            list.add(stats);
        };
    }

    @Override
    public BinaryOperator<List<ParagraphStats>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<ParagraphStats>, CalculationResult> finisher() {
        return list -> {
            String mostFrequentWord = getMostFrequentWord(words);
            int averageParagraphSize = words.size() / list.size();
            long averageProcessingTime = processingTime / list.size();
            return new CalculationResult(mostFrequentWord, averageParagraphSize, averageProcessingTime);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }

    private String getMostFrequentWord(List<String> words) {
        Map<String, Long> lengths = words.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        return lengths.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }
}
