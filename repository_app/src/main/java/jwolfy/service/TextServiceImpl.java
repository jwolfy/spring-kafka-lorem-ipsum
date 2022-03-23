package jwolfy.service;

import jwolfy.domain.CalculationResult;
import jwolfy.repository.CalculationResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextServiceImpl implements TextService {
    private final CalculationResultRepository repository;

    public TextServiceImpl(CalculationResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CalculationResult> getHistory() {
        return repository.findTop10ByOrderByCreatedDateDesc();
    }

    @Override
    public void addCalculationResult(CalculationResult calculationResult) {
        repository.save(calculationResult);
    }
}
