package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.file.FileApproveLogModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileApproveLogDTO;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.file.FileApproveLogDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.file.FileApproveLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileApproveLogManager.java v 1.0, 2019年08月27日 16:12 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileApproveLogManager {

    @Autowired
    private FileApproveLogMapper logMapper;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private LogClient logClient;

    /**
     * 批量添加流程记录
     */
    public Integer batchInsert(List<FileApproveLogDTO> logList) {
        List<FileApproveLogDO> dos = BeanConvertUtils.convertList(logList, FileApproveLogDO.class);
        return logMapper.batchInsert(dos);
    }

    /**
     * 文件报批 - 流程记录（分页查询）
     */
    public List<FileApproveLogModel> pageListLogInfo(FileApproveLogQuery query) {
        return logMapper.pageListLogInfo(query);
    }

    /**
     * 根据文件删除记录
     */
    public Integer deleteByFileId(Long fileId) {
        return logMapper.deleteByFileId(fileId);
    }

    /**
     * 查询文件记录
     */
    public List<FileApproveLogDTO> listLogs(FileApproveLogQuery query) {
        List<FileApproveLogDO> dos = logMapper.listLogs(query);
        return BeanConvertUtils.convertList(dos, FileApproveLogDTO.class);
    }

    /**
     * 添加流程记录
     */
    public Integer insert(FileApproveLogDTO dto) {
        FileApproveLogDO newDo = BeanConvertUtils.convert(dto, FileApproveLogDO.class);

        FileApproveLogDO oldDo = new FileApproveLogDO();
        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_INSERT.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "添加流程信息",
                // 旧值
                oldDo,
                // 新值
                newDo
        );
        return logMapper.insert(newDo);
    }

    /**
     * 更新 流程记录
     */
    public Integer updateLog(FileApproveLogDTO logDTO) {
        FileApproveLogDO newDo = BeanConvertUtils.convert(logDTO, FileApproveLogDO.class);

        FileApproveLogDO oldDo = logMapper.selectByPrimaryKey(logDTO.getId());

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                logDTO.getModifier(),
                // 操作类型
                logDTO.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : logDTO.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(logDTO.getModifierId()),
                // 备注，这里是操作模块名
                "更新流程信息",
                // 旧值
                oldDo,
                // 新值
                newDo
        );
        return logMapper.updateByPrimaryKeySelective(newDo);
    }

    /**
     * 批量更新
     */
    public Integer updateLogByIds(FileApproveLogDTO dto) {
        FileApproveLogDO dos = BeanConvertUtils.convert(dto, FileApproveLogDO.class);
        return logMapper.batchUpdate(dos);
    }

    /**
     * 查询当前操作人最近的两条流程记录
     */
    public List<FileApproveLogDTO> queryLastTwoLog(FileApproveLogQuery query) {
        List<FileApproveLogDO> dos = logMapper.queryLastTwoLog(query);
        return BeanConvertUtils.convertList(dos, FileApproveLogDTO.class);
    }

}
