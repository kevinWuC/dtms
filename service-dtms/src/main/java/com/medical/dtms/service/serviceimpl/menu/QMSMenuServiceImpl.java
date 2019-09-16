package com.medical.dtms.service.serviceimpl.menu;

import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.menu.QMSMenuDTO;
import com.medical.dtms.dto.menu.query.QMSMenuQuery;
import com.medical.dtms.feignservice.menu.QMSMenuService;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.service.manager.menu.QMSMenuManager;
import com.medical.dtms.service.manager.role.QMSRoleInMenuManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version： QMSMenuServiceImpl.java v 1.0, 2019年08月19日 16:42 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class QMSMenuServiceImpl implements QMSMenuService {

    @Autowired
    private QMSMenuManager menuManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private QMSRoleInMenuManager roleInMenuManager;

    /**
     * @param [menuDTO]
     * @return java.lang.Boolean
     * @description 菜单管理 - 新增
     **/
    @Override
    public Boolean addMenu(@RequestBody QMSMenuDTO menuDTO) {
        // 校验编号的唯一性
        QMSMenuQuery query = new QMSMenuQuery(menuDTO.getParentId(), menuDTO.getCode());
        QMSMenuDTO menu = menuManager.getQMSMenuByCondition(query);
        // 存在，提示重复
        if (null != menu) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "编号重复，请重新填写");
        }

        try {
            // 不存在，直接新增
            menuDTO.setBizId(idGenerator.nextId());
            menuManager.addMenu(menuDTO);
        } catch (Exception e) {
            log.error("新增菜单失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [menuDTO]
     * @return java.lang.Boolean
     * @description 菜单管理 - 更新
     **/
    @Override
    public Boolean updateMenu(@RequestBody QMSMenuDTO menuDTO) {
        // 校验该菜单是否删除
        QMSMenuQuery query = new QMSMenuQuery(menuDTO.getBizId());
        QMSMenuDTO menu = menuManager.getQMSMenuByCondition(query);
        if (null == menu) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该菜单已被删除，无法操作");
        }

        // 校验是否允许编辑
        if (null != menu.getAllowEdit() && !menu.getAllowEdit()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该菜单不允许被编辑");
        }

        // 重复性校验
        query = new QMSMenuQuery(menuDTO.getParentId(), menuDTO.getCode());
        QMSMenuDTO dto = menuManager.getQMSMenuByCondition(query);
        if (null != dto && !dto.getBizId().equals(menuDTO.getBizId())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "编号重复，请重新填写");
        }

        // 更新
        try {
            menuManager.updateMenu(menuDTO);
        } catch (Exception e) {
            log.error("更新菜单失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [menuDTO]
     * @return java.lang.Boolean
     * @description 菜单管理 - 删除 (逻辑删除)
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean deleteMenu(@RequestBody QMSMenuDTO menuDTO) {
        // 校验该菜单是否删除
        QMSMenuQuery query = new QMSMenuQuery(menuDTO.getBizId());
        QMSMenuDTO menu = menuManager.getQMSMenuByCondition(query);
        if (null == menu) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该菜单已被删除，无法操作");
        }

        // 校验是否允许删除
        if (null != menu.getAllowDelete() && !menu.getAllowDelete()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该菜单不允许被删除");
        }

        // 删除子菜单
        query = new QMSMenuQuery();
        query.setParentId(menuDTO.getBizId());
        List<QMSMenuModel> models = menuManager.listQMSMenus(query);
        if (CollectionUtils.isNotEmpty(models)) {
            try {
                menuManager.deleteByPatentId(menuDTO.getBizId());
            } catch (Exception e) {
                log.error("子菜单删除失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        try {
            // 删除角色与菜单关联表的关联
            roleInMenuManager.deleteByMenuId(menuDTO.getBizId());
        } catch (Exception e) {
            log.error("角色与菜单关联表删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        try {
            menuDTO.setIsDeleted(true);
            menuManager.updateMenu(menuDTO);
        } catch (Exception e) {
            log.error("删除菜单失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.model.menu.QMSMenuModel>
     * @description 菜单管理 - 列表展示
     **/
    @Override
    public List<QMSMenuModel> listQMSMenus(@RequestBody QMSMenuQuery query) {
        if (null == query || null == query.getParentId()) {
            // 一级类别
            query.setParentId(Constants.PARENT_ID);
        }

        List<QMSMenuModel> models = menuManager.listQMSMenus(query);
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        List<QMSMenuModel> list = new ArrayList<>();
        for (QMSMenuModel model : models) {
            query.setParentId(model.getBizId());
            list = menuManager.listQMSMenus(query);
            if (CollectionUtils.isEmpty(list)) {
                model.setLastOrNot(true);
                model.setList(new ArrayList<>());
                continue;
            }
            model.setLastOrNot(false);
            model.setList(list);

            if (CollectionUtils.isNotEmpty(list)){
                for (QMSMenuModel qmsMenuModel : list) {
                    query.setParentId(qmsMenuModel.getBizId());
                    List<QMSMenuModel> childMenu = menuManager.listQMSMenus(query);
                    if (CollectionUtils.isEmpty(childMenu)) {
                        qmsMenuModel.setLastOrNot(true);
                        qmsMenuModel.setList(new ArrayList<>());
                        continue;
                    }
                    qmsMenuModel.setLastOrNot(false);
                    qmsMenuModel.setList(childMenu);
                }
            }
        }



        // 查询父级 name
        List<Long> parentIds = models.stream().map(QMSMenuModel::getParentId).distinct().collect(Collectors.toList());
        List<QMSMenuModel> parentInfos = menuManager.listParentInfos(parentIds);
        if (CollectionUtils.isEmpty(parentIds)) {
            return models;
        }

        for (QMSMenuModel model : models) {
            List<QMSMenuModel> collect = parentInfos.stream().filter(qmsMenuModel -> model.getParentId().equals(qmsMenuModel.getParentId())).collect(Collectors.toList());
            if (model.getParentId().equals(Constants.PARENT_ID)) {
                model.setParentName("父节点");
                continue;
            }
            model.setParentName(collect.get(0).getParentName());
        }
        return models;
    }

}
