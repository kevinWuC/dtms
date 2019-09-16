package com.medical.dtms.service.mapper.file;

import com.medical.dtms.common.model.file.FileAttachmentModel;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.service.dataobject.file.FileAttachmentsDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileAttachmentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileAttachmentsDO record);

    FileAttachmentsDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileAttachmentsDO record);

    Integer batchInsertFiles(List<FileAttachmentsDO> dos);

    List<FileAttachmentModel> pageListFileAttach(FileModelQuery query);
}