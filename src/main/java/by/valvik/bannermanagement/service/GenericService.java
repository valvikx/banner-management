package by.valvik.bannermanagement.service;

import java.util.List;

public interface GenericService<E> {

    E getOne(Integer id);

    List<E> getAll();

    E create(E entityFromClient);

    E update(Integer id, E entityFromClient);

    E delete(Integer id);

}
