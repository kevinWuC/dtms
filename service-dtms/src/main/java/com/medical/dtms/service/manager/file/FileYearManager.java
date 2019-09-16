package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.model.file.FileYearRecordModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileYearDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.service.dataobject.file.FileYearDO;
import com.medical.dtms.service.mapper.file.FileYearMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileYearManager.java v 1.0, 2019年08月28日 18:29 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileYearManager {

    @Autowired
    private FileYearMapper yearMapper;

    /**
     * 新增年审记录
     */
    public int insert(FileYearDTO dto) {
        FileYearDO aDo = BeanConvertUtils.convert(dto, FileYearDO.class);
        return yearMapper.insert(aDo);
    }

    /**
     * 年审记录（分页展示）
     */
    public List<FileYearRecordModel> pageListFileYearRecord(FileModelQuery query) {
        return yearMapper.pageListFileYearRecord(query);
    }

}
