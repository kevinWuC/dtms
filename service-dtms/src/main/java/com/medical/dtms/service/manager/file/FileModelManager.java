package com.medical.dtms.service.manager.file;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.file.*;
import com.medical.dtms.common.model.train.TrainFileModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.dto.train.query.TrainFileQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.file.FileModelDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.file.FileModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileManager.java v 1.0, 2019年08月27日 11:23 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileModelManager {

    @Autowired
    private FileModelMapper fileModelMapper;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;

    /**
     * 查询文件信息
     */
    public FileModelDTO getFileByCondition(FileModelQuery query) {
        FileModelDO fileDO = fileModelMapper.getFileByCondition(query);
        return BeanConvertUtils.convert(fileDO, FileModelDTO.class);
    }

    /**
     * 添加文件
     */
    public Integer insert(FileModelDTO dto) {
        FileModelDO aDo = BeanConvertUtils.convert(dto, FileModelDO.class);
        return fileModelMapper.insert(aDo);
    }

    /**
     * 更新文件
     */
    public Integer updateFile(FileModelDTO dto) {
        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getBizId());
        FileModelDO oldFile = fileModelMapper.getFileByCondition(query);

        FileModelDO newFile = BeanConvertUtils.convert(dto, FileModelDO.class);

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldFile.getBizId()),
                // 操作人
                dto.getModifier(),
                // 操作类型
                dto.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : dto.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newFile.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getModifierId()),
                // 备注，这里是操作模块名
                "文件管理",
                // 旧值
                oldFile,
                // 新值
                newFile
        );

        return fileModelMapper.updateByPrimaryKeySelective(newFile);
    }

    /**
     * 文件列表查询
     */
    public List<FileModel> listFiles(FileModelQuery query) {
        return fileModelMapper.listFiles(query);
    }

    /**
     * 文件查询列表展示
     */
    public List<FileQueryModel> listFileQueryFiles(FileModelQuery query) {
        return fileModelMapper.listFileQueryFiles(query);
    }

    /**
     * 导出
     */
    public List<FileQueryModel> exportFiles(FileModelQuery query) {
        return fileModelMapper.listFileQueryFiles(query);
    }

    /**
     * 文件审批 - 列表展示
     */
    public List<FileApprovalModel> pageListFilesInApproval(FileModelQuery query) {
        return fileModelMapper.pageListFilesInApproval(query);
    }

    /**
     * 文件接收列表展示
     */
    public List<FileReceiveModel> pageListForReceive(FileModelQuery query) {
        return fileModelMapper.pageListForReceive(query);
    }

    /**
     * 文件类型下拉框
     */
    public List<DropDownModel> listFileTypes(DropDownQuery query) {
        return fileModelMapper.listFileTypes(query);
    }

    /**
     * 申请类别下拉框
     */
    public List<DropDownModel> listApplyTypesInFileModel(DropDownQuery query) {
        return fileModelMapper.listApplyTypesInFileModel(query);
    }

    /**
     * 发布年度
     */
    public List<DropDownModel> listApplyYears(DropDownQuery query) {
        return fileModelMapper.listApplyYears(query);
    }

    /**
     * 培训文件待选列表展示
     **/
    public List<TrainFileModel> pageListForTrainFileSelect(TrainFileQuery query) {
        return fileModelMapper.pageListForTrainFileSelect(query);
    }

    /**
     * 培训文件已选列表展示
     **/
    public List<TrainFileModel> pageListForTrainFileSelected(TrainFileQuery query) {
        return fileModelMapper.pageListForTrainFileSelected(query);
    }

    /**
     * 修改申请列表
     */
    public List<FileApplyModel> pageListApply(FileModelQuery query) {
        return fileModelMapper.pageListApply(query);
    }

    /**
     * 申请部门下拉
     */
    public List<DropDownModel> listApplyDeptInFileModel(DropDownQuery query) {
        return fileModelMapper.listApplyDeptInFileModel(query);
    }

    /**
     * 文件年审（分页展示）
     */
    public List<FileYearModel> pageListFileYear(FileModelQuery query) {
        return fileModelMapper.pageListFileYear(query);
    }

    /**
     * 分页查询归档/作废文件
     */
    public List<ArchiveOrInvalidModel> queryListArchiveOrInvalidFile(FileModelQuery query) {
        return fileModelMapper.queryListArchiveOrInvalidFile(query);
    }

    /**
     * 退回文件
     */
    public List<BackFileModel> pageListBackFile(FileModelQuery query) {
        return fileModelMapper.pageListBackFile(query);
    }

    /**
     * 文件报批详情查询
     */
    public FileDetailModel selectByPrimaryKey(Long id) {
        return fileModelMapper.selectByPrimaryKey(id);
    }

}
