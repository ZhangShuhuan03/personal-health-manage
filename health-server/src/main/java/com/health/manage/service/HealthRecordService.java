package com.health.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.health.manage.dto.HealthRecordDTO;
import com.health.manage.dto.HealthRecordQueryDTO;
import com.health.manage.entity.HealthRecord;
import com.health.manage.vo.HealthRecordVO;

import java.util.List;

/**
 * 健康记录 Service
 */
public interface HealthRecordService extends IService<HealthRecord> {

    IPage<HealthRecordVO> pageQuery(HealthRecordQueryDTO query);

    HealthRecordVO getDetailById(Long id);

    Long add(HealthRecordDTO dto);

    void update(HealthRecordDTO dto);

    void deleteById(Long id);

    void deleteBatch(List<Long> ids);
}
