package by.valvik.bannermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ExtendedJpaRepository<T> extends JpaRepository<T, Integer> {

    @Override
    @Query("FROM #{#entityName} e WHERE e.isDeleted = false")
    List<T> findAll();

}
