package com.medical.dtms.service.manager.dept;

import com.medical.dtms.common.model.dept.QMSDeptModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.dept.QMSDeptDTO;
import com.medical.dtms.dto.dept.query.QMSDeptQuery;
import com.medical.dtms.service.dataobject.dept.QMSDeptDO;
import com.medical.dtms.service.mapper.qms.QMSDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： QMSDeptManager.java v 1.0, 2019年08月14日 14:17 Exp$
 * @Description
 **/
@Service
public class QMSDeptManager {

    @Autowired
    private QMSDeptMapper qmsDeptMapper;

    /**
     * 部门类别 - 根据编号做唯一性校验
     */
    public QMSDeptDTO getQMSDeptByCode(QMSDeptQuery query) {
        QMSDeptDO deptDO = qmsDeptMapper.getQMSDeptByCode(query);
        if (null == deptDO) {
            return null;
        }
        return BeanConvertUtils.convert(deptDO, QMSDeptDTO.class);
    }

    /**
     * 部门管理 - 列表展示
     * @return
     */
    public List<QMSDeptModel> listQMSDept(QMSDeptQuery query) {
        return qmsDeptMapper.listQMSDept(query);
    }
    /**
     * 查询父级 数据
     */
    public List<QMSDeptModel> listParentInfos(List<Long> parentIds) {
        return qmsDeptMapper.listParentInfos(parentIds);
    }

    /**
     * 部门类别 - 新增
     */
    public Integer insert(QMSDeptDTO deptDTO) {
        QMSDeptDO aDo = BeanConvertUtils.convert(deptDTO, QMSDeptDO.class);
        return qmsDeptMapper.insert(aDo);
    }

    /**
     * 部门类别 - 主键查询是否存在
     */
    public QMSDeptDTO selectByPrimaryKey(Long bizId) {
        QMSDeptDO aDo = qmsDeptMapper.selectByPrimaryKey(bizId);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, QMSDeptDTO.class);
    }

    /**
     * 部门类别 - 更新
     */
    public Integer updateByPrimaryKeySelective(QMSDeptDTO dto) {
        QMSDeptDO aDo = BeanConvertUtils.convert(dto, QMSDeptDO.class);
        return qmsDeptMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 部门管理 - 删除(物理删除)
     */
    public Integer deleteDept(Long bizId) {
        return qmsDeptMapper.deleteByPrimaryKey(bizId);
    }

    /**
     * 部门管理 - 根据父级id，删除子部门(物理删除)
     */
    public Integer deleteByPatentId(Long bizId){
        return qmsDeptMapper.deleteByPatentId(bizId);
    }


}
