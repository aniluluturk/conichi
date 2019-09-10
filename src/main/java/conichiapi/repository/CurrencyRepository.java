package conichiapi.repository;

import conichiapi.model.CurrencyResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyResponse, Long> {
    CurrencyResponse findBySourceCurrencyAndTargetCurrencyOrderByIdDesc(String source, String target);
}