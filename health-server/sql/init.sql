-- 个人健康管理系统 数据库初始化脚本
-- MySQL 8.x

CREATE DATABASE IF NOT EXISTS health_manage DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE health_manage;

-- 健康记录表
DROP TABLE IF EXISTS health_record;
CREATE TABLE health_record (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    height          DECIMAL(5,2)    DEFAULT NULL COMMENT '身高(cm)',
    weight          DECIMAL(5,2)    DEFAULT NULL COMMENT '体重(kg)',
    bmi             DECIMAL(4,2)    DEFAULT NULL COMMENT 'BMI指数',
    heart_rate      INT             DEFAULT NULL COMMENT '心率(次/分)',
    blood_pressure  VARCHAR(20)     DEFAULT NULL COMMENT '血压(mmHg)，如 120/80',
    exercise_duration INT           DEFAULT NULL COMMENT '运动时长(分钟)',
    sleep_time      DECIMAL(4,1)    DEFAULT NULL COMMENT '睡眠时间(小时)',
    diet_habit      VARCHAR(500)    DEFAULT NULL COMMENT '饮食习惯',
    record_time     DATETIME        NOT NULL COMMENT '记录时间',
    deleted         TINYINT         NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
    create_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    INDEX idx_record_time (record_time),
    INDEX idx_deleted (deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康记录表';

-- 示例数据
INSERT INTO health_record (height, weight, bmi, heart_rate, blood_pressure, exercise_duration, sleep_time, diet_habit, record_time)
VALUES
    (175.00, 70.00, 22.86, 72, '120/80', 45, 7.5, '三餐规律，少油少盐', '2026-07-01 08:00:00'),
    (168.00, 65.00, 23.03, 78, '125/82', 30, 6.0, '早餐经常不吃，晚餐偏油腻', '2026-07-05 09:30:00'),
    (180.00, 85.00, 26.23, 85, '135/90', 15, 5.5, '喜食甜食和油炸食品', '2026-07-08 07:00:00');
