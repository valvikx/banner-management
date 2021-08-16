package by.valvik.bannermanagement.controller;

import by.valvik.bannermanagement.domain.BaseEntity;
import by.valvik.bannermanagement.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public abstract class BaseController<E extends BaseEntity, S extends GenericService<E>> {

    private final S service;

    public BaseController(S service) {

        this.service = service;

    }

    @GetMapping("{id}")
    public ResponseEntity<E> getOne(@PathVariable Integer id) {

        return ResponseEntity.ok(service.getOne(id));

    }

    @GetMapping()
    public ResponseEntity<List<E>> getAll() {

        return ResponseEntity.ok(service.getAll());

    }

    @PostMapping
    public ResponseEntity<E> create(@RequestBody @Valid E entityFromClient) {

        return ResponseEntity.ok(service.create(entityFromClient));

    }

    @PutMapping("{id}")
    public ResponseEntity<E> update(@PathVariable Integer id, @RequestBody @Valid E entityFromClient) {

        return ResponseEntity.ok(service.update(id, entityFromClient));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<E> delete(@PathVariable Integer id) {

        return ResponseEntity.ok(service.delete(id));

    }

}
