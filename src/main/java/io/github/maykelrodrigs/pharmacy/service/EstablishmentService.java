package io.github.maykelrodrigs.pharmacy.service;

import io.github.maykelrodrigs.pharmacy.domain.Establishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstablishmentService {

    Establishment save(Establishment establishment);

    Establishment update(Establishment establishment);

    Establishment findById(String id);

    Page<Establishment> findAll(Pageable pageable);

    void remove(String establishment);

}
