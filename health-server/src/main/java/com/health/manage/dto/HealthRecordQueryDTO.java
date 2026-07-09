package com.health.manage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 健康记录分页条件查询 DTO
 */
@Data
@Schema(description = "健康记录查询参数")
public class HealthRecordQueryDTO {

    @Schema(description = "当前页码", example = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;

    @Schema(description = "最小身高(cm)")
    private BigDecimal minHeight;

    @Schema(description = "最大身高(cm)")
    private BigDecimal maxHeight;

    @Schema(description = "最小体重(kg)")
    private BigDecimal minWeight;

    @Schema(description = "最大体重(kg)")
    private BigDecimal maxWeight;

    @Schema(description = "最小BMI")
    private BigDecimal minBmi;

    @Schema(description = "最大BMI")
    private BigDecimal maxBmi;

    @Schema(description = "最小心率")
    private Integer minHeartRate;

    @Schema(description = "最大心率")
    private Integer maxHeartRate;

    @Schema(description = "血压关键词")
    private String bloodPressure;

    @Schema(description = "饮食习惯关键词")
    private String dietHabit;

    @Schema(description = "记录开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordTimeStart;

    @Schema(description = "记录结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime recordTimeEnd;
}
