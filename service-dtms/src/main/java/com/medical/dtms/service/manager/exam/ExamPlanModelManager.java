package com.medical.dtms.service.manager.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.service.dataobject.exam.ExamPlanModelDO;
import com.medical.dtms.service.mapper.exam.ExamPlanModelMapper;

/**
 * 考试安排
 * 
 * @author shenqifeng
 * @version $Id: ExamPlanModelManager.java, v 0.1 2019年9月9日 下午10:26:28 shenqifeng Exp $
 */
@Service
public class ExamPlanModelManager {
    @Autowired
    private ExamPlanModelMapper examPlanModelMapper;

    /**
     * 新增
     *
     * @param examPlanModelId
     * @return
     */
    public Boolean deleteExamPlanModelByPlanModeID(Long examPlanModelId) {
        Integer num = examPlanModelMapper.deleteExamPlanModelByPlanModeID(examPlanModelId);
        return num > 0 ? true : false;
    }

    /**
     * 新增
     * 
     * @param examPlanModelDTO
     * @return
     */
    public Boolean insertExamPlanModel(ExamPlanModelDTO examPlanModelDTO) {
        ExamPlanModelDO examPlanModelDO = BeanConvertUtils.convert(examPlanModelDTO,
            ExamPlanModelDO.class);
        Integer num = examPlanModelMapper.insertExamPlanModel(examPlanModelDO);
        return num > 0 ? true : false;
    }

    /**
     * 修改
     * 
     * @param examPlanModelDTO
     * @return
     */
    public Boolean updateExamPlanModel(ExamPlanModelDTO examPlanModelDTO) {
        ExamPlanModelDO examPlanModelDO = BeanConvertUtils.convert(examPlanModelDTO,
            ExamPlanModelDO.class);
        Integer num = examPlanModelMapper.updateExamPlanModel(examPlanModelDO);
        return num > 0 ? true : false;
    }

    /**
     * 查询详情
     * 
     * @param examPlanModelId
     * @return
     */
    public ExamPlanModelDTO getExamPlanModelById(Long examPlanModelId) {
        return BeanConvertUtils.convert(examPlanModelMapper.getExamPlanModelById(examPlanModelId),
            ExamPlanModelDTO.class);
    }

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    public List<ExamPlanModelDTO> listExamPlanModelByQuery(ExamPlanModelQuery query) {
        return examPlanModelMapper.listExamPlanModelByQuery(query);
    }
}
