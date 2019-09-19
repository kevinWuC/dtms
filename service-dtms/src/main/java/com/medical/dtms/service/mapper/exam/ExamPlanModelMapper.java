package com.medical.dtms.service.mapper.exam;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.medical.dtms.dto.exam.ExamPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.service.dataobject.exam.ExamPlanModelDO;

@Repository
public interface ExamPlanModelMapper {

    /**
     * 删除
     *
     * @param examPlanModelId
     * @return
     */
    Integer  deleteExamPlanModelByPlanModeID(@Param("examPlanModelId") Long examPlanModelId);
    /**
     * 新增
     * 
     * @param examPlanModelDO
     * @return
     */
    Integer insertExamPlanModel(ExamPlanModelDO examPlanModelDO);

    /**
     * 修改
     * 
     * @param examPlanModelDO
     * @return
     */
    Integer updateExamPlanModel(ExamPlanModelDO examPlanModelDO);

    /**
     * 查询详情
     * 
     * @param examPlanModelId
     * @return
     */
    ExamPlanModelDO getExamPlanModelById(@Param("examPlanModelId") Long examPlanModelId);

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    List<ExamPlanModelDTO> listExamPlanModelByQuery(ExamPlanModelQuery query);
}