package jwolfy.service;

import jwolfy.domain.CalculationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {
    private final TextService textService;

    @Autowired
    public KafkaConsumerListener(TextService textService) {
        this.textService = textService;
    }

    @KafkaListener(topics = "${kafka.topic}", containerFactory = "kafkaListenerContainerFactory")
    public void calculationResultListener(CalculationResult calculationResult) {
        textService.addCalculationResult(calculationResult);
    }
}
