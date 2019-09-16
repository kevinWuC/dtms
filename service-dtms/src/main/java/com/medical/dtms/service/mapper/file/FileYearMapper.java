package com.medical.dtms.service.mapper.file;

import com.medical.dtms.common.model.file.FileYearRecordModel;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.service.dataobject.file.FileYearDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileYearMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileYearDO record);

    FileYearDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileYearDO record);

    List<FileYearRecordModel> pageListFileYearRecord(FileModelQuery query);

}