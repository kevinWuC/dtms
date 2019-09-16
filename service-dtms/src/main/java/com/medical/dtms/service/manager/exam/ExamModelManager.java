package com.medical.dtms.service.manager.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medical.dtms.common.model.exam.ExamDetailModel;
import com.medical.dtms.common.model.exam.ExamPlanModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamModelDTO;
import com.medical.dtms.dto.exam.query.ExamQuery;
import com.medical.dtms.service.dataobject.exam.ExamModelDO;
import com.medical.dtms.service.mapper.exam.ExamModelMapper;

/**
 * 试卷
 * 
 * @author shenqifeng
 * @version $Id: ExamModelMapper.java, v 0.1 2019年9月7日 下午8:17:52 shenqifeng Exp $
 */
@Repository
public class ExamModelManager {
    @Autowired
    private ExamModelMapper examModelMapper;

    /**
     * 新增
     * 
     * @param examModelDO
     * @return
     */
    public Boolean insertExam(ExamModelDTO examModelDTO) {
        ExamModelDO examModelDO = BeanConvertUtils.convert(examModelDTO, ExamModelDO.class);
        Integer num = examModelMapper.insertExam(examModelDO);
        return num > 0 ? true : false;
    }

    /**
     * 修改
     * 
     * @param examModelDO
     * @return
     */
    public Boolean updateExam(ExamModelDTO examModelDTO) {
        ExamModelDO examModelDO = BeanConvertUtils.convert(examModelDTO, ExamModelDO.class);
        Integer num = examModelMapper.updateExam(examModelDO);
        return num > 0 ? true : false;
    }

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    public List<ExamModelDTO> listExamByQuery(ExamQuery query) {
        return examModelMapper.listExamByQuery(query);
    }

    /**
     * 查询详情
     * 
     * @param examId
     * @return
     */
    public ExamDetailModel getExamByExamId(Long examId) {
        return examModelMapper.getExamByExamId(examId);
    }

    /**
     * 删除
     * 
     * @param examId
     * @return
     */
    public Boolean deleteByExamId(Long examId) {
        Integer num = examModelMapper.deleteByExamId(examId);
        return num > 0 ? true : false;
    }

    /**
     * 查询所有试卷
     * 
     * @return
     */
    public List<ExamPlanModel> listExamForPlan() {
        return examModelMapper.listExamForPlan();
    }
}