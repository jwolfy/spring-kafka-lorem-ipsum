package jwolfy.repository;

import jwolfy.domain.CalculationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationResultRepository extends JpaRepository<CalculationResult, Integer> {

    List<CalculationResult> findTop10ByOrderByCreatedDateDesc();
}
