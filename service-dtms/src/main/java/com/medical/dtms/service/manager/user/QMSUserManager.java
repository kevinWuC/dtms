package com.medical.dtms.service.manager.user;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.user.SimpleUserModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
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
        QMSUserDO aDo = BeanConvertUtils.convert(dto, QMSUserDO.class);
        int num = userMapper.insert(aDo);
        if (num == 1) {
            //新增日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_INSERT.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(aDo.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(aDo.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(aDo.getBizId()));
            qmsSysLogsDO.setOperationIp(loginLogManager.getIpByUserId(aDo.getCreatorId()));
            qmsSysLogsDO.setGmtCreated(new Date());
            qmsSysLogsDO.setCreator(aDo.getCreator());
            qmsSysLogsDO.setCreatorId(aDo.getCreatorId());
            qmsSysLogsDO.setIsDeleted(false);
            int insert = qmsSysLogsMapper.insert(qmsSysLogsDO);
        }
        return num;
    }

    /**
     * 用户管理 - 编辑用户
     */
    public Integer updateUser(QMSUserDTO dto) {

        BaseUserQuery query = new BaseUserQuery();
        query.setUserId(dto.getBizId());
        QMSUserDTO oldUser = getUserByCondition(query);
        QMSUserDO newUser = BeanConvertUtils.convert(dto, QMSUserDO.class);
        // 获取表名
        String tableName = operateManager.getTableName(newUser.getClass());
        String ip = loginLogManager.getIpByUserId(dto.getModifierId());

        String comment = "用户管理";

        logClient.logObject(
                // 对象主键
                oldUser.getBizId().toString(),
                // 操作人
                oldUser.getModifier(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_UPDATE.getName(),
                // 本次操作的别名，这里是操作的表名
                tableName,
                // 本次操作的额外描述，这里记录为操作人的ip
                ip,
                // 备注，这里是操作模块名
                comment,
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
