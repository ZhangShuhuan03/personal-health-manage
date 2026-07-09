package com.health.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.health.manage.common.Result;
import com.health.manage.dto.AiAnalysisRequestDTO;
import com.health.manage.dto.HealthRecordDTO;
import com.health.manage.dto.HealthRecordQueryDTO;
import com.health.manage.service.AiAnalysisService;
import com.health.manage.service.HealthRecordService;
import com.health.manage.vo.AiAnalysisVO;
import com.health.manage.vo.HealthRecordVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 健康记录 CRUD + AI 分析接口
 */
@Tag(name = "健康档案管理")
@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordService healthRecordService;
    private final AiAnalysisService aiAnalysisService;

    @Operation(summary = "分页条件查询")
    @GetMapping("/list")
    public Result<IPage<HealthRecordVO>> list(HealthRecordQueryDTO query) {
        return Result.success(healthRecordService.pageQuery(query));
    }

    @Operation(summary = "根据ID查询")
    @GetMapping("/{id}")
    public Result<HealthRecordVO> getById(
            @Parameter(description = "记录ID") @PathVariable Long id) {
        return Result.success(healthRecordService.getDetailById(id));
    }

    @Operation(summary = "新增健康记录")
    @PostMapping("/add")
    public Result<Long> add(@Valid @RequestBody HealthRecordDTO dto) {
        return Result.success(healthRecordService.add(dto));
    }

    @Operation(summary = "修改健康记录")
    @PutMapping("/update")
    public Result<Void> update(@Valid @RequestBody HealthRecordDTO dto) {
        healthRecordService.update(dto);
        return Result.success();
    }

    @Operation(summary = "逻辑删除")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @Parameter(description = "记录ID") @PathVariable Long id) {
        healthRecordService.deleteById(id);
        return Result.success();
    }

    @Operation(summary = "批量逻辑删除")
    @DeleteMapping("/batch")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        healthRecordService.deleteBatch(ids);
        return Result.success();
    }

    @Operation(summary = "AI健康分析")
    @PostMapping("/ai/suggest")
    public Result<AiAnalysisVO> aiSuggest(@Valid @RequestBody AiAnalysisRequestDTO request) {
        return Result.success(aiAnalysisService.analyze(request.getRecordId()));
    }
}
