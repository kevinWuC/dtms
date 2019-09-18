package com.medical.dtms.service.manager.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.service.dataobject.exam.ExamUserPlanModelDO;
import com.medical.dtms.service.mapper.exam.ExamUserPlanModelMapper;

@Service
public class ExamUserPlanModelManager {
    @Autowired
    private ExamUserPlanModelMapper examUserPlanModelMapper;

    /**
     * 批量新增
     * 
     * @param list
     * @return
     */
    public Boolean insertBatchExamUserPlanModel(List<ExamUserPlanModelDTO> list) {
        List<ExamUserPlanModelDO> examUserPlanModelDOs = BeanConvertUtils.convertList(list,
            ExamUserPlanModelDO.class);
        Integer num = examUserPlanModelMapper.insertBatchExamUserPlanModel(examUserPlanModelDOs);
        return num > 0 ? true : false;
    }

    /**
     * 修改
     * 
     * @param examUserPlanModelDO
     * @return
     */
    public Boolean updateExamUserPlanModel(ExamUserPlanModelDTO examUserPlanModelDTO) {
        ExamUserPlanModelDO examUserPlanModelDO = BeanConvertUtils.convert(examUserPlanModelDTO,
            ExamUserPlanModelDO.class);
        Integer num = examUserPlanModelMapper.updateExamUserPlanModel(examUserPlanModelDO);
        return num > 0 ? true : false;
    }

    /**
     * 启动
     * 
     * @param examPlanId
     * @return
     */
    public Boolean updateStart(Long examPlanId) {
        Integer num = examUserPlanModelMapper.updateStart(examPlanId);
        return num > 0 ? true : false;
    }

    /**
     * 根据考试安排id  删除
     * 
     * @param examPlanId
     * @return
     */
    public Boolean deleteByExamPlanId(Long examPlanId) {
        Integer num = examUserPlanModelMapper.deleteByExamPlanId(examPlanId);
        return num > 0 ? true : false;
    }

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    public List<ExamUserPlanModelDTO> listExamUserPlanByQuery(ExamPlanModelQuery query) {
        return examUserPlanModelMapper.listExamUserPlanByQuery(query);
    }

    /**
     * 分页查询（批卷用）
     *
     * @param query
     * @return
     */
    public List<ExamUserPlanModelDTO> listExamUserPlanByQueryForMark(ExamPlanModelQuery query) {
        return examUserPlanModelMapper.listExamUserPlanByQueryForMark(query);
    }

    /**
     * 根据用户考试bizId查询用户考试信息
     *
     * @param bizId
     * @return
     */
    public ExamUserPlanModelDTO selectExamUserPlanModelByBizId(Long bizId){
        return examUserPlanModelMapper.selectExamUserPlanModelByBizId(bizId);
    }

}
