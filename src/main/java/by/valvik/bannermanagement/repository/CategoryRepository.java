package by.valvik.bannermanagement.repository;

import by.valvik.bannermanagement.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends ExtendedJpaRepository<Category> {

    Optional<Category> findByName(String categoryName);

    Optional<Category> findByReqName(String reqName);

}
