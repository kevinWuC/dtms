package com.medical.dtms.service.manager.exam;

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
     * 新增
     * 
     * @param examModelDO
     * @return
     */
    public Boolean insertExam(ExamModelDTO examModelDTO) {
        ExamModelDO examModelDO = BeanConvertUtils.convert(examModelDTO, ExamModelDO.class);
        Integer num = examModelMapper.insertExam(examModelDO);
        if (num == 1){
            //新增修改日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_INSERT.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(examModelDO.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(examModelDO.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(examModelDO.getExamId()));
            qmsSysLogsDO.setOperationIp(sysLoginLogManager.getIpByUserId(String.valueOf(examModelDO.getCreateUserId())));
            qmsSysLogsDO.setGmtCreated(new Date());
            qmsSysLogsDO.setCreatorId(String.valueOf(examModelDO.getCreateUserId()));
            qmsSysLogsDO.setCreator(examModelDO.getCreateUserName());
            qmsSysLogsMapper.insert(qmsSysLogsDO);
        }
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
        //查询没更新之前的信息
        ExamModelDO examByExamIds = examModelMapper.getExamByExamIds(examModelDTO.getExamId());
        Integer num = examModelMapper.updateExam(examModelDO);
        if (num == 1){
            //新增修改日志记录(tb_dtms_qms_sys_logs)
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_UPDATE.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(examModelDO.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(examModelDO.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(examModelDO.getExamId()));
            qmsSysLogsDO.setOperationIp(sysLoginLogManager.getIpByUserId(String.valueOf(examModelDO.getModifyUserId())));
            qmsSysLogsDO.setGmtModified(new Date());
            qmsSysLogsDO.setModifierId(String.valueOf(examModelDO.getModifyUserId()));
            qmsSysLogsDO.setModifier(examModelDO.getModifyUserName());
            int insert = qmsSysLogsMapper.insert(qmsSysLogsDO);
            boolean b = addSysLogDetails(examByExamIds, examModelDO);
        }
        return num > 0 ? true : false;
    }

    /**
     * 新增日志明细
     * @param examByExamIds (旧)
     * @param examModelDO  （新）
     * @return
     */
    private boolean addSysLogDetails(ExamModelDO examByExamIds,ExamModelDO examModelDO){
        boolean isSuccess = false;
        if (!StringUtils.equals(examByExamIds.getExamName(),examModelDO.getExamName())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("ExamName");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(examModelDO.getExamName());
            qmsSysLogDetailsDO.setOldValue(examByExamIds.getExamName());
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(examModelDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(examModelDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (examByExamIds.getExamDuration() != examModelDO.getExamDuration()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("ExamDuration");
            /*qmsSysLogDetailsDO.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(examModelDO.getExamName());
            qmsSysLogDetailsDO.setOldValue(examByExamIds.getExamName());
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(examModelDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(examModelDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (examByExamIds.getExamDuration() != examModelDO.getExamDuration()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("ExamDuration");
            /*qmsSysLogDetailsDO.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(examModelDO.getExamDuration()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(examByExamIds.getExamDuration()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(examModelDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(examModelDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (examByExamIds.getTotalPoints() != examModelDO.getTotalPoints()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("TotalPoints");
            /*qmsSysLogDetailsDO.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(examModelDO.getTotalPoints()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(examByExamIds.getTotalPoints()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(examModelDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(examModelDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        /*if (examByExamIds.getIsUse() != examModelDO.getIsUse()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("IsUse");
            *//*qmsSysLogDetailsDO.setFieldText();*//*
            qmsSysLogDetailsDO.setNewValue(String.valueOf(examModelDO.getIsUse()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(examByExamIds.getIsUse()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(examModelDO.getModifyUserName());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(examModelDO.getCreateUserId()));
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }*/
        return isSuccess;

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
        ExamModelDO  examModelDO= examModelMapper.getExamByExamIds(examId);
        if (examModelDO != null){
            //新增修改日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_DELETE.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(examModelDO.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(examModelDO.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(examModelDO.getExamId()));
            qmsSysLogsDO.setOperationIp(sysLoginLogManager.getIpByUserId(String.valueOf(examModelDO.getModifyUserId())));
            qmsSysLogsDO.setGmtModified(new Date());
            qmsSysLogsDO.setModifierId(String.valueOf(examModelDO.getModifyUserId()));
            qmsSysLogsDO.setModifier(examModelDO.getModifyUserName());
            qmsSysLogsMapper.insert(qmsSysLogsDO);
        }
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