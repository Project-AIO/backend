package com.idt.aio.service;

import com.idt.aio.dto.SynonymDto;
import com.idt.aio.entity.Synonym;
import com.idt.aio.repository.SynonymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SynonymService {
    private final SynonymRepository synonymRepository;

    public Page<SynonymDto> fetchSynonymsByProjectIdByPage(Integer projectId, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("synonymId").ascending());
        Page<Synonym> synonymPage = synonymRepository.findByProject_ProjectId(projectId, pageable);
        return synonymPage.map(s->SynonymDto.from(s));
    }
}
