package com.idt.aio.repository;

import com.idt.aio.entity.Synonym;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Integer> {
    Page<Synonym> findByProject_ProjectId(Integer projectId, Pageable pageable);
    List<Synonym> findByProject_ProjectId(Integer projectId);

    @Modifying
    @Query("update Synonym s set s.source = :source, s.match = :match where s.synonymId = :synonymId")
    void updateSynonymById(final Integer synonymId, @Param("source") final  String source, @Param("match") final String match);

}
