package com.medical.dtms.service.manager.exam;

import java.util.List;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
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
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private LogClient logClient;
    @Autowired
    private SysLoginLogManager loginLogManager;

    /**
     * 删除
     *
     * @param examPlanModelId
     * @return
     */
    public Boolean deleteExamPlanModelByPlanModeID(ExamPlanModelDTO dto) {

        ExamPlanModelDO oldDo = examPlanModelMapper.getExamPlanModelById(dto.getExamPlanModelId());

        ExamPlanModelDO newDo = BeanConvertUtils.convert(dto, ExamPlanModelDO.class);
        newDo.setDeleted(true);

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getId()),
                // 操作人
                dto.getModifyUserName(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_DELETE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(String.valueOf(dto.getModifyUserId())),
                // 备注，这里是操作模块名
                "考试安排",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        Integer num = examPlanModelMapper.deleteExamPlanModelByPlanModeID(dto.getExamPlanModelId());
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
        ExamPlanModelDO newDo = BeanConvertUtils.convert(examPlanModelDTO,
                ExamPlanModelDO.class);

        ExamPlanModelDO oldDo = examPlanModelMapper.getExamPlanModelById(examPlanModelDTO.getExamPlanModelId());

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getExamPlanModelId()),
                // 操作人
                examPlanModelDTO.getModifyUserName(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(String.valueOf(examPlanModelDTO.getModifyUserId())),
                // 备注，这里是操作模块名
                "考试安排",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        Integer num = examPlanModelMapper.updateExamPlanModel(newDo);
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
