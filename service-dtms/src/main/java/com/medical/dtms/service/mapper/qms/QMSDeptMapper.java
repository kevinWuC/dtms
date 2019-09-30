package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.dept.QMSDeptModel;
import com.medical.dtms.dto.dept.query.QMSDeptQuery;
import com.medical.dtms.service.dataobject.dept.QMSDeptDO;
import com.medical.dtms.service.dataobject.menu.QMSMenuDO;
import feign.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSDeptMapper {
    int deleteByPrimaryKey(@Param("deptId") Long deptId);

    int deleteByPatentId(@org.apache.ibatis.annotations.Param("bizId") Long bizId);

    int insert(QMSDeptDO record);

    QMSDeptDO selectByPrimaryKey(@Param("bizId") Long bizId);

    int updateByPrimaryKeySelective(QMSDeptDO record);

    QMSDeptDO getQMSDeptByCode(QMSDeptQuery query);

    QMSMenuDO getQMSDeptByCondition(QMSDeptQuery deptQuery);

    List<QMSDeptModel> listQMSDept(QMSDeptQuery query);

    List<QMSDeptModel> listParentInfos(List<Long> parentIds);

    List<QMSDeptDO> showDeptInfoByDeptIds(List<Long> deptIds);
}