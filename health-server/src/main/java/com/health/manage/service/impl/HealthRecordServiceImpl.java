package com.health.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.health.manage.dto.HealthRecordDTO;
import com.health.manage.dto.HealthRecordQueryDTO;
import com.health.manage.entity.HealthRecord;
import com.health.manage.exception.BusinessException;
import com.health.manage.mapper.HealthRecordMapper;
import com.health.manage.service.HealthRecordService;
import com.health.manage.util.HealthRecordConverter;
import com.health.manage.vo.HealthRecordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 健康记录 Service 实现
 */
@Service
@RequiredArgsConstructor
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord>
        implements HealthRecordService {

    @Override
    public IPage<HealthRecordVO> pageQuery(HealthRecordQueryDTO query) {
        Page<HealthRecord> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<HealthRecord> wrapper = buildQueryWrapper(query);
        wrapper.orderByDesc(HealthRecord::getRecordTime);
        IPage<HealthRecord> recordPage = this.page(page, wrapper);
        return recordPage.convert(HealthRecordConverter::toVO);
    }

    @Override
    public HealthRecordVO getDetailById(Long id) {
        HealthRecord record = getRecordOrThrow(id);
        return HealthRecordConverter.toVO(record);
    }

    @Override
    public Long add(HealthRecordDTO dto) {
        HealthRecord record = HealthRecordConverter.toEntity(dto);
        calculateBmi(record);
        this.save(record);
        return record.getId();
    }

    @Override
    public void update(HealthRecordDTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException("修改时ID不能为空");
        }
        getRecordOrThrow(dto.getId());
        HealthRecord record = HealthRecordConverter.toEntity(dto);
        calculateBmi(record);
        this.updateById(record);
    }

    @Override
    public void deleteById(Long id) {
        getRecordOrThrow(id);
        this.removeById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BusinessException("批量删除ID列表不能为空");
        }
        this.removeByIds(ids);
    }

    private HealthRecord getRecordOrThrow(Long id) {
        HealthRecord record = super.getById(id);
        if (record == null) {
            throw new BusinessException("健康记录不存在，ID: " + id);
        }
        return record;
    }

    /**
     * 根据身高体重自动计算 BMI
     */
    private void calculateBmi(HealthRecord record) {
        if (record.getHeight() != null && record.getWeight() != null
                && record.getHeight().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal heightM = record.getHeight().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
            BigDecimal bmi = record.getWeight().divide(heightM.multiply(heightM), 2, RoundingMode.HALF_UP);
            record.setBmi(bmi);
        }
    }

    private LambdaQueryWrapper<HealthRecord> buildQueryWrapper(HealthRecordQueryDTO query) {
        LambdaQueryWrapper<HealthRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(query.getMinHeight() != null, HealthRecord::getHeight, query.getMinHeight())
                .le(query.getMaxHeight() != null, HealthRecord::getHeight, query.getMaxHeight())
                .ge(query.getMinWeight() != null, HealthRecord::getWeight, query.getMinWeight())
                .le(query.getMaxWeight() != null, HealthRecord::getWeight, query.getMaxWeight())
                .ge(query.getMinBmi() != null, HealthRecord::getBmi, query.getMinBmi())
                .le(query.getMaxBmi() != null, HealthRecord::getBmi, query.getMaxBmi())
                .ge(query.getMinHeartRate() != null, HealthRecord::getHeartRate, query.getMinHeartRate())
                .le(query.getMaxHeartRate() != null, HealthRecord::getHeartRate, query.getMaxHeartRate())
                .like(StringUtils.hasText(query.getBloodPressure()), HealthRecord::getBloodPressure, query.getBloodPressure())
                .like(StringUtils.hasText(query.getDietHabit()), HealthRecord::getDietHabit, query.getDietHabit())
                .ge(query.getRecordTimeStart() != null, HealthRecord::getRecordTime, query.getRecordTimeStart())
                .le(query.getRecordTimeEnd() != null, HealthRecord::getRecordTime, query.getRecordTimeEnd());
        return wrapper;
    }
}
