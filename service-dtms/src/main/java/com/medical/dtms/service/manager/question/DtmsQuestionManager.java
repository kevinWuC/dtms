/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.service.manager.question;

import java.util.Date;
import java.util.List;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.qms.QMSSysLogsMapper;
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
    private SysLoginLogManager loginLogManager;
    @Autowired
    private QMSSysLogsMapper qmsSysLogsMapper;
    @Autowired
    private LogClient logClient;

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
            qmsSysLogsDO.setOperationIp(loginLogManager.getIpByUserId(String.valueOf(questionsDO.getCreateUserId())));
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
        DtmsQuestionsDO newDo = BeanConvertUtils.convert(questionsDTO, DtmsQuestionsDO.class);
        //查询没更新之前的信息
        DtmsQuestionsDO oldDo = questionMapper.getQuestionById(questionsDTO.getBizId());

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                questionsDTO.getModifyUserName(),
                // 操作类型
                questionsDTO.getDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : questionsDTO.getDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(String.valueOf(questionsDTO.getModifyUserId())),
                // 备注，这里是操作模块名
                "试题管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        Integer num = questionMapper.updateQuestion(newDo);
        return num > 0 ? true : false;
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
