package com.medical.dtms.service.mapper.train;

import com.medical.dtms.service.dataobject.train.TrainQuestionProcessDO;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainQuestionProcessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TrainQuestionProcessDO record);

    int insertSelective(TrainQuestionProcessDO record);

    TrainQuestionProcessDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TrainQuestionProcessDO record);

    int updateByPrimaryKey(TrainQuestionProcessDO record);
}