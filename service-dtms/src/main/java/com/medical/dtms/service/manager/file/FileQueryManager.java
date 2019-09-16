package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileQueryDTO;
import com.medical.dtms.dto.file.query.FileInDeptRoleQuery;
import com.medical.dtms.service.dataobject.file.FileInDeptRoleDO;
import com.medical.dtms.service.mapper.file.FileQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileInDeptRoleManager.java v 1.0, 2019年08月28日 11:28 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileQueryManager {

    @Autowired
    private FileQueryMapper deptRoleMapper;

    /**
     * 校验文件 与部门、角色是否关联
     */
    public List<FileQueryDTO> listFileInDeptRole(FileInDeptRoleQuery query) {
        List<FileInDeptRoleDO> dos = deptRoleMapper.listFileInDeptRole(query);
        return BeanConvertUtils.convertList(dos, FileQueryDTO.class);
    }

    /**
     * 根据id 删除数据
     */
    public Integer deleteByIds(List<Long> bizIds) {
        return deptRoleMapper.deleteByIds(bizIds);
    }

    /**
     * 批量新增数据
     */
    public Integer batchInsert(List<FileInDeptRoleDO> dtoList) {
        List<FileInDeptRoleDO> dos = BeanConvertUtils.convertList(dtoList, FileInDeptRoleDO.class);
        return deptRoleMapper.batchInsert(dos);
    }

    /**
     * 部门下拉
     */
    public List<DropDownModel> listReceiveDept(DropDownQuery query) {
        return deptRoleMapper.listReceiveDept(query);
    }

    /**
     * 角色下拉
     */
    public List<DropDownModel> listRolesInFileModel(DropDownQuery query) {
        return deptRoleMapper.listRolesInFileModel(query);
    }
}
