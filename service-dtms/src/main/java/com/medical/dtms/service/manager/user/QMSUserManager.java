package com.medical.dtms.service.manager.user;

import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.user.SimpleUserModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.user.QMSUserDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.common.model.user.QMSUserModel;
import com.medical.dtms.service.dataobject.user.QMSUserDO;
import com.medical.dtms.service.mapper.qms.QMSUserMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSUserManager.java v 1.0, 2019年08月20日 17:43 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSUserManager {

    @Autowired
    private QMSUserMapper userMapper;

    /**
     * 校验用户是否存在
     */
    public QMSUserDTO getUserByCondition(BaseUserQuery query) {
        QMSUserDO aDo = userMapper.getUserByCondition(query);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, QMSUserDTO.class);
    }

    /**
     * 用户管理 - 新增用户
     */
    public Integer insert(QMSUserDTO dto) {
        QMSUserDO aDo = BeanConvertUtils.convert(dto, QMSUserDO.class);
        return userMapper.insert(aDo);
    }

    /**
     * 用户管理 - 编辑用户
     */
    public Integer updateUser(QMSUserDTO dto) {
        QMSUserDO aDo = BeanConvertUtils.convert(dto, QMSUserDO.class);
        return userMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 用户管理 - 删除用户
     */
    public Integer deleteUser(Long bizId) {
        return userMapper.deleteByPrimaryKey(bizId);
    }

    /**
     * 用户管理 - 分页展示用户列表
     */
    public List<QMSUserModel> pageListUser(BaseUserQuery query) {
        List<QMSUserModel> dos = userMapper.pageListUser(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new ArrayList<>();
        }
        return dos;
    }

    /**
     * 人员列表
     */
    public List<DropDownModel> listUsers(DropDownQuery query) {
        return userMapper.listUsers(query);
    }

    /**
     * 根据用户 ids 查询 用户信息
     */
    public List<SimpleUserModel> listUserInfos(List<Long> userIds) {
        return userMapper.listUserInfos(userIds);
    }

}
