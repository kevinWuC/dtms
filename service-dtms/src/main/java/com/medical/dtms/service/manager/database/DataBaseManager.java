package com.medical.dtms.service.manager.database;

import com.medical.dtms.common.model.datasource.BackUpInfoModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.datasource.QMSBackUpDTO;
import com.medical.dtms.dto.datasource.query.QMSBackUpQuery;
import com.medical.dtms.service.dataobject.datasource.QMSBackUpDO;
import com.medical.dtms.service.mapper.datasource.QMSBackUpMapper;
import com.medical.dtms.service.mapper.qms.DataBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： DataBaseManager.java v 1.0, 2019年08月22日 9:43 wuxuelin Exp$
 * @Description
 **/
@Service
public class DataBaseManager {

    @Autowired
    private DataBaseMapper dataBaseMapper;
    @Autowired
    private QMSBackUpMapper upDOMapper;

    /**
     * 根据数据库查询表
     */
    public List<String> showTables(String dataBaseName) {
        return dataBaseMapper.showTables(dataBaseName);
    }

    /**
     * 保存导出的sql 文件信息到数据库
     */
    public Integer insertSqlInfo(QMSBackUpDTO dto) {
        QMSBackUpDO aDo = BeanConvertUtils.convert(dto, QMSBackUpDO.class);
        return upDOMapper.insert(aDo);
    }

    /**
     * 主键查询是否存在
     */
    public QMSBackUpDTO selectByPrimaryKey(String bizId) {
        QMSBackUpDO upDO = upDOMapper.selectByPrimaryKey(bizId);
        return BeanConvertUtils.convert(upDO, QMSBackUpDTO.class);
    }

    /**
     * 更新
     */
    public Integer updateByPrimaryKeySelective(QMSBackUpDTO dto) {
        QMSBackUpDO aDo = BeanConvertUtils.convert(dto, QMSBackUpDO.class);
        return upDOMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 查询备份列表
     */
    public List<BackUpInfoModel> ListBackUpInfo(QMSBackUpQuery query) {
        return upDOMapper.ListBackUpInfo(query);
    }

}
