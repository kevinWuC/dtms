package com.medical.dtms.service.mapper.file;

import com.medical.dtms.common.model.file.ApplyCheckModel;
import com.medical.dtms.common.model.file.FileApplyDetailModel;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.service.dataobject.file.FileApplyDO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileApplyDO record);

    FileApplyDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileApplyDO record);

    List<ApplyCheckModel> pageListApplyCheck(FileModelQuery query);

    FileApplyDetailModel selectById(Long id);
}