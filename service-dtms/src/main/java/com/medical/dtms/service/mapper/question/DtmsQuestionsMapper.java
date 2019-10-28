package com.medical.dtms.service.mapper.question;

import java.util.List;

import com.medical.dtms.common.model.question.DtmsQuestionsModel;
import org.springframework.stereotype.Repository;

import com.medical.dtms.common.model.question.DtmsQuestionListModel;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.service.dataobject.question.DtmsQuestionsDO;

/**
 * 试题管理
 * 
 * @author shenqifeng
 * @version $Id: DtmsQuestionsMapper.java, v 0.1 2019年8月27日 下午5:24:29 shenqifeng Exp $
 */
@Repository
public interface DtmsQuestionsMapper {
    /**
     * 新增试题
     * 
     * @param questionsDO
     * @return
     */
    Integer saveQuestion(DtmsQuestionsDO questionsDO);

    /**
     *批量插入
     * 
     * @param list
     * @return
     */
    Integer saveBatchQuestion(List<DtmsQuestionsDO> list);

    /**
     * 修改试题
     * 
     * @param questionsDO
     * @return
     */
    Integer updateQuestion(DtmsQuestionsDO questionsDO);

    /**
     * 查询试题详情
     * 
     * @param bizId
     * @return
     */
    DtmsQuestionsDO getQuestionById(Long bizId);

    /**
     * 列表查询试题
     * 
     * @param query
     * @return
     */
    List<DtmsQuestionListModel> listQuestionsByQuery(QuestionQuery query);

    /**
     * 查询试卷问题
     * 
     * @param query
     * @return
     */
    List<DtmsQuestionListModel> listQuestionByQueryForExam(QuestionQuery query);

    /**
     * 查询试卷问题
     * 
     * @param query
     * @return
     */
    List<DtmsQuestionListModel> listQuestionByQueryForExamNotChose(QuestionQuery query);

    /**
     * 查询预览试题
     * 
     * @param query
     * @return
     */
    List<DtmsQuestionsDO> listQuestionsForPreview(QuestionQuery query);

    List<DtmsQuestionsDO> listQuestionsByExamId(Long examId);
}