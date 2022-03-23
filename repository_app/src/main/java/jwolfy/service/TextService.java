package jwolfy.service;

import jwolfy.domain.CalculationResult;

import java.util.List;

public interface TextService {
    List<CalculationResult> getHistory();
    void addCalculationResult(CalculationResult calculationResult);
}
