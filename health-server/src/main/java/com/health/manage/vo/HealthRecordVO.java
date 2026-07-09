package com.health.manage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 健康记录响应 VO
 */
@Data
@Schema(description = "健康记录响应")
public class HealthRecordVO {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "身高(cm)")
    private BigDecimal height;

    @Schema(description = "体重(kg)")
    private BigDecimal weight;

    @Schema(description = "BMI指数")
    private BigDecimal bmi;

    @Schema(description = "心率(次/分)")
    private Integer heartRate;

    @Schema(description = "血压(mmHg)")
    private String bloodPressure;

    @Schema(description = "运动时长(分钟)")
    private Integer exerciseDuration;

    @Schema(description = "睡眠时间(小时)")
    private BigDecimal sleepTime;

    @Schema(description = "饮食习惯")
    private String dietHabit;

    @Schema(description = "记录时间")
    private LocalDateTime recordTime;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
