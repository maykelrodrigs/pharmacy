package io.github.maykelrodrigs.pharmacy.service.impl;

import io.github.maykelrodrigs.pharmacy.domain.Establishment;
import io.github.maykelrodrigs.pharmacy.service.exception.BusinessException;
import io.github.maykelrodrigs.pharmacy.service.exception.EntityNotFoundException;
import io.github.maykelrodrigs.pharmacy.repository.EstablishmentRespository;
import io.github.maykelrodrigs.pharmacy.service.EstablishmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class EstablishmentServiceImpl implements EstablishmentService {

    private EstablishmentRespository establishmentRespository;

    public EstablishmentServiceImpl(EstablishmentRespository establishmentRespository) {
        this.establishmentRespository = establishmentRespository;
    }

    @Override
    public Establishment save(Establishment establishment) {
        if (Objects.nonNull(establishment.getId()))
            throw new BusinessException("id exists");

        return establishmentRespository.save(establishment);
    }

    @Override
    public Establishment update(Establishment establishment) {
        if (Objects.isNull(establishment.getId()))
            throw new BusinessException("id not found");

        return establishmentRespository.findById(establishment.getId())
                .map(el -> establishmentRespository.save(establishment))
                .orElseThrow(() -> new EntityNotFoundException(textEntityNotFound(establishment.getId())));
    }

    @Override
    public Establishment findById(String id) {
        return establishmentRespository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(textEntityNotFound(id)));
    }

    @Override
    public Page<Establishment> findAll(Pageable pageable) {
        return establishmentRespository.findAll(pageable);
    }

    @Override
    public void remove(String id) {
        establishmentRespository.delete(
                establishmentRespository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(textEntityNotFound(id))));
    }

    private static String textEntityNotFound(String id) {
        return String.format("no establishment with id %s exists", id);
    }

}
