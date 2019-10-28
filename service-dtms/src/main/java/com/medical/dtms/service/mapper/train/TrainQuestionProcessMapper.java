package com.medical.dtms.service.mapper.train;

import com.medical.dtms.common.model.train.MyTrainSubmitModel;
import com.medical.dtms.common.model.train.query.TrainSubmitAnswerQuery;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import com.medical.dtms.service.dataobject.train.TrainQuestionProcessDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainQuestionProcessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TrainQuestionProcessDO record);

    int insertSelective(TrainQuestionProcessDO record);

    TrainQuestionProcessDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TrainQuestionProcessDO record);

    int updateByPrimaryKey(TrainQuestionProcessDO record);

    /**
     * 批量更新
     *
     * @param dos
     * @return
     */
    int updateBatchTrainQuestionProcess(List<TrainQuestionProcessDO> dos);

    TrainQuestionProcessDO checkFirstExamOrNot(TrainSubmitAnswerQuery query);

    Integer batchInsertInfo(List<TrainQuestionProcessDO> processDTOList);

    Integer batchUpdateTrainQuestionProcess(MyTrainSubmitModel model);
}