package com.medical.dtms.service.serviceimpl.file;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.enumeration.file.FileStatusEnum;
import com.medical.dtms.common.model.file.ArchiveOrInvalidModel;
import com.medical.dtms.common.model.file.ExportModel;
import com.medical.dtms.common.model.file.FileApproveLogModel;
import com.medical.dtms.common.model.file.FileQueryModel;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.FileQueryDTO;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.dto.file.query.FileInDeptRoleQuery;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileQueryService;
import com.medical.dtms.service.dataobject.file.FileInDeptRoleDO;
import com.medical.dtms.service.manager.file.FileApproveLogManager;
import com.medical.dtms.service.manager.file.FileModelManager;
import com.medical.dtms.service.manager.file.FileQueryManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version： FileInDeptRoleServiceImpl.java v 1.0, 2019年08月28日 11:27 wuxuelin Exp$
 * @Description 文件查询
 **/
@RestController
@Slf4j
public class FileQueryServiceImpl implements FileQueryService {

    @Autowired
    private FileQueryManager queryManager;
    @Autowired
    private FileModelManager fileModelManager;
    @Autowired
    private FileApproveLogManager logManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 新增文件的部门/角色授权
     **/
    @Override
    public Boolean addFileInDeptRole(@RequestBody FileQueryDTO dto) {
        // 校验是否重复授权
        FileInDeptRoleQuery query = new FileInDeptRoleQuery();
        query.setFileId(dto.getFileId());
        query.setRoleIdList(CollectionUtils.isEmpty(dto.getRoleIdList()) == true ? null : dto.getRoleIdList());
        query.setDeptIdList(CollectionUtils.isEmpty(dto.getDeptIdList()) == true ? null : dto.getDeptIdList());

        deleteDeptRoleRel(query);

        FileInDeptRoleDO roleDTO;
        List<FileInDeptRoleDO> dtoList = new ArrayList<>();
        // 部门授权
        if (CollectionUtils.isNotEmpty(dto.getDeptIdList())) {
            for (Long deptId : dto.getDeptIdList()) {
                roleDTO = new FileInDeptRoleDO();
                BeanUtils.copyProperties(dto, roleDTO);
                roleDTO.setBizId(idGenerator.nextId());
                roleDTO.setDeptId(deptId);
                dtoList.add(roleDTO);
            }
        }

        // 角色授权
        if (CollectionUtils.isNotEmpty(dto.getRoleIdList())) {
            for (Long roleId : dto.getRoleIdList()) {
                roleDTO = new FileInDeptRoleDO();
                BeanUtils.copyProperties(dto, roleDTO);
                roleDTO.setBizId(idGenerator.nextId());
                roleDTO.setRoleId(roleId);
                dtoList.add(roleDTO);
            }
        }

        // 保存数据
        try {
            queryManager.batchInsert(dtoList);
        } catch (Exception e) {
            log.error("保存文件与角色/部门关联数据失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 文件查询 - 删除记录
     **/
    @Override
    public Boolean deleteFileInFileQuery(@RequestBody FileQueryDTO dto) {
        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getBizId());
        FileModelDTO fileModelDTO = fileModelManager.getFileByCondition(query);
        if (null == fileModelDTO) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条记录已被删除，请勿重复操作！");
        }

        // 未删除，则需要删除与部门、角色的关联
        FileInDeptRoleQuery roleQuery = new FileInDeptRoleQuery();
        roleQuery.setFileId(dto.getBizId());

        deleteDeptRoleRel(roleQuery);

        // 删除流程记录
        FileApproveLogQuery logQuery = new FileApproveLogQuery();
        logQuery.setFileId(dto.getBizId());
        List<FileApproveLogModel> list = logManager.pageListLogInfo(logQuery);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                logManager.deleteByFileId(dto.getBizId());
            } catch (Exception e) {
                log.error("文件查询 删除流程记录失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        // 删除该条记录
        try {
            fileModelDTO.setIsDeleted(true);
            fileModelManager.updateFile(fileModelDTO);
        } catch (Exception e) {
            log.error("文件查询 删除记录失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.common.model.file.ExportModel>
     * @description 文件管理 - 文件查询 - 导出 - 查询文件列表
     **/
    @Override
    public List<ExportModel> exportFiles(@RequestBody FileModelQuery query) {
        // 不分页，但是需要根据条件查询，然后将结果导出
        query.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_3.getStatus());
        List<FileQueryModel> models = fileModelManager.exportFiles(query);
        if (CollectionUtils.isEmpty(models)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "无满足条件数据,请更换查询条件");
        }
        List<ExportModel> list = new ArrayList<>();
        ExportModel exportModel;
        for (FileQueryModel model : models) {
            exportModel = new ExportModel();
            BeanUtils.copyProperties(model, exportModel);
            exportModel.setEffectDate(model.getEffectDate() == null ? null : DateUtils.format(model.getEffectDate(), DateUtils.YYYY_MM_DD_HH_mm_ss));
            list.add(exportModel);
        }
        return list;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileQueryModel>
     * @description 文件管理 - 文件查询 - 分页展示
     **/
    @Override
    public PageInfo<FileQueryModel> pageListQueryFiles(@RequestBody FileModelQuery query) {
        query.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_3.getStatus());
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileQueryModel> models = fileModelManager.listFileQueryFiles(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileQueryModel>
     * @description 文件管理 - 我的文档 - 分页展示
     **/
    @Override
    public PageInfo<FileQueryModel> pageListMyFiles(@RequestBody FileModelQuery query) {
        // 角色id、部门id
        if (null != query.getRoleId() || null != query.getReceiveDeptId()) {
            FileInDeptRoleQuery roleQuery = new FileInDeptRoleQuery();
            if (null != query.getRoleId()) {
                List<Long> roleIdList = new ArrayList<>();
                roleIdList.add(query.getRoleId());
                roleQuery.setRoleIdList(roleIdList);
            }

            if (null != query.getReceiveDeptId()) {
                List<Long> deptIdList = new ArrayList<>();
                deptIdList.add(query.getReceiveDeptId());
                roleQuery.setDeptIdList(deptIdList);
            }

            List<FileQueryDTO> dtos = queryManager.listFileInDeptRole(roleQuery);
            if (CollectionUtils.isEmpty(dtos)) {
                return new PageInfo<>(new ArrayList<>());
            }
            List<Long> fileIds = dtos.stream().map(FileQueryDTO::getFileId).distinct().collect(Collectors.toList());
            query.setFileIds(fileIds);
        }

        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileQueryModel> models = fileModelManager.listFileQueryFiles(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * 删除 文件与部门、角色的关联
     */
    private void deleteDeptRoleRel(FileInDeptRoleQuery roleQuery) {
        List<FileQueryDTO> dtos = queryManager.listFileInDeptRole(roleQuery);
        if (CollectionUtils.isNotEmpty(dtos)) {
            // 删除文件与部门/角色的关联
            List<Long> bizIds = dtos.stream().map(FileQueryDTO::getBizId).distinct().collect(Collectors.toList());
            try {
                queryManager.deleteByIds(bizIds);
            } catch (Exception e) {
                log.error("删除文件与部门/角色的关联失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }
    }


}
