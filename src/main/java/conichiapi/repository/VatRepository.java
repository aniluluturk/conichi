package conichiapi.repository;

import conichiapi.model.VatResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatRepository extends CrudRepository<VatResponse, Long> {
    public VatResponse findByVatNumber(String vatNumber);
}