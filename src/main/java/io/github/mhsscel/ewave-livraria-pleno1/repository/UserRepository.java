package io.github.mhsscel.bookjavaapi.repository;

import io.github.mhsscel.bookjavaapi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String username);

}
