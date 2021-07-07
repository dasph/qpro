package me.amvse.qpro.repositories;

import me.amvse.qpro.models.Test;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
  Optional<Test> findOneByKey (String key);
}
