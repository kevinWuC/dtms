package com.medical.dtms.service.serviceimpl.role;

import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.role.QMSRoleInMenuDTO;
import com.medical.dtms.dto.role.query.QMSRoleInMenuQuery;
import com.medical.dtms.feignservice.role.QMSRoleInMenuService;
import com.medical.dtms.service.manager.role.QMSRoleInMenuManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSRoleInMenuServiceImpl.java v 1.0, 2019年08月20日 16:00 wuxuelin Exp$
 * @Description 角色 - 菜单 关联
 **/
@RestController
@Slf4j
public class QMSRoleInMenuServiceImpl implements QMSRoleInMenuService {

    @Autowired
    private QMSRoleInMenuManager roleInMenuManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 新增 角色 - 菜单关联
     **/
    @Override
    public Boolean addQMSRoleInMenu(@RequestBody QMSRoleInMenuDTO dto) {
        // 查询角色关联的菜单
        QMSRoleInMenuQuery query = new QMSRoleInMenuQuery();
        query.setRoleId(dto.getRoleId());
        List<QMSRoleInMenuDTO> dtos = roleInMenuManager.listRoleInMenu(query);
        if (CollectionUtils.isNotEmpty(dtos)){
            // 删除
            try {
                roleInMenuManager.deleteByRoleId(dto.getRoleId());
            } catch (Exception e) {
                log.error("删除角色 - 菜单关联失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        try {
            // 新增关联
            List<QMSRoleInMenuDTO> list = new ArrayList<>();
            QMSRoleInMenuDTO menuDTO;
            for (Long menuId : dto.getMenuIdList()) {
                menuDTO = new QMSRoleInMenuDTO();
                BeanUtils.copyProperties(dto,menuDTO);
                menuDTO.setBizId(idGenerator.nextId());
                menuDTO.setMenuId(menuId);
                list.add(menuDTO);
            }
            roleInMenuManager.addQMSRoleInMenu(list);
        } catch (Exception e) {
            log.error("新增角色 - 菜单关联失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }
}
