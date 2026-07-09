package com.health.manage.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 健康记录实体
 */
@Data
@TableName("health_record")
public class HealthRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 身高(cm) */
    private BigDecimal height;

    /** 体重(kg) */
    private BigDecimal weight;

    /** BMI指数 */
    private BigDecimal bmi;

    /** 心率(次/分) */
    private Integer heartRate;

    /** 血压(mmHg) */
    private String bloodPressure;

    /** 运动时长(分钟) */
    private Integer exerciseDuration;

    /** 睡眠时间(小时) */
    private BigDecimal sleepTime;

    /** 饮食习惯 */
    private String dietHabit;

    /** 记录时间 */
    private LocalDateTime recordTime;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
