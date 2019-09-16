package com.medical.dtms.service.serviceimpl.user;

import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.user.QMSUserInDeptDTO;
import com.medical.dtms.feignservice.user.QMSUserInDeptService;
import com.medical.dtms.service.manager.user.QMSUserInDeptManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： QMSRoleInMenuServiceImpl.java v 1.0, 2019年08月20日 16:00  Exp$
 * @Description 用户 - 部门 关联
 **/
@RestController
@Slf4j
public class QMSUserInDeptServiceImpl implements QMSUserInDeptService {

    @Autowired
    private QMSUserInDeptManager qmsUserInDeptManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 新增 角色 - 菜单关联
     **//*
    @Override
    public Boolean insert(@RequestBody QMSUserInDeptDTO dto) {
        // 查询用户关联的菜单
        QMSUserInDeptQuery query = new QMSUserInDeptQuery();
        query.setUserId(dto.getUserId());
        List<QMSUserInDeptDTO> dtos = qmsUserInDeptManager.listQMSUserInDept(query);
        if (CollectionUtils.isNotEmpty(dtos)){
            // 删除
            try {
                qmsUserInDeptManager.deleteByPrimaryKey(dto.getUserId());
            } catch (Exception e) {
                log.error("删除用户 - 部门关联失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        try {
            // 新增关联
            List<QMSUserInDeptDTO> list = new ArrayList<>();
            QMSUserInDeptDTO deptDTO;
            for (Long deptId : dto.getDeptIdList()) {
                deptDTO = new QMSUserInDeptDTO();
                BeanUtils.copyProperties(dto,deptDTO);
                deptDTO.setBizId(idGenerator.nextId());
                deptDTO.setDeptId(deptId);
                list.add(deptDTO);
            }
            qmsUserInDeptManager.insert(list);
        } catch (Exception e) {
            log.error("新增用户 - 部门关联失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }*/

    @Override
    public Boolean addQMSUserInDept(QMSUserInDeptDTO dto) {
        return null;
    }
}
