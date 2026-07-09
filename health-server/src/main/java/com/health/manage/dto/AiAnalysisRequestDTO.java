package com.health.manage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI 健康分析请求 DTO
 */
@Data
@Schema(description = "AI健康分析请求参数")
public class AiAnalysisRequestDTO {

    @Schema(description = "健康记录ID", example = "1")
    @NotNull(message = "健康记录ID不能为空")
    private Long recordId;
}
