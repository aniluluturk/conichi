package conichiapi.repository;

import conichiapi.model.ConversionResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends CrudRepository<ConversionResponse, Long> {
    public ConversionResponse findBySourceCurrencyAndTargetCurrencyOrderByIdDesc(String source, String target);
}