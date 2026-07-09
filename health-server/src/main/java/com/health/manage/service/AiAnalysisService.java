package com.health.manage.service;

import com.health.manage.vo.AiAnalysisVO;

/**
 * AI 健康分析 Service
 */
public interface AiAnalysisService {

    /**
     * 根据健康记录ID，AI 生成健康改善建议
     */
    AiAnalysisVO analyze(Long recordId);
}
