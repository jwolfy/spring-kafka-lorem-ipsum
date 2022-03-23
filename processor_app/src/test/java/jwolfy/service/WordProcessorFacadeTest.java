package jwolfy.service;

import jwolfy.controller.ParagraphLength;
import jwolfy.domain.CalculationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordProcessorFacadeTest {
    @Mock
    private TextLookupService textLookupService;

    @Mock
    private KafkaTemplate<String, CalculationResult> kafkaTemplate;

    @InjectMocks
    private WordProcessorFacade wordProcessorFacade;

    @Test
    void shouldProcessAndSendResult() {
        // GIVEN
        CompletableFuture<String> future1 = CompletableFuture.completedFuture("<p>one two three</p>");
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("<p>one two</p>\n<p>two</p>");
        when(textLookupService.fetchText(1, ParagraphLength.SHORT)).thenReturn(future1);
        when(textLookupService.fetchText(2, ParagraphLength.SHORT)).thenReturn(future2);

        // WHEN
        CalculationResult result = wordProcessorFacade.getAndProduceResult(2, ParagraphLength.SHORT);

        // THEN
        assertEquals("two", result.getFreqWord());
        assertEquals(2, result.getAvgParagraphSize());
        verify(kafkaTemplate, times(1)).send(any(), eq(result.getFreqWord()), eq(result));
    }
}