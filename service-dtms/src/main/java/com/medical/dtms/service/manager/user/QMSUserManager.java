package com.medical.dtms.service.manager.user;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.user.SimpleUserModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.menu.QMSMenuDTO;
import com.medical.dtms.dto.user.QMSUserDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.common.model.user.QMSUserModel;
import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import com.medical.dtms.service.dataobject.user.QMSUserDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.qms.QMSSysLogsMapper;
import com.medical.dtms.service.mapper.qms.QMSUserMapper;
import com.medical.dtms.logclient.service.LogClient;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version： QMSUserManager.java v 1.0, 2019年08月20日 17:43 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSUserManager {

    @Autowired
    private QMSUserMapper userMapper;
    @Autowired
    private QMSSysLogsMapper qmsSysLogsMapper;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;


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
        QMSUserDO newDo = BeanConvertUtils.convert(dto, QMSUserDO.class);

        QMSUserDO oldDo = new QMSUserDO();
        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_INSERT.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "用户管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return userMapper.insert(newDo);
    }

    /**
     * 用户管理 - 编辑用户
     */
    public Integer updateUser(QMSUserDTO dto) {

        BaseUserQuery query = new BaseUserQuery();
        query.setUserId(dto.getBizId());
        QMSUserDO oldUser = userMapper.getUserByCondition(query);
        QMSUserDO newUser = BeanConvertUtils.convert(dto, QMSUserDO.class);
        // 获取表名

        logClient.logObject(
                // 对象主键
                String.valueOf(oldUser.getBizId()),
                // 操作人
                dto.getModifier(),
                // 操作类型
                dto.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : dto.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newUser.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getModifierId()),
                // 备注，这里是操作模块名
                "用户管理",
                // 旧值
                oldUser,
                // 新值
                newUser
        );
        return userMapper.updateByPrimaryKeySelective(newUser);
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
        return userMapper.pageListUser(query);
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
        return userMapper.listUserInfoByIds(userIds);
    }

    /**
     * 人员列表
     */
    public List<QMSUserModel> listUsersInfo(BaseUserQuery query) {
        return userMapper.listUsersInfo(query);
    }

}
