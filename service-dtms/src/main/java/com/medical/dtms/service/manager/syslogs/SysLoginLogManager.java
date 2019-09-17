package com.medical.dtms.service.manager.syslogs;

import com.medical.dtms.common.model.syslog.SimpleLogInLogModel;
import com.medical.dtms.common.model.syslog.SysLoginLogModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.log.QMSSysLoginLogDTO;
import com.medical.dtms.dto.log.query.QMSSysLoginLogQuery;
import com.medical.dtms.service.dataobject.log.QMSSysLoginLogDO;
import com.medical.dtms.service.mapper.qms.QMSSysLoginLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： SysLoginLogManager.java v 1.0, 2019年08月20日 16:02 huangshuaiqaun Exp$
 * @Description
 **/
@Service
public class SysLoginLogManager {

    @Autowired
    private QMSSysLoginLogMapper qmsSysLoginLogMapper;

    /**
     * 登陆日志 - 列表查询
     */
    public List<SysLoginLogModel> listLoginLogs(QMSSysLoginLogQuery query) {
        return qmsSysLoginLogMapper.listLoginLogs(query);
    }

    /**
     * 登陆日志 - 新增
     */
    public Integer insert(QMSSysLoginLogDTO loginLogDTO) {
        QMSSysLoginLogDO aDo = BeanConvertUtils.convert(loginLogDTO, QMSSysLoginLogDO.class);
        return qmsSysLoginLogMapper.insert(aDo);
    }

    /**
     * 登陆日志 - 新增
     */
    public Integer insertSelective(QMSSysLoginLogDTO loginLogDTO) {
        QMSSysLoginLogDO aDo = BeanConvertUtils.convert(loginLogDTO, QMSSysLoginLogDO.class);
        return qmsSysLoginLogMapper.insertSelective(aDo);
    }

    /**
     * 登陆日志 - 主键查询是否存在
     */
    public QMSSysLoginLogDTO selectByPrimaryKey(Long bizId) {
        QMSSysLoginLogDO aDo = qmsSysLoginLogMapper.selectByPrimaryKey(bizId);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, QMSSysLoginLogDTO.class);
    }

    /**
     * 登陆日志 - 删除（物理删除）
     */
    public Integer deleteByPrimaryKey(Long loginLogId) {
        return qmsSysLoginLogMapper.deleteByPrimaryKey(loginLogId);
    }

    /**
     * 登陆日志 - 更新
     */
    public Integer updateByPrimaryKey(QMSSysLoginLogDTO loginLogDTO) {
        QMSSysLoginLogDO aDo = BeanConvertUtils.convert(loginLogDTO, QMSSysLoginLogDO.class);
        return qmsSysLoginLogMapper.updateByPrimaryKey(aDo);
    }

    /**
     * 登陆日志 - 更新
     */
    public Integer updateByPrimaryKeySelective(QMSSysLoginLogDTO loginLogDTO) {
        QMSSysLoginLogDO aDo = BeanConvertUtils.convert(loginLogDTO, QMSSysLoginLogDO.class);
        return qmsSysLoginLogMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 根据用户id 查询数据
     */
    public List<SimpleLogInLogModel> listUserLastVisitAndVisitTime(List<Long> userIds) {
        return qmsSysLoginLogMapper.listUserLastVisitAndVisitTime(userIds);
    }

    /** 根据用户 id 获取 ip*/
    public SysLoginLogModel getIpByUserId(Long userId){
        return qmsSysLoginLogMapper.getIpByUserId(userId);
    }

}
