package me.amvse.qpro.repositories;

import me.amvse.qpro.models.TestTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TestTemplateRepository extends JpaRepository<TestTemplate, Long> {
}
