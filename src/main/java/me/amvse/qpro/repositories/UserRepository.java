package me.amvse.qpro.repositories;

import me.amvse.qpro.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findOneByEmail (String email);
}
