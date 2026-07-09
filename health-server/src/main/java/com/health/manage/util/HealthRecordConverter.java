package com.health.manage.util;

import com.health.manage.dto.HealthRecordDTO;
import com.health.manage.entity.HealthRecord;
import com.health.manage.vo.HealthRecordVO;
import org.springframework.beans.BeanUtils;

/**
 * 健康记录对象转换工具
 */
public final class HealthRecordConverter {

    private HealthRecordConverter() {
    }

    public static HealthRecordVO toVO(HealthRecord entity) {
        if (entity == null) {
            return null;
        }
        HealthRecordVO vo = new HealthRecordVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static HealthRecord toEntity(HealthRecordDTO dto) {
        if (dto == null) {
            return null;
        }
        HealthRecord entity = new HealthRecord();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
