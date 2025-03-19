package com.idt.aio.request;

import lombok.Builder;
import java.util.List;

@Builder
public record ContentSenderRequest(
        String jobId,
        List<RuleData> rules,
        String filePath,
        String chunkSize,
        Float overlapTokenRate,
        String empModelName,
        Integer docId
) {
}
