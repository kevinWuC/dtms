package com.medical.dtms.service.serviceimpl.dropdown;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.dropdown.DropDownDetailsModel;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.dto.item.QMSItemsDTO;
import com.medical.dtms.dto.item.query.QMSItemsQuery;
import com.medical.dtms.feignservice.dropdown.DropDownService;
import com.medical.dtms.service.manager.file.FileModelManager;
import com.medical.dtms.service.manager.file.FileQueryManager;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.item.QMSItemsManager;
import com.medical.dtms.service.manager.user.QMSUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： DropDownServiceImpl.java v 1.0, 2019年09月04日 11:38 wuxuelin Exp$
 * @Description 下拉框
 **/
@RestController
@Slf4j
public class DropDownServiceImpl implements DropDownService {

    @Autowired
    private FileModelManager modelManager;
    @Autowired
    private QMSUserManager userManager;
    @Autowired
    private FileQueryManager queryManager;
    @Autowired
    private QMSItemsManager itemsManager;
    @Autowired
    private QMSItemDetailsManager detailsManager;

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownDetailsModel>
     * @description 字典明细二级下拉框
     **/
    @Override
    public PageInfo<DropDownDetailsModel> listTypeDetailsName(@RequestBody DropDownQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownDetailsModel> models = detailsManager.listTypeDetailsName(query.getItemsId());
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param []
     * @return java.util.List<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 文件类别下拉框
     **/
    @Override
    public PageInfo<DropDownModel> listFileTypes(@RequestBody DropDownQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> models = modelManager.listFileTypes(query);
        return getDropDownModels(models);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 申请类别下拉框
     **/
    @Override
    public PageInfo<DropDownModel> listApplyTypesInFileModel(@RequestBody DropDownQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> models = modelManager.listApplyTypesInFileModel(query);
        return getDropDownModels(models);
    }

    /**
     * @param []
     * @return java.util.List<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 接收部门下拉框
     **/
    @Override
    public PageInfo<DropDownModel> listReceiveDept(@RequestBody DropDownQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> models = queryManager.listReceiveDept(query);
        return getDropDownModels(models);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 编写部门下拉
     **/
    @Override
    public PageInfo<DropDownModel> listApplyDeptInFileModel(@RequestBody DropDownQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> models = modelManager.listApplyDeptInFileModel(query);
        return getDropDownModels(models);
    }

    /**
     * @param []
     * @return java.util.List<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 人员列表
     **/
    @Override
    public PageInfo<DropDownModel> listUsers(@RequestBody DropDownQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> models = userManager.listUsers(query);
        return getDropDownModels(models);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 发布年度
     **/
    @Override
    public PageInfo<DropDownModel> listYears(@RequestBody DropDownQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> models = modelManager.listApplyYears(query);
        return getDropDownModels(models);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 分配角色 下拉
     **/
    @Override
    public List<DropDownModel> listRolesInFileModel(@RequestBody DropDownQuery query) {
        List<DropDownModel> models = queryManager.listRolesInFileModel(query);
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        return models;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 所有文件类别下拉
     **/
    @Override
    public PageInfo<DropDownModel> listAllFileTypes(@RequestBody DropDownQuery query) {
        QMSItemsQuery itemsQuery = new QMSItemsQuery();
        itemsQuery.setFullName("文件类别设置");
        QMSItemsDTO dto = itemsManager.getQMSItemByCode(itemsQuery);
        if (null == dto) {
            return new PageInfo<>(new ArrayList<>());
        }
        query.setItemsId(dto.getBizId());
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> models = detailsManager.listAllFileTypes(query);
        return getDropDownModels(models);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 所有申请原因下拉
     **/
    @Override
    public PageInfo<DropDownModel> listAllApplyReasons(@RequestBody DropDownQuery query) {
        QMSItemsQuery itemsQuery = new QMSItemsQuery();
        itemsQuery.setFullName("申请原因设置");
        QMSItemsDTO dto = itemsManager.getQMSItemByCode(itemsQuery);
        if (null == dto) {
            return new PageInfo<>(new ArrayList<>());
        }
        query.setItemsId(dto.getBizId());
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> dos = detailsManager.listAllFileTypes(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new PageInfo<>(new ArrayList<>());
        }
        for (DropDownModel aDo : dos) {
            aDo.setApplyReasonId(aDo.getFileTypeId());
            aDo.setApplyReasonName(aDo.getFileTypeIdName());
            aDo.setFileTypeId(null);
            aDo.setFileTypeIdName(null);
        }
        return new PageInfo<>(dos);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.dropdown.DropDownModel>
     * @description 所有申请类别下拉
     **/
    @Override
    public PageInfo<DropDownModel> listAllApplyTypes(@RequestBody DropDownQuery query) {
        QMSItemsQuery itemsQuery = new QMSItemsQuery();
        itemsQuery.setFullName("申请类别设置");
        QMSItemsDTO dto = itemsManager.getQMSItemByCode(itemsQuery);
        if (null == dto) {
            return new PageInfo<>(new ArrayList<>());
        }
        query.setItemsId(dto.getBizId());
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DropDownModel> dos = detailsManager.listAllFileTypes(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new PageInfo<>(new ArrayList<>());
        }
        for (DropDownModel aDo : dos) {
            aDo.setApplyTypeId(aDo.getFileTypeId());
            aDo.setApplyTypeName(aDo.getFileTypeIdName());
            aDo.setFileTypeId(null);
            aDo.setFileTypeIdName(null);
        }
        return new PageInfo<>(dos);
    }

    /**
     * 返回数据
     */
    private PageInfo<DropDownModel> getDropDownModels(List<DropDownModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }
}
