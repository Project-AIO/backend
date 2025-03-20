package com.idt.aio.repository;

import com.idt.aio.entity.Homonym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomonymRepository extends JpaRepository<Homonym, Integer> {
    Page<Homonym> findByProject_ProjectId(Integer projectId, Pageable pageable);

    List<Homonym> findByProject_ProjectId(Integer projectId);

    @Modifying
    @Query("update Homonym s set s.source = :source, s.match = :match where s.homonymId = :homonymId")
    void updateHomonymById(final Integer homonymId, @Param("source") final String source,
                           @Param("match") final String match);

}
