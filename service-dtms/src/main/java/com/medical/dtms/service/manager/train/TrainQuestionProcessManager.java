package com.medical.dtms.service.manager.train;

import com.medical.dtms.common.model.train.MyTrainSubmitModel;
import com.medical.dtms.common.model.train.query.TrainSubmitAnswerQuery;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import com.medical.dtms.service.dataobject.train.TrainQuestionProcessDO;
import com.medical.dtms.service.mapper.train.TrainQuestionProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： ExamUserAnswerModelManager.java v 1.0,
 * @Description
 **/
@Service
public class TrainQuestionProcessManager {

    @Autowired
    TrainQuestionProcessMapper trainQuestionProcessMapper;


    /**
     * 批量更新
     *
     * @param dtos
     * @return
     */
    public Boolean updateBatchTrainQuestionProcess(List<TrainQuestionProcessDTO> dtos) {
        List<TrainQuestionProcessDO> dos = BeanConvertUtils.convertList(dtos, TrainQuestionProcessDO.class);
        int num = trainQuestionProcessMapper.updateBatchTrainQuestionProcess(dos);
        return num > 0 ? true : false;
    }

    /**
     * 校验是否是第一次考试
     */
    public TrainQuestionProcessDTO checkFirstExamOrNot(TrainSubmitAnswerQuery query) {
        TrainQuestionProcessDO processDO = trainQuestionProcessMapper.checkFirstExamOrNot(query);
        return BeanConvertUtils.convert(processDO, TrainQuestionProcessDTO.class);
    }

    public Integer batchInsertInfo(List<TrainQuestionProcessDTO> processDTOList){
        List<TrainQuestionProcessDO> processDOS = BeanConvertUtils.convertList(processDTOList, TrainQuestionProcessDO.class);
        return trainQuestionProcessMapper.batchInsertInfo(processDOS);
    }

    public Integer batchUpdateTrainQuestionProcess(MyTrainSubmitModel model){
        return trainQuestionProcessMapper.batchUpdateTrainQuestionProcess(model);
    }

}
