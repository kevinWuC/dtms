package com.medical.dtms.service.manager.train;

import com.medical.dtms.common.model.train.UserExamInfoModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamUserAnswerModelDTO;
import com.medical.dtms.dto.exam.query.ExamUserAnswerModelQuery;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import com.medical.dtms.service.dataobject.exam.ExamUserAnswerModelDo;
import com.medical.dtms.service.dataobject.train.TrainQuestionProcessDO;
import com.medical.dtms.service.mapper.exam.ExamUserAnswerModelMapper;
import com.medical.dtms.service.mapper.train.TrainQuestionProcessMapper;
import org.apache.ibatis.annotations.Param;
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




}
