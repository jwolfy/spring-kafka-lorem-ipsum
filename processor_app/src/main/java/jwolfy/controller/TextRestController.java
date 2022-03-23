package jwolfy.controller;

import jwolfy.domain.CalculationResult;
import jwolfy.service.WordProcessorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/betvictor/text")
@Validated
public class TextRestController {
    private final WordProcessorFacade wordProcessorFacade;

    @Autowired
    public TextRestController(WordProcessorFacade wordProcessorFacade) {
        this.wordProcessorFacade = wordProcessorFacade;
    }

    @GetMapping
    public CalculationResult getResult(
            @RequestParam("p") @Min(1) int paragraphNum,
            @RequestParam("l") ParagraphLength paragraphLength
    ) {
        return wordProcessorFacade.getAndProduceResult(paragraphNum, paragraphLength);
    }
}
