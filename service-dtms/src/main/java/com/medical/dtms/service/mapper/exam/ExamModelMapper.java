package com.medical.dtms.service.mapper.exam;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.medical.dtms.common.model.exam.ExamDetailModel;
import com.medical.dtms.common.model.exam.ExamPlanModel;
import com.medical.dtms.dto.exam.ExamModelDTO;
import com.medical.dtms.dto.exam.query.ExamQuery;
import com.medical.dtms.service.dataobject.exam.ExamModelDO;

/**
 * 试卷
 * 
 * @author shenqifeng
 * @version $Id: ExamModelMapper.java, v 0.1 2019年9月7日 下午8:17:52 shenqifeng Exp $
 */
@Repository
public interface ExamModelMapper {
    /**
     * 新增
     * 
     * @param examModelDO
     * @return
     */
    Integer insertExam(ExamModelDO examModelDO);

    /**
     * 修改
     * 
     * @param examModelDO
     * @return
     */
    Integer updateExam(ExamModelDO examModelDO);

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    List<ExamModelDTO> listExamByQuery(ExamQuery query);

    /**
     * 查询详情
     * 
     * @param examId
     * @return
     */
    ExamDetailModel getExamByExamId(@Param("examId") Long examId);

    /**
     * 查询详情(返回实体类)
     *
     * @param examId
     * @return
     */
    ExamModelDO getExamByExamIds(@Param("examId") Long examId);

    /**
     * 删除
     * 
     * @param examId
     * @return
     */
    Integer deleteByExamId(@Param("examId") Long examId);

    /**
     * 查询所有试卷
     * 
     * @return
     */
    List<ExamPlanModel> listExamForPlan();
}