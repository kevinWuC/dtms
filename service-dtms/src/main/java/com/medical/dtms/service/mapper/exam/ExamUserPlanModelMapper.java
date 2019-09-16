package com.medical.dtms.service.mapper.exam;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.service.dataobject.exam.ExamUserPlanModelDO;

@Repository
public interface ExamUserPlanModelMapper {
    /**
     * 批量新增
     * 
     * @param list
     * @return
     */
    Integer insertBatchExamUserPlanModel(List<ExamUserPlanModelDO> list);

    /**
     * 修改
     * 
     * @param examUserPlanModelDO
     * @return
     */
    Integer updateExamUserPlanModel(ExamUserPlanModelDO examUserPlanModelDO);

    /**
     * 启动
     * 
     * @param examPlanId
     * @return
     */
    Integer updateStart(@Param("examPlanId") Long examPlanId);

    /**
     * 根据考试安排id  删除
     * 
     * @param examPlanId
     * @return
     */
    Integer deleteByExamPlanId(@Param("examPlanId") Long examPlanId);

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    List<ExamUserPlanModelDTO> listExamUserPlanByQuery(ExamPlanModelQuery query);

    /**
     * 根据用户考试bizId查询用户考试信息
     *
     * @param bizId
     * @return
     */
    ExamUserPlanModelDTO selectExamUserPlanModelByBizId(@Param("examUserPlanModelId")Long bizId);

}