package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.file.FileYearRecordModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileYearDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.file.FileYearDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.file.FileYearMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileYearManager.java v 1.0, 2019年08月28日 18:29 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileYearManager {

    @Autowired
    private FileYearMapper yearMapper;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;


    /**
     * 新增年审记录
     */
    public int insert(FileYearDTO dto) {
        FileYearDO newDo = BeanConvertUtils.convert(dto, FileYearDO.class);

        FileYearDO oldDo = new FileYearDO();
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
                "文件年审",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return yearMapper.insert(newDo);
    }

    /**
     * 年审记录（分页展示）
     */
    public List<FileYearRecordModel> pageListFileYearRecord(FileModelQuery query) {
        return yearMapper.pageListFileYearRecord(query);
    }

}
