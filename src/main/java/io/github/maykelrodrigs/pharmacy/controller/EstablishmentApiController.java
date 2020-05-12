package io.github.maykelrodrigs.pharmacy.controller;

import io.github.maykelrodrigs.pharmacy.controller.swagger.EstablishmentApi;
import io.github.maykelrodrigs.pharmacy.domain.Establishment;
import io.github.maykelrodrigs.pharmacy.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/establishment")
public class EstablishmentApiController implements EstablishmentApi {

    @Autowired
    private EstablishmentService establishmentService;

    @PostMapping
    public ResponseEntity<Establishment> create(@RequestBody @Valid Establishment establishment) {
        Establishment result = establishmentService.save(establishment);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Establishment> update(@RequestBody @Valid Establishment establishment) {
        Establishment result = establishmentService.update(establishment);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        establishmentService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establishment> findById(@PathVariable String id) {
        Establishment result = establishmentService.findById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<Page<Establishment>> findAll(Pageable pageable) {
        Page<Establishment> result = establishmentService.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
