package com.idt.aio.service;

import com.idt.aio.entity.Answer;
import com.idt.aio.entity.Document;
import com.idt.aio.entity.SimilarityDoc;
import com.idt.aio.exception.DomainExceptionCode;
import com.idt.aio.repository.AnswerRepository;
import com.idt.aio.repository.DocumentRepository;
import com.idt.aio.repository.SimilarityDocRepository;
import com.idt.aio.request.SimilarityDocRequest;
import com.idt.aio.response.SimilarityDocResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SimilarityDocService {
    private final SimilarityDocRepository similarityDocRepository;
    private final DocumentRepository documentRepository;
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<SimilarityDocResponse> getSimilarityDocsByDocId(final Integer docId) {
        final List<SimilarityDoc> similarityDocs = similarityDocRepository.getSimilarityDocsByDocument_DocId(docId);
        return SimilarityDocResponse.from(similarityDocs);
    }

    @Transactional(readOnly = true)
    public SimilarityDocResponse getSimilarityDocById(final Integer similarityDocId) {
        final SimilarityDoc similarityDoc = similarityDocRepository.getSimilarityDocsBySimilarityDocId(similarityDocId);
        return SimilarityDocResponse.from(similarityDoc);
    }

    @Transactional
    public void saveSimilarityDoc(final SimilarityDocRequest params) {
        if (!documentRepository.existsById(params.docId())) {
            throw DomainExceptionCode.DOCUMENT_NOT_FOUND.newInstance();
        }

        if (!answerRepository.existsById(params.answerId())) {
            throw DomainExceptionCode.ANSWER_NOT_FOUND.newInstance();
        }

        final Document documentReferenceById = documentRepository.getReferenceById(params.docId());
        final Answer answerReferenceById = answerRepository.getReferenceById(params.answerId());

        similarityDocRepository.save(SimilarityDoc.builder()
                .document(documentReferenceById)
                .answer(answerReferenceById)
                .page(params.page())
                .score(params.score())
                .build());


    }

    @Transactional
    public void deleteSimilarityDocsByDocumentId(final Integer docId) {
        similarityDocRepository.deleteSimilarityDocByDocument_DocId(docId);
    }

    @Transactional
    public void deleteSimilarityDocsById(final Integer similarityDocId) {
        similarityDocRepository.deleteById(similarityDocId);
    }
}
