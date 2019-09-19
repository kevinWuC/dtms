package com.medical.dtms.service.manager.exam;

import com.medical.dtms.common.model.train.UserExamInfoModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamUserAnswerModelDTO;
import com.medical.dtms.dto.exam.query.ExamUserAnswerModelQuery;
import com.medical.dtms.service.dataobject.exam.ExamUserAnswerModelDo;
import com.medical.dtms.service.mapper.exam.ExamUserAnswerModelMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
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
     * 通过条件获取用户答案
     *
     * @param userAnswerModelDo
     * @return
     */
    public ExamUserAnswerModelDTO getUserAnswerByCondition(ExamUserAnswerModelDo userAnswerModelDo){
        ExamUserAnswerModelDo userAnswerModelDo1 = answerModelMapper.getUserAnswerByCondition(userAnswerModelDo);
        return  BeanConvertUtils.convert(userAnswerModelDo1,ExamUserAnswerModelDTO.class);
    }

    /**
     * 通过条件获取试卷题目
     *
     * @param userAnswerModelDo
     * @return
     */
    public List<ExamUserAnswerModelDTO> getUserAnswerListByCondition(ExamUserAnswerModelDo userAnswerModelDo){
        List<ExamUserAnswerModelDo> list = answerModelMapper.getUserAnswerListByCondition(userAnswerModelDo);
        return BeanConvertUtils.convertList(list,ExamUserAnswerModelDTO.class);
    }

    /**
     * 通过考试ID删除题目
     *
     * @param examPlanId
     * @return
     */
    public Boolean deleteByExamPlanId(@Param("examPlanId")Long examPlanId){
        int num = answerModelMapper.deleteByExamUserPlanId(examPlanId);
        return num > 0 ? true : false;
    }

    /**
     * 批量新增
     *
     * @param dtos
     * @return
     */
    public Boolean insertBatchExamUserAnswerModel(List<ExamUserAnswerModelDTO> dtos) {
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
    public Boolean updateBatchExamUserAnswerModel(List<ExamUserAnswerModelDTO> dtos) {
        List<ExamUserAnswerModelDo> dos = BeanConvertUtils.convertList(dtos, ExamUserAnswerModelDo.class);
        int num = answerModelMapper.updateBatchExamUserAnswerModel(dos);
        return num > 0 ? true : false;
    }

    /**
     * 根据用户 id 和 试卷ID 查询用户答题情况
     */
    public List<UserExamInfoModel> listExamInfo(ExamUserAnswerModelQuery query) {
        return answerModelMapper.listExamInfo(query);
    }


}
