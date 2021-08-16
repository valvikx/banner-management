package by.valvik.bannermanagement.security.repository;

import by.valvik.bannermanagement.security.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByLogin(String login);

}
