package com.medical.dtms.service.serviceimpl.role;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.role.QMSRoleDTO;
import com.medical.dtms.dto.role.QMSRoleInMenuDTO;
import com.medical.dtms.dto.role.query.QMSRoleInMenuQuery;
import com.medical.dtms.dto.role.query.QMSRoleQuery;
import com.medical.dtms.dto.user.QMSUserInRoleDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.feignservice.role.QMSRoleService;
import com.medical.dtms.common.model.role.QMSRoleModel;
import com.medical.dtms.service.manager.role.QMSRoleInMenuManager;
import com.medical.dtms.service.manager.role.QMSRoleManager;
import com.medical.dtms.service.manager.user.QMSUserInRoleManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @version： QMSRoleServiceImpl.java v 1.0, 2019年08月20日 12:13 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class QMSRoleServiceImpl implements QMSRoleService {

    @Autowired
    private QMSRoleManager roleManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private QMSRoleInMenuManager roleInMenuManager;
    @Autowired
    private QMSUserInRoleManager userInRoleManager;

    /**
     * @param [RoleDTO]
     * @return java.lang.Boolean
     * @description 角色管理 - 新增
     **/
    @Override
    public Boolean addRole(@RequestBody QMSRoleDTO roleDTO) {
        // 根据角色名称校验唯一性
        QMSRoleQuery query = new QMSRoleQuery(roleDTO.getRoleName());
        QMSRoleDTO role = roleManager.getRoleByCondition(query);
        if (null != role) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "角色名称重复，请重新填写");
        }

        try {
            roleDTO.setBizId(idGenerator.nextId());
            roleManager.insert(roleDTO);
        } catch (Exception e) {
            log.error("新增角色失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [RoleDTO]
     * @return java.lang.Boolean
     * @description 角色管理 - 编辑
     **/
    @Override
    public Boolean updateRole(@RequestBody QMSRoleDTO roleDTO) {
        // 校验是否存在
        QMSRoleDTO dto = checkExistOrNot(roleDTO);

        // 校验是否允许编辑
        if (null != dto.getAllowEdit() && !dto.getAllowEdit()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该角色不允许被编辑");
        }

        // 重复性校验
        if (StringUtils.isNotBlank(roleDTO.getRoleName())) {
            QMSRoleQuery query = new QMSRoleQuery(roleDTO.getRoleName());
            QMSRoleDTO role = roleManager.getRoleByCondition(query);
            if (null != role && !role.getBizId().equals(roleDTO.getBizId())) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "角色名称重复，请重新填写");
            }
        }

        try {
            roleManager.updateRole(roleDTO);
        } catch (Exception e) {
            log.error("角色编辑失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [RoleDTO]
     * @return java.lang.Boolean
     * @description 角色管理 - 删除
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean deleteRole(@RequestBody QMSRoleDTO roleDTO) {
        QMSRoleDTO dto = checkExistOrNot(roleDTO);

        // 校验是否允许删除
        if (null != dto.getAllowDelete() && !dto.getAllowDelete()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该角色不允许被删除");
        }

        // 删除 角色 - 菜单关联表
        QMSRoleInMenuQuery query = new QMSRoleInMenuQuery();
        query.setRoleId(roleDTO.getBizId());
        List<QMSRoleInMenuDTO> dtos = roleInMenuManager.listRoleInMenu(query);
        if (CollectionUtils.isNotEmpty(dtos)) {
            try {
                roleInMenuManager.deleteByRoleId(roleDTO.getBizId());
            } catch (Exception e) {
                log.error("删除角色与菜单关联失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        // 删除 角色 - 用户 关联表
        BaseUserQuery qmsUserInRoleQuery = new BaseUserQuery();
        qmsUserInRoleQuery.setRoleId(roleDTO.getBizId());
        List<QMSUserInRoleDTO> list = userInRoleManager.listQMSUserInRole(qmsUserInRoleQuery);
        if (CollectionUtils.isNotEmpty(list)) {
            if (CollectionUtils.isNotEmpty(list)) {
                try {
                    userInRoleManager.deleteQMSUserInRoleByRoleId(roleDTO.getBizId());
                } catch (Exception e) {
                    log.error("删除角色与用户关联失败", e);
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
                }
            }
        }

        // 删除角色表
        try {
            roleDTO.setIsDeleted(true);
            roleManager.updateRole(roleDTO);
        } catch (Exception e) {
            log.error("删除角色失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.role.QMSRoleModel>
     * @description 角色管理 - 分页展示
     **/
    @Override
    public PageInfo<QMSRoleModel> pageListQMSRoles(@RequestBody QMSRoleQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<QMSRoleModel> models = roleManager.listQMSRoles(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        // 查询角色拥有的菜单
        List<Long> roleIdList = models.stream().map(QMSRoleModel::getBizId).distinct().collect(Collectors.toList());
        List<QMSMenuModel> menuModels = roleInMenuManager.listMenuByRole(roleIdList);
        if (CollectionUtils.isNotEmpty(menuModels)) {
            for (QMSRoleModel roleModel : models) {
                List<String> newList = new ArrayList<>();
                if (CollectionUtils.isEmpty(menuModels.stream().filter(qmsMenuModel -> roleModel.getBizId().equals(qmsMenuModel.getRoleId())).collect(Collectors.toList())) != true) {
                    menuModels.stream().filter(qmsMenuModel -> roleModel.getBizId().equals(qmsMenuModel.getRoleId())).collect(Collectors.toList()).
                            stream().map(QMSMenuModel::getBizId).distinct().collect(Collectors.toList()).forEach(aLong -> newList.add(String.valueOf(aLong)));
                }
                roleModel.setMenuIdListStr(newList);
            }

        }
        return new PageInfo<>(models);
    }


    /**
     * 校验角色是否存在
     */
    private QMSRoleDTO checkExistOrNot(@RequestBody QMSRoleDTO roleDTO) {
        QMSRoleQuery roleQuery = new QMSRoleQuery(roleDTO.getBizId());
        QMSRoleDTO dto = roleManager.getRoleByCondition(roleQuery);
        if (null == dto) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该角色已被删除，无法操作");
        }
        return dto;
    }

}
