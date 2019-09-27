package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.file.ApplyCheckModel;
import com.medical.dtms.common.model.file.FileApplyDetailModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileApplyDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.file.FileApplyDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.file.FileApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileApplyManager.java v 1.0, 2019年08月30日 15:59 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileApplyManager {

    @Autowired
    private FileApplyMapper applyMapper;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private LogClient logClient;

    /**
     * 添加申请
     */
    public Integer addFileApply(FileApplyDTO dto) {
        FileApplyDO newDo = BeanConvertUtils.convert(dto, FileApplyDO.class);

        FileApplyDO oldDo = new FileApplyDO();
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
                "提交修改/作废申请",
                // 旧值
                oldDo,
                // 新值
                newDo
        );
        return applyMapper.insert(newDo);
    }

    /**
     * 获取申请详情
     */
    public FileApplyDTO getApplyDetail(Long bizId) {
        FileApplyDO aDo = applyMapper.selectByPrimaryKey(bizId);
        return BeanConvertUtils.convert(aDo, FileApplyDTO.class);
    }

    /**
     * 更新申请结果
     */
    public Integer update(FileApplyDTO apply) {
        FileApplyDO newDo = BeanConvertUtils.convert(apply, FileApplyDO.class);
        FileApplyDO oldDo = applyMapper.selectByPrimaryKey(apply.getBizId());

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                apply.getModifier(),
                // 操作类型
                apply.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : apply.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(apply.getModifierId()),
                // 备注，这里是操作模块名
                "更新申请结果",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return applyMapper.updateByPrimaryKeySelective(newDo);
    }

    /**
     * 申请审核 分页查询
     */
    public List<ApplyCheckModel> pageListApplyCheck(FileModelQuery query) {
        return applyMapper.pageListApplyCheck(query);
    }

    /**
     * 申请回显
     */
    public FileApplyDetailModel selectById(Long id) {
        return applyMapper.selectById(id);
    }
}
