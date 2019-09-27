package com.medical.dtms.service.manager.exam;

import java.util.List;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
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
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private LogClient logClient;
    @Autowired
    private SysLoginLogManager loginLogManager;


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
     * 重新安排考试（补考）
     *
     * @param list
     * @return
     */
    public Boolean insertBatchExamUserPlanModelForAfresh(List<ExamUserPlanModelDTO> list) {
        List<ExamUserPlanModelDO> examUserPlanModelDOs = BeanConvertUtils.convertList(list,
                ExamUserPlanModelDO.class);
        Integer num = examUserPlanModelMapper.insertBatchExamUserPlanModelForAfresh(examUserPlanModelDOs);
        return num > 0 ? true : false;
    }

    /**
     * 修改
     *
     * @param examUserPlanModelDO
     * @return
     */
    public Boolean updateExamUserPlanModel(ExamUserPlanModelDTO dto) {
        ExamUserPlanModelDO newDo = BeanConvertUtils.convert(dto,
                ExamUserPlanModelDO.class);

        ExamUserPlanModelDO oldDo = examUserPlanModelMapper.selectByPrimaryKey(dto.getExamUserPlanModelId());

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getExamUserPlanModelId()),
                // 操作人
                dto.getModifyUserName(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(String.valueOf(dto.getModifyUserId())),
                // 备注，这里是操作模块名
                "开始考试",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        Integer num = examUserPlanModelMapper.updateExamUserPlanModel(newDo);
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
    public ExamUserPlanModelDTO selectExamUserPlanModelByBizId(Long bizId) {
        return examUserPlanModelMapper.selectExamUserPlanModelByBizId(bizId);
    }

    /**
     * 根据用户ID和考试ID删除用户和考试的关联
     *
     * @param dtos
     * @return
     */
    public Boolean deleteBatchByCondition(List<ExamUserPlanModelDTO> dtos) {
        List<ExamUserPlanModelDO> dos = BeanConvertUtils.convertList(dtos, ExamUserPlanModelDO.class);
        Integer num = examUserPlanModelMapper.deleteBatchByCondition(dos);
        return num > 0 ? true : false;
    }

}
