package com.medical.dtms.service.mapper.train;


import com.medical.dtms.common.model.train.TrainConfigModel;
import com.medical.dtms.common.model.train.TrainConfigQueryModel;
import com.medical.dtms.dto.train.query.TrainConfigQuery;
import com.medical.dtms.service.dataobject.train.TrainConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @version： TrainConfigManager.java v 1.0, 2019年08月29日 13:53 huangshuaiquan Exp$
 * @Description
 **/
@Repository
public interface TrainConfigMapper {

    int deleteByPrimaryKey(@Param("bizId")Long bizId);

    int insert(TrainConfigDO record);

    int insertSelective(TrainConfigDO record);

    TrainConfigDO selectByPrimaryKey(@Param("bizId") Long bizId);

    int updateByPrimaryKeySelective(TrainConfigDO record);

    int updateByPrimaryKey(TrainConfigDO record);

    TrainConfigDO getTrainConfigByTrainName(TrainConfigQuery query);

    List<TrainConfigModel> listTrainConfigs(TrainConfigQuery query);

    TrainConfigQueryModel selectTrainConfig(@Param("bizId") Long bizId);

}