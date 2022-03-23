package jwolfy.controller;

import jwolfy.domain.CalculationResult;
import jwolfy.service.TextService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/betvictor/history")
public class HistoryRestController {
    private final TextService textService;

    public HistoryRestController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping
    public List<CalculationResult> getResultHistory() {
        return textService.getHistory();
    }
}
