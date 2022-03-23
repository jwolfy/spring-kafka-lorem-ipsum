package jwolfy.service;

import jwolfy.controller.ParagraphLength;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class LoripsumLookupService implements TextLookupService {
    private static final Logger log = LoggerFactory.getLogger(LoripsumLookupService.class);
    private static final String URL = "https://loripsum.net/api/%s/%s";
    private final RestTemplate restTemplate;

    @Autowired
    public LoripsumLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @Async
    public CompletableFuture<String> fetchText(int paragraphNum, ParagraphLength paragraphLength) {
        log.info("Requesting {} paragraphs with length {}", paragraphNum, paragraphLength);
        String url = String.format(URL, paragraphNum, paragraphLength.toString().toLowerCase());
        String response = restTemplate.getForObject(url, String.class);
        return CompletableFuture.completedFuture(response);
    }
}
