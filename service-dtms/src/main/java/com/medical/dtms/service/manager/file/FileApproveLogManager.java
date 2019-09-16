package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.model.file.FileApproveLogModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileApproveLogDTO;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.service.dataobject.file.FileApproveLogDO;
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
    public Integer insert(FileApproveLogDTO logDTO) {
        FileApproveLogDO aDo = BeanConvertUtils.convert(logDTO, FileApproveLogDO.class);
        return logMapper.insert(aDo);
    }

    /**
     * 更新 流程记录
     */
    public Integer updateLog(FileApproveLogDTO logDTO) {
        FileApproveLogDO aDo = BeanConvertUtils.convert(logDTO, FileApproveLogDO.class);
        return logMapper.updateByPrimaryKeySelective(aDo);
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
