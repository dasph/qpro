package me.amvse.qpro.repositories;

import me.amvse.qpro.models.Test;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
