package com.medical.dtms.service.serviceimpl.file;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.enumeration.file.FileApplyTypeEnum;
import com.medical.dtms.common.enumeration.file.FileStatusEnum;
import com.medical.dtms.common.enumeration.operate.SubmitTypeEnum;
import com.medical.dtms.common.model.file.ApplyCheckModel;
import com.medical.dtms.common.model.file.FileApplyDetailModel;
import com.medical.dtms.common.model.file.FileApplyModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.file.FileApplyDTO;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.feignservice.file.FileApplyService;
import com.medical.dtms.service.manager.file.FileApplyManager;
import com.medical.dtms.service.manager.file.FileModelManager;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： FileApplyServiceImpl.java v 1.0, 2019年08月30日 15:58 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class FileApplyServiceImpl implements FileApplyService {

    @Autowired
    private FileApplyManager applyManager;
    @Autowired
    private FileModelManager fileModelManager;
    @Autowired
    private QMSItemDetailsManager detailsManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 校验 修改/作废申请 是否合法
     **/
    @Override
    public Boolean checkOptionLegalOrNot(@RequestBody FileApplyDTO dto) {
        // 校验是否锁定
        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getFileId());
        FileModelDTO file = fileModelManager.getFileByCondition(query);
        if (null == file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "操作失败，记录不存在");
        }

        if (null != file.getApplyStatus() && Constants.LOCK.equals(file.getApplyStatus())) {
            if (SubmitTypeEnum.SubmitTypeEnum_7.getType().equals(dto.getSubmitType()) || SubmitTypeEnum.SubmitTypeEnum_8.getType().equals(dto.getSubmitType())) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "申请已经锁定，请等待申请完成！");
            }
            if (SubmitTypeEnum.SubmitTypeEnum_9.getType().equals(dto.getSubmitType())) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "未申请修改通过!");
            }
        }


        if (SubmitTypeEnum.SubmitTypeEnum_7.getType().equals(dto.getSubmitType()) || SubmitTypeEnum.SubmitTypeEnum_8.getType().equals(dto.getSubmitType())) {
            if (null != file.getAllowModify() && file.getAllowModify() | null != file.getAllowBad() && file.getAllowBad()) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "已经申请通过，请不要重复申请哦!");
            }
        }

        if (SubmitTypeEnum.SubmitTypeEnum_9.getType().equals(dto.getSubmitType())) {
            if (null == file.getAllowModify() && null == file.getAllowBad() && null == file.getApplyStatus() || Constants.NO_LOCK.equals(file.getApplyStatus())) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "未申请修改通过");
            }
        }

        // 如果文件状态为已提交，则提示：“已经发起操作啦！，不允许重复发起！“
        if (null != file.getFileStatus() && FileStatusEnum.FILE_STATUS_ENUM_2.equals(file.getFileStatus())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "已经发起操作啦！，不允许重复发起！");
        }
        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApplyModel>
     * @description 申请审核 分页查询
     **/
    @Override
    public PageInfo<ApplyCheckModel> pageListApplyCheck(@RequestBody FileModelQuery query) {
        List<ApplyCheckModel> models = applyManager.pageListApplyCheck(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        for (ApplyCheckModel model : models) {
            model.setFileApplyTypeName(model.getFileApplyType() == null ? null : FileApplyTypeEnum.getNameByType(model.getFileApplyType()));
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.model.file.FileApplyDetailModel
     * @description 申请回显
     **/
    @Override
    public FileApplyDetailModel selectByPrimaryKey(@RequestBody FileModelQuery query) {
        return applyManager.selectById(query.getBizId());
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 提交 修改/作废申请
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean addFileApply(@RequestBody FileApplyDTO dto) {
        // 校验是否锁定
        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getFileId());
        FileModelDTO file = fileModelManager.getFileByCondition(query);
        if (null == file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "操作失败，记录不存在");
        }

        try {
            // 锁定文件
            file.setModifier(dto.getCreator());
            file.setModifierId(dto.getCreatorId());
            file.setApplyType(dto.getApplyType());
            file.setApplyStatus(Constants.LOCK);
            fileModelManager.updateFile(file);
        } catch (Exception e) {
            log.error("文件锁定失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        try {
            dto.setBizId(idGenerator.nextId());
            dto.setFileApplyType(SubmitTypeEnum.SubmitTypeEnum_7.getType().equals(dto.getSubmitType()) == true ? FileApplyTypeEnum.FileApplyTypeEnum_2.getType() : FileApplyTypeEnum.FileApplyTypeEnum_5.getType());
            applyManager.addFileApply(dto);
        } catch (Exception e) {
            log.error("申请修改/作废/修改失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApplyModel>
     * @description 修改申请列表（分页）
     **/
    @Override
    public PageInfo<FileApplyModel> pageListApply(@RequestBody FileModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileApplyModel> models = fileModelManager.pageListApply(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * 申请审核
     *
     * @param dto
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean agreeOrDisThisApply(@RequestBody FileApplyDTO dto) {
        FileApplyDTO apply = applyManager.getApplyDetail(dto.getBizId());
        if (null == apply) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条记录已被删除，无法操作！");
        }

        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getFileId());
        FileModelDTO file = fileModelManager.getFileByCondition(query);
        if (null == file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条文件记录已被删除，无法操作！");
        }
        if (SubmitTypeEnum.SubmitTypeEnum_10.equals(dto.getSubmitType())) {
            // 字段 applytype 判断是什么类型
            QMSItemDetailsQuery query1 = new QMSItemDetailsQuery();
            query1.setBizId(file.getApplyType());
            QMSItemDetailsDTO details = detailsManager.getQMSItemDetailsByCondition(query1);
            if (null == details || StringUtils.isBlank(details.getItemName())) {
                log.error("无法确定申请类型");
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "操作失败，请稍后重试");
            }

            // 作废
            if (StringUtils.equals(FileApplyTypeEnum.FileApplyTypeEnum_5.getName(), details.getItemName()) || details.getItemName().contains(FileApplyTypeEnum.FileApplyTypeEnum_5.getName())) {
                file.setAllowBad(true);
            }

            // 修改
            if (StringUtils.equals(FileApplyTypeEnum.FileApplyTypeEnum_6.getName(), details.getItemName()) || details.getItemName().contains(FileApplyTypeEnum.FileApplyTypeEnum_6.getName())) {
                file.setAllowModify(true);
            }

            dto.setResult(true);
        } else {
            dto.setResult(false);
        }

        try {
            applyManager.update(dto);

            file.setApplyStatus(Constants.NO_LOCK);
            fileModelManager.updateFile(file);
        } catch (Exception e) {
            log.error("审核失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }
}
