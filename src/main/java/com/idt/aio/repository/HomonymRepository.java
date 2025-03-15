package com.idt.aio.repository;

import com.idt.aio.entity.Homonym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomonymRepository extends JpaRepository<Homonym, Integer> {
}
