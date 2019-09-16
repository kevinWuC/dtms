package com.medical.dtms.service.mapper.file;

import com.medical.dtms.common.model.file.FileApproveLogModel;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.service.dataobject.file.FileApproveLogDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileApproveLogMapper {
    int deleteByPrimaryKey(Long id);

    FileApproveLogDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileApproveLogDO record);

    Integer batchInsert(List<FileApproveLogDO> dos);

    List<FileApproveLogModel> pageListLogInfo(FileApproveLogQuery query);

    Integer deleteByFileId(@Param("fileId") Long fileId);

    List<FileApproveLogDO> listLogs(FileApproveLogQuery query);

    Integer insert(FileApproveLogDO logDO);

    List<FileApproveLogDO> queryLastTwoLog(FileApproveLogQuery query);

    Integer batchUpdate(FileApproveLogDO logDO);
}