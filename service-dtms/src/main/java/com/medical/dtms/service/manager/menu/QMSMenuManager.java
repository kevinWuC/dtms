package com.medical.dtms.service.manager.menu;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.menu.QMSMenuDTO;
import com.medical.dtms.dto.menu.query.QMSMenuQuery;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.menu.QMSMenuDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.qms.QMSMenuMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSMenuManager.java v 1.0, 2019年08月19日 16:52 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSMenuManager {

    @Autowired
    private QMSMenuMapper menuMapper;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;

    /**
     * 校验菜单是否存在
     */
    public QMSMenuDTO getQMSMenuByCondition(QMSMenuQuery menuQuery) {
        QMSMenuDO menu = menuMapper.getQMSMenuByCondition(menuQuery);
        if (null == menu) {
            return null;
        }
        return BeanConvertUtils.convert(menu, QMSMenuDTO.class);
    }

    /**
     * 菜单管理 - 新增
     */
    public Integer addMenu(QMSMenuDTO menuDTO) {
        QMSMenuDO aDo = BeanConvertUtils.convert(menuDTO, QMSMenuDO.class);
        return menuMapper.insert(aDo);
    }

    /**
     * 菜单管理 - 更新
     */
    public Integer updateMenu(QMSMenuDTO menuDTO) {
        QMSMenuQuery query = new QMSMenuQuery();
        query.setBizId(menuDTO.getBizId());
        QMSMenuDO oldMenu = menuMapper.getQMSMenuByCondition(query);

        QMSMenuDO newMenu = BeanConvertUtils.convert(menuDTO, QMSMenuDO.class);
        String ip = loginLogManager.getIpByUserId(menuDTO.getModifierId());
        // 记录日志
        logClient.logObject(
                // 对象主键
                oldMenu.getBizId(),
                // 操作人
                oldMenu.getModifier(),
                // 操作类型
                menuDTO.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : menuDTO.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newMenu.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                ip,
                // 备注，这里是操作模块名
                "菜单管理",
                // 旧值
                oldMenu,
                // 新值
                newMenu
        );
        return menuMapper.updateByPrimaryKeySelective(newMenu);
    }

    /**
     * 菜单管理 - 删除(物理删除)
     */
    public Integer deleteMenu(Long bizId) {
        return menuMapper.deleteByPrimaryKey(bizId);
    }

    /**
     * 菜单管理 - 根据父级id，删除子菜单(物理删除)
     */
    public Integer deleteByPatentId(Long bizId) {
        return menuMapper.deleteByPatentId(bizId);
    }

    /**
     * 菜单管理 - 列表展示
     */
    public List<QMSMenuModel> listQMSMenus(QMSMenuQuery query) {
        List<QMSMenuDO> dos = menuMapper.listQMSMenus(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new ArrayList<>();
        }
        return BeanConvertUtils.convertList(dos, QMSMenuModel.class);
    }

    /**
     * 查询父级 数据
     */
    public List<QMSMenuModel> listParentInfos(List<Long> parentIds) {
        return menuMapper.listParentInfos(parentIds);
    }
}
