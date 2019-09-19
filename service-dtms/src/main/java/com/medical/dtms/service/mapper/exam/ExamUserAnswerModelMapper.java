package com.medical.dtms.service.mapper.exam;

import com.medical.dtms.common.model.train.UserExamInfoModel;
import com.medical.dtms.dto.exam.query.ExamUserAnswerModelQuery;
import com.medical.dtms.service.dataobject.exam.ExamUserAnswerModelDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version： ExamUserAnswerModelManager.java v 1.0, 2019年09月11日 21:19 huangshuaiquan Exp$
 * @Description
 **/
@Repository
public interface ExamUserAnswerModelMapper {
    /**
     * 通过条件获取用户答案
     *
     * @param record
     * @return
     */
    ExamUserAnswerModelDo getUserAnswerByCondition(ExamUserAnswerModelDo record);
    /**
     * 批量新增
     *
     * @param dos
     * @return
     */
    int insertBatchExamUserAnswerModel(List<ExamUserAnswerModelDo> dos);
    /**
     * 批量更新
     *
     * @param dos
     * @return
     */
    int updateBatchExamUserAnswerModel(List<ExamUserAnswerModelDo> dos);
    /**
     * 通过考试ID删除题目
     *
     * @param examPlanId
     * @return
     */
    int deleteByExamUserPlanId(@Param("examPlanId")Long examPlanId);
    /**
     * 通过条件删除用户答案
     *
     * @param record
     * @return
     */
    List<ExamUserAnswerModelDo> getUserAnswerListByCondition(ExamUserAnswerModelDo record);

    int deleteByPrimaryKey(Long id);

    int insert(ExamUserAnswerModelDo record);

    int insertSelective(ExamUserAnswerModelDo record);

    ExamUserAnswerModelDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ExamUserAnswerModelDo record);

    int updateByPrimaryKey(ExamUserAnswerModelDo record);

    List<UserExamInfoModel> listExamInfo(ExamUserAnswerModelQuery query);


}