package com.medical.dtms.service.mapper.file;

import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.dto.file.query.FileInDeptRoleQuery;
import com.medical.dtms.service.dataobject.file.FileInDeptRoleDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileQueryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileInDeptRoleDO record);

    FileInDeptRoleDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileInDeptRoleDO record);

    List<FileInDeptRoleDO> listFileInDeptRole(FileInDeptRoleQuery query);;

    Integer deleteByIds(List<Long> bizIds);

    Integer batchInsert(List<FileInDeptRoleDO> dos);

    List<DropDownModel> listReceiveDept(DropDownQuery query);

    List<DropDownModel> listRolesInFileModel(DropDownQuery query);
}