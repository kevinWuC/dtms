package com.medical.dtms.service.manager.user;

import com.medical.dtms.common.model.dept.QMSUserInDeptModel;
import com.medical.dtms.common.model.syslog.BaseSimpleUserModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.user.QMSUserInDeptDTO;
import com.medical.dtms.dto.user.query.QMSUserInDeptQuery;
import com.medical.dtms.service.dataobject.user.QMSUserInDeptDO;
import com.medical.dtms.service.mapper.qms.QMSUserInDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： QMSUserInDeptManager.java v 1.0, 2019年08月20日 20:47 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSUserInDeptManager {


    @Autowired
    private QMSUserInDeptMapper userInDeptMapper;

    /**
     * 查询用户是否关联部门
     * @param query
     */
    public List<QMSUserInDeptDTO> listQMSUserInDept(QMSUserInDeptQuery query) {
        List<QMSUserInDeptDO> list = userInDeptMapper.listUserInDept(query);
        return BeanConvertUtils.convertList(list, QMSUserInDeptDTO.class);
    }

    /**
     * 根据用户 id 删除数据
     */
    public  Integer deleteByPrimaryKey(Long userId) {
        QMSUserInDeptDO deptDO = new QMSUserInDeptDO();
        deptDO.setUserId(userId);
        return userInDeptMapper.deleteByCondition(deptDO);
    }

    /**
     * 新增 用户与部门关联
     */
    public Integer insert(List<QMSUserInDeptDTO> dtoList) {
        List<QMSUserInDeptDO> dos = BeanConvertUtils.convertList(dtoList, QMSUserInDeptDO.class);
        return userInDeptMapper.addQMSUserInDept(dos);
    }

    /**
     * 根据部门 id 删除数据
     */
    public  Integer deleteByCondition(Long deptId) {
        QMSUserInDeptDO deptDO = new QMSUserInDeptDO();
        deptDO.setDeptId(deptId);
        return userInDeptMapper.deleteByCondition(deptDO);
    }

    /** 根据用户ids 查询 部门 id
     * @param userIds*/
    public List<BaseSimpleUserModel> listDeptIdsByUserIds(List<Long> userIds){
        return userInDeptMapper.listDeptIdsByUserIds(userIds);
    }

    /** 根据用户查询部门数据*/
    public List<QMSUserInDeptModel> listDeptByUserIdsAndDept(QMSUserInDeptQuery query){
        return userInDeptMapper.listDeptByUserIdsAndDept(query);
    }

}
