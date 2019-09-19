package com.medical.dtms.service.manager.train;

import com.medical.dtms.common.model.train.TrainConfigModel;
import com.medical.dtms.common.model.train.TrainConfigQueryModel;
import com.medical.dtms.common.model.train.TrainUserModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.query.TrainConfigQuery;
import com.medical.dtms.service.dataobject.train.TrainConfigDO;
import com.medical.dtms.service.mapper.train.TrainConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： TrainConfigManager.java v 1.0, 2019年08月29日 13:53 huangshuaiquan Exp$
 * @Description
 **/
@Service
public class TrainConfigManager {

    @Autowired
    private TrainConfigMapper trainConfigMapper;

    /**
     * 培训配置 - 新增
     **/
    public Integer insert(TrainConfigDTO trainConfigDTO) {
        TrainConfigDO aDo = BeanConvertUtils.convert(trainConfigDTO, TrainConfigDO.class);
        return trainConfigMapper.insert(aDo);
    }

    /**
     * 主键查询培训是否存在
     **/
    public TrainConfigDTO selectByPrimaryKey(Long bizId) {
        TrainConfigDO aDo = trainConfigMapper.selectByPrimaryKey(bizId);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, TrainConfigDTO.class);
    }

    /**
     * 主键查询培训信息
     **/
    public TrainConfigQueryModel selectTrainConfig(Long bizId) {
        return trainConfigMapper.selectTrainConfig(bizId);
    }

    /**
     * 根据培训名称确定唯一性
     **/
    public TrainConfigDTO getTrainConfigByTrainName(TrainConfigQuery query) {
        TrainConfigDO aDo = trainConfigMapper.getTrainConfigByTrainName(query);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, TrainConfigDTO.class);
    }

    /**
     * 培训配置 - 更新
     **/
    public Integer updateByPrimaryKeySelective(TrainConfigDTO dto) {
        TrainConfigDO aDo = BeanConvertUtils.convert(dto, TrainConfigDO.class);
        return trainConfigMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 培训配置 - 列表查询
     **/
    public List<TrainConfigModel> listTrainConfigs(TrainConfigQuery query) {
        return trainConfigMapper.listTrainConfigs(query);
    }

    /**
     * 培训配置 - 删除
     **/
    public Integer deleteByPrimaryKey(Long bizId) {
        return trainConfigMapper.deleteByPrimaryKey(bizId);
    }

    /**
     * 查询培训信息和试卷信息
     */
    public TrainUserModel queryTrainInfo(Long bizId) {
        return trainConfigMapper.queryTrainInfo(bizId);
    }

}
