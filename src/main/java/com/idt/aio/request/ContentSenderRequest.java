package com.idt.aio.request;

import java.util.List;
import lombok.Builder;

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
