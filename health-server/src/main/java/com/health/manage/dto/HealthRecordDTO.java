package com.health.manage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 健康记录新增/修改 DTO
 */
@Data
@Schema(description = "健康记录请求参数")
public class HealthRecordDTO {

    @Schema(description = "主键ID（修改时必填）")
    private Long id;

    @Schema(description = "身高(cm)", example = "175.00")
    @DecimalMin(value = "50.0", message = "身高不能小于50cm")
    @DecimalMax(value = "250.0", message = "身高不能大于250cm")
    private BigDecimal height;

    @Schema(description = "体重(kg)", example = "70.00")
    @DecimalMin(value = "20.0", message = "体重不能小于20kg")
    @DecimalMax(value = "300.0", message = "体重不能大于300kg")
    private BigDecimal weight;

    @Schema(description = "BMI指数", example = "22.86")
    private BigDecimal bmi;

    @Schema(description = "心率(次/分)", example = "72")
    @Min(value = 30, message = "心率不能小于30")
    @Max(value = 220, message = "心率不能大于220")
    private Integer heartRate;

    @Schema(description = "血压(mmHg)", example = "120/80")
    @Size(max = 20, message = "血压格式不正确")
    private String bloodPressure;

    @Schema(description = "运动时长(分钟)", example = "45")
    @Min(value = 0, message = "运动时长不能为负数")
    @Max(value = 1440, message = "运动时长不能超过1440分钟")
    private Integer exerciseDuration;

    @Schema(description = "睡眠时间(小时)", example = "7.5")
    @DecimalMin(value = "0.0", message = "睡眠时间不能为负数")
    @DecimalMax(value = "24.0", message = "睡眠时间不能超过24小时")
    private BigDecimal sleepTime;

    @Schema(description = "饮食习惯", example = "三餐规律，少油少盐")
    @Size(max = 500, message = "饮食习惯描述不能超过500字")
    private String dietHabit;

    @Schema(description = "记录时间", example = "2026-07-01T08:00:00")
    @NotNull(message = "记录时间不能为空")
    private LocalDateTime recordTime;
}
