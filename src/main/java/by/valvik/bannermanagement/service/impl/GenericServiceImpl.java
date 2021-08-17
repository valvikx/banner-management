package by.valvik.bannermanagement.service.impl;

import by.valvik.bannermanagement.domain.BaseEntity;
import by.valvik.bannermanagement.error.ErrorAction;
import by.valvik.bannermanagement.service.GenericService;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static by.valvik.bannermanagement.util.Reflections.getEntityName;
import static by.valvik.bannermanagement.util.Reflections.getNullFieldNames;
import static org.springframework.beans.BeanUtils.copyProperties;

@Transactional
@Getter
public abstract class GenericServiceImpl<E extends BaseEntity, R extends JpaRepository<E, Integer>>
                      implements GenericService<E> {

    private final R repository;

    private final ErrorAction errorAction;

    public GenericServiceImpl(R repository, ErrorAction errorAction) {

        this.repository = repository;

        this.errorAction = errorAction;

    }

    public E getOne(Integer id) {

        return getEntityFromDb(id);

    }

    public List<E> getAll() {

        return repository.findAll();

    }

    public E create(E entityFromClient) {

        return repository.save(entityFromClient);

    }

    public E update(Integer id, E entityFromClient) {

        E entityFromDb = getFromDbAndCopyProperties(id, entityFromClient);

        return repository.save(entityFromDb);

    }

    public E delete(Integer id) {

        E entityFromDb = getEntityFromDb(id);

        repository.delete(entityFromDb);

        return entityFromDb;

    }

    public E getEntityFromDb(Integer id) {

        String entityName = getEntityName(this);

        return repository.findById(id).orElseThrow(errorAction.onNotFound(id, entityName));

    }

    public E getFromDbAndCopyProperties(Integer id, E entityFromClient) {

        E entityFromDb = getEntityFromDb(id);

        copyProperties(entityFromClient, entityFromDb, getNullFieldNames(entityFromClient));

        return entityFromDb;

    }

}
