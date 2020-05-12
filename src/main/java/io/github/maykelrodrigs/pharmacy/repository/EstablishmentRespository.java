package io.github.maykelrodrigs.pharmacy.repository;

import io.github.maykelrodrigs.pharmacy.domain.Establishment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablishmentRespository extends MongoRepository<Establishment, String> {

}
