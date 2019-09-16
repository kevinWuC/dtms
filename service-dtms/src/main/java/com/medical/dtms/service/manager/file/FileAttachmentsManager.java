package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.model.file.FileAttachmentModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileAttachmentsDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.service.dataobject.file.FileAttachmentsDO;
import com.medical.dtms.service.mapper.file.FileAttachmentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileAttachmentsManager.java v 1.0, 2019年08月28日 11:01 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileAttachmentsManager {

    @Autowired
    private FileAttachmentsMapper attachmentsMapper;

    /**
     * 保存附件
     */
    public Integer batchInsertFiles(List<FileAttachmentsDTO> dtos) {
        List<FileAttachmentsDO> dos = BeanConvertUtils.convertList(dtos, FileAttachmentsDO.class);
        return attachmentsMapper.batchInsertFiles(dos);
    }

    /**
     * 查询文件附件
     */
    public List<FileAttachmentModel> pageListFileAttach(FileModelQuery query) {
        return attachmentsMapper.pageListFileAttach(query);
    }
}
