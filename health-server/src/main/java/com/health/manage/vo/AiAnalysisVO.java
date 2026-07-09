package com.health.manage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AI 健康分析响应 VO
 */
@Data
@Schema(description = "AI健康分析响应")
public class AiAnalysisVO {

    @Schema(description = "健康改善建议")
    private String healthAdvice;

    @Schema(description = "运动建议")
    private String exerciseAdvice;

    @Schema(description = "饮食建议")
    private String dietAdvice;
}
