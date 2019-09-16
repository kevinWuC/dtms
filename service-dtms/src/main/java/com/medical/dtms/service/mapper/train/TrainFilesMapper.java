package com.medical.dtms.service.mapper.train;

import com.medical.dtms.common.model.train.SimpleTrainFileModel;
import com.medical.dtms.dto.train.query.TrainFileQuery;
import com.medical.dtms.service.dataobject.train.TrainFilesDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version： TrainConfigManager.java v 1.0, 2019年08月29日 13:53 huangshuaiquan Exp$
 * @Description
 **/
@Repository
public interface TrainFilesMapper {

    int deleteByPrimaryKey(@Param("bizId")Long id);

    int insert(List<TrainFilesDO> dos);

    int insertSelective(TrainFilesDO record);

    TrainFilesDO selectByPrimaryKey(@Param("bizId")Long id);

    int updateByPrimaryKeySelective(TrainFilesDO record);

    int updateByPrimaryKey(TrainFilesDO record);

    int deleteByCondition(TrainFilesDO record);

    List<TrainFilesDO> listTrainFiles(TrainFileQuery query);

    int deleteByPrimaryKeyList(List<Long> bizIds);

    List<SimpleTrainFileModel> listFileInfoByTrainIds(List<Long> trainIds);

}