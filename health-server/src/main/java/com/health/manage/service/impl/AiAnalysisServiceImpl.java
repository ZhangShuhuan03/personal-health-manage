package com.health.manage.service.impl;

import com.health.manage.entity.HealthRecord;
import com.health.manage.exception.BusinessException;
import com.health.manage.service.AiAnalysisService;
import com.health.manage.service.HealthRecordService;
import com.health.manage.vo.AiAnalysisVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * AI 健康分析 Service 实现（极简 SpringAI 集成）
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiAnalysisServiceImpl implements AiAnalysisService {

    private final ChatModel chatModel;
    private final HealthRecordService healthRecordService;

    private static final String PROMPT_TEMPLATE = """
            你是一位专业的健康顾问。请根据以下健康记录数据，分别给出简洁实用的建议（每项100字以内）：
            
            身高: %s cm
            体重: %s kg
            BMI: %s
            心率: %s 次/分
            血压: %s mmHg
            运动时长: %s 分钟
            睡眠时间: %s 小时
            饮食习惯: %s
            
            请严格按以下 JSON 格式返回，不要包含其他内容：
            {"healthAdvice":"健康改善建议","exerciseAdvice":"运动建议","dietAdvice":"饮食建议"}
            """;

    @Override
    public AiAnalysisVO analyze(Long recordId) {
        HealthRecord record = healthRecordService.lambdaQuery()
                .eq(HealthRecord::getId, recordId)
                .one();
        if (record == null) {
            throw new BusinessException("健康记录不存在，ID: " + recordId);
        }

        String prompt = String.format(PROMPT_TEMPLATE,
                nullSafe(record.getHeight()),
                nullSafe(record.getWeight()),
                nullSafe(record.getBmi()),
                nullSafe(record.getHeartRate()),
                nullSafe(record.getBloodPressure()),
                nullSafe(record.getExerciseDuration()),
                nullSafe(record.getSleepTime()),
                nullSafe(record.getDietHabit()));

        try {
            ChatResponse chatResponse = chatModel.call(new Prompt(new UserMessage(prompt)));
            String response = extractContent(chatResponse);
            log.debug("AI 分析响应: {}", response);
            return parseResponse(response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("AI 调用失败, recordId={}", recordId, e);
            throw new BusinessException("AI 分析失败，请检查 API Key 和网络配置：" + e.getMessage());
        }
    }

    private String extractContent(ChatResponse chatResponse) {
        if (chatResponse == null || chatResponse.getResult() == null) {
            throw new BusinessException("AI 返回结果为空");
        }
        AssistantMessage output = chatResponse.getResult().getOutput();
        if (output == null || !StringUtils.hasText(output.getContent())) {
            throw new BusinessException("AI 返回内容为空");
        }
        return output.getContent();
    }

    private AiAnalysisVO parseResponse(String response) {
        AiAnalysisVO vo = new AiAnalysisVO();
        try {
            String json = response.trim();
            if (json.contains("```")) {
                json = json.replaceAll("```json\\s*", "").replaceAll("```\\s*", "").trim();
            }
            vo.setHealthAdvice(extractJsonValue(json, "healthAdvice"));
            vo.setExerciseAdvice(extractJsonValue(json, "exerciseAdvice"));
            vo.setDietAdvice(extractJsonValue(json, "dietAdvice"));
            if (!StringUtils.hasText(vo.getHealthAdvice())) {
                vo.setHealthAdvice(response);
                vo.setExerciseAdvice(StringUtils.hasText(vo.getExerciseAdvice()) ? vo.getExerciseAdvice() : "请结合日常运动习惯合理安排");
                vo.setDietAdvice(StringUtils.hasText(vo.getDietAdvice()) ? vo.getDietAdvice() : "请保持均衡饮食，少油少盐");
            }
        } catch (Exception e) {
            log.warn("AI 响应解析失败，返回原始内容", e);
            vo.setHealthAdvice(response);
            vo.setExerciseAdvice("请参考健康改善建议");
            vo.setDietAdvice("请参考健康改善建议");
        }
        return vo;
    }

    private String extractJsonValue(String json, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]+)\"";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private String nullSafe(Object value) {
        return value != null ? value.toString() : "未记录";
    }
}
