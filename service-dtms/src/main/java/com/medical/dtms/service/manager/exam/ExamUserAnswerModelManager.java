package com.medical.dtms.service.manager.exam;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamUserAnswerModelDTO;
import com.medical.dtms.service.dataobject.exam.ExamUserAnswerModelDo;
import com.medical.dtms.service.mapper.exam.ExamUserAnswerModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： ExamUserAnswerModelManager.java v 1.0, 2019年09月11日 21:19 huangshuaiquan Exp$
 * @Description
 **/
@Service
public class ExamUserAnswerModelManager {

    @Autowired
    ExamUserAnswerModelMapper answerModelMapper;

    /**
     * 批量新增
     *
     * @param dtos
     * @return
     */
    public Boolean insertBatchExamUserAnswerModel(List<ExamUserAnswerModelDTO> dtos){
        List<ExamUserAnswerModelDo> dos = BeanConvertUtils.convertList(dtos, ExamUserAnswerModelDo.class);
        int num = answerModelMapper.insertBatchExamUserAnswerModel(dos);
        return num > 0 ? true : false;
    }


    /**
     * 批量更新
     *
     * @param dtos
     * @return
     */
    public Boolean updateBatchExamUserAnswerModel(List<ExamUserAnswerModelDTO> dtos){
        List<ExamUserAnswerModelDo> dos = BeanConvertUtils.convertList(dtos, ExamUserAnswerModelDo.class);
        int num = answerModelMapper.updateBatchExamUserAnswerModel(dos);
        return num > 0 ? true : false;
    }


}
