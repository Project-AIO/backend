package com.idt.aio.repository;

import com.idt.aio.entity.Admin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    @EntityGraph(attributePaths = "authorities")
    Optional<Admin> findOneWithAuthoritiesByAdminId(String adminId);

    Optional<Admin> findByAdminId(String adminId);

}
