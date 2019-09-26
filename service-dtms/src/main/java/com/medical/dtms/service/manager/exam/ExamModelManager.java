package com.medical.dtms.service.manager.exam;

import java.util.Date;
import java.util.List;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.logclient.service.LogClient;
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
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;
    @Autowired
    private QMSSysLogsMapper qmsSysLogsMapper;
    @Autowired
    private QMSSysLogDetailsMapper qmsSysLogDetailsMapper;

    /**
     * 新增
     *
     * @param
     * @return
     */
    public Boolean insertExam(ExamModelDTO examModelDTO) {
        ExamModelDO examModelDO = BeanConvertUtils.convert(examModelDTO, ExamModelDO.class);
        Integer num = examModelMapper.insertExam(examModelDO);
        if (num == 1) {
            //新增修改日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_INSERT.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(examModelDO.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(examModelDO.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(examModelDO.getExamId()));
            qmsSysLogsDO.setOperationIp(loginLogManager.getIpByUserId(String.valueOf(examModelDO.getCreateUserId())));
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
     * @param
     * @return
     */
    public Integer updateExam(ExamModelDTO examModelDTO) {
        ExamModelDO newDo = BeanConvertUtils.convert(examModelDTO, ExamModelDO.class);
        //查询没更新之前的信息
        ExamModelDO oldDo = examModelMapper.getExamByExamIds(examModelDTO.getExamId());

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getId()),
                // 操作人
                examModelDTO.getModifyUserName(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(String.valueOf(examModelDTO.getModifyUserId())),
                // 备注，这里是操作模块名
                "文件管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );
        return examModelMapper.updateExam(newDo);
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
    public Boolean deleteByExamId(ExamModelDTO model) {
        ExamModelDO oldDo = examModelMapper.getExamByExamIds(model.getExamId());
        ExamModelDO newDo = BeanConvertUtils.convert(model, ExamModelDO.class);

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getId()),
                // 操作人
                model.getModifyUserName(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(String.valueOf(model.getModifyUserId())),
                // 备注，这里是操作模块名
                "文件管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        Integer num = examModelMapper.deleteByExamId(model.getExamId());
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