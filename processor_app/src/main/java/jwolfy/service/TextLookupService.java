package jwolfy.service;

import jwolfy.controller.ParagraphLength;

import java.util.concurrent.CompletableFuture;

public interface TextLookupService {
    CompletableFuture<String> fetchText(int paragraphNum, ParagraphLength paragraphLength);
}
