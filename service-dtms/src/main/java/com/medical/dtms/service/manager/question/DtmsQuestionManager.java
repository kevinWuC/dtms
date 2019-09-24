/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.service.manager.question;

import java.util.Date;
import java.util.List;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.service.dataobject.log.QMSSysLogDetailsDO;
import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.qms.QMSSysLogDetailsMapper;
import com.medical.dtms.service.mapper.qms.QMSSysLogsMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dtms.common.model.question.DtmsQuestionListModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.service.dataobject.question.DtmsQuestionsDO;
import com.medical.dtms.service.mapper.question.DtmsQuestionsMapper;

/**
 * 
 * @author shenqifeng
 * @version $Id: DtmsQuestionManager.java, v 0.1 2019年8月27日 下午9:33:16 shenqifeng Exp $
 */
@Service
public class DtmsQuestionManager {
    @Autowired
    private DtmsQuestionsMapper questionMapper;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager sysLoginLogManager;
    @Autowired
    private QMSSysLogsMapper qmsSysLogsMapper;
    @Autowired
    private QMSSysLogDetailsMapper qmsSysLogDetailsMapper;

    /**
     * 新增试题
     * 
     * @param questionsDO
     * @return
     */
    public Boolean saveQuestion(DtmsQuestionsDTO questionsDTO) {
        DtmsQuestionsDO questionsDO = BeanConvertUtils.convert(questionsDTO, DtmsQuestionsDO.class);
        Integer num = questionMapper.saveQuestion(questionsDO);
        if (num == 1){
            //新增修改日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_INSERT.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(questionsDO.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(questionsDO.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(questionsDO.getBizId()));
            qmsSysLogsDO.setOperationIp(sysLoginLogManager.getIpByUserId(String.valueOf(questionsDO.getCreateUserId())));
            qmsSysLogsDO.setGmtCreated(new Date());
            qmsSysLogsDO.setCreator(questionsDO.getCreateUserName());
            qmsSysLogsMapper.insert(qmsSysLogsDO);
        }
        return num > 0 ? true : false;
    }

    /**
     * 批量新增试题
     * 
     * @param questionsDO
     * @return
     */
    public Boolean saveBatchQuestion(List<DtmsQuestionsDTO> questionsDTOs) {
        List<DtmsQuestionsDO> questionsDOs = BeanConvertUtils.convertList(questionsDTOs,
            DtmsQuestionsDO.class);
        Integer num = questionMapper.saveBatchQuestion(questionsDOs);
        return num > 0 ? true : false;
    }

    /**
     * 修改试题
     * 
     * @param questionsDTO
     * @return
     */
    public Boolean updateQuestion(DtmsQuestionsDTO questionsDTO) {
        DtmsQuestionsDO questionsDO = BeanConvertUtils.convert(questionsDTO, DtmsQuestionsDO.class);
        //查询没更新之前的信息
        DtmsQuestionsDO questionById = questionMapper.getQuestionById(questionsDTO.getBizId());
        Integer num = questionMapper.updateQuestion(questionsDO);
        if (num == 1){
            //新增修改日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            if (questionsDO.getDeleted() ==true) {
                qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_DELETE.getType());
            }else {
                qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_UPDATE.getType());
            }
            qmsSysLogsDO.setTableName(operateManager.getTableName(questionsDO.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(questionsDO.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(questionsDO.getBizId()));
            qmsSysLogsDO.setOperationIp(sysLoginLogManager.getIpByUserId(String.valueOf(questionsDO.getCreateUserId())));
            qmsSysLogsDO.setGmtModified(new Date());
            qmsSysLogsDO.setCreator(questionsDO.getCreateUserName());
            if (questionsDO.getDeleted() == false){
                boolean b = addSysLogDetails(questionById, questionsDO);
            }
            qmsSysLogsMapper.insert(qmsSysLogsDO);
        }
        return num > 0 ? true : false;
    }

    private boolean addSysLogDetails(DtmsQuestionsDO questionById,DtmsQuestionsDO questionsDO){
        boolean isSuccess = false;
        if (questionById.getExamTypeId() != questionsDO.getExamTypeId()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("ExamTypeId");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(questionsDO.getExamTypeId()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(questionById.getExamTypeId()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setModifier(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (questionById.getQuestionsBankTypeId() != questionsDO.getQuestionsBankTypeId()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("QuestionsBankTypeId");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(questionsDO.getQuestionsBankTypeId()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(questionById.getQuestionsBankTypeId()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setModifier(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (questionById.getQuestionsTypeId() != questionsDO.getQuestionsTypeId()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("QuestionsTypeId");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(questionsDO.getQuestionsTypeId()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(questionById.getQuestionsTypeId()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setModifier(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (StringUtils.equals(questionById.getQuestionTitle(), questionsDO.getQuestionTitle())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("QuestionTitle");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(questionsDO.getQuestionTitle()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(questionById.getQuestionTitle()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setModifier(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (StringUtils.equals(questionById.getQuestionContent(), questionsDO.getQuestionContent())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("QuestionContent");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(questionsDO.getQuestionContent()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(questionById.getQuestionContent()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setModifier(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }

        if (StringUtils.equals(questionById.getAnswer(), questionsDO.getAnswer())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("Answer");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(questionsDO.getAnswer()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(questionById.getAnswer()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setModifier(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }

        if (StringUtils.equals(questionById.getUseDeptId(), questionsDO.getUseDeptId())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("UseDeptId");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(questionsDO.getUseDeptId()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(questionById.getUseDeptId()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setModifier(questionsDO.getModifyUserName());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(questionsDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        return isSuccess;

    }


    /**
     * 查询试题详情
     * 
     * @param bizId
     * @return
     */
    public DtmsQuestionsDTO getQuestionById(Long bizId) {
        DtmsQuestionsDTO questionsDTO = new DtmsQuestionsDTO();
        DtmsQuestionsDO questionsDO = questionMapper.getQuestionById(bizId);
        if (questionsDO != null) {
            questionsDTO = BeanConvertUtils.convert(questionsDO, DtmsQuestionsDTO.class);
        }
        return questionsDTO;

    }

    /**
     * 列表查询试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionListModel> listQuestionsByQuery(QuestionQuery query) {
        return questionMapper.listQuestionsByQuery(query);
    }

    /**
     * 查询试卷试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionListModel> listQuestionByQueryForExam(QuestionQuery query) {
        return questionMapper.listQuestionByQueryForExam(query);
    }

    /**
     * 查询试卷试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionListModel> listQuestionByQueryForExamNotChose(QuestionQuery query) {
        return questionMapper.listQuestionByQueryForExamNotChose(query);
    }

    /**
     * 查询预览试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionsDTO> listQuestionsForPreview(QuestionQuery query) {
        return BeanConvertUtils.convertList(questionMapper.listQuestionsForPreview(query),
            DtmsQuestionsDTO.class);
    }
}
