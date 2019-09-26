package com.medical.dtms.service.serviceimpl.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.enumeration.file.FileApplyTypeEnum;
import com.medical.dtms.common.enumeration.file.FileStatusEnum;
import com.medical.dtms.common.enumeration.operate.SubmitTypeEnum;
import com.medical.dtms.common.enumeration.result.LogResultEnum;
import com.medical.dtms.common.model.file.*;
import com.medical.dtms.common.model.user.SimpleUserModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.file.FileApproveLogDTO;
import com.medical.dtms.dto.file.FileAttachmentsDTO;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileService;
import com.medical.dtms.service.manager.file.FileApproveLogManager;
import com.medical.dtms.service.manager.file.FileAttachmentsManager;
import com.medical.dtms.service.manager.file.FileModelManager;
import com.medical.dtms.service.manager.user.QMSUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @version： FileServiceImpl.java v 1.0, 2019年08月27日 11:10 wuxuelin Exp$
 * @Description 文件管理
 **/
@RestController
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private FileModelManager fileManger;
    @Autowired
    private FileApproveLogManager logManager;
    @Autowired
    private FileAttachmentsManager attachmentsManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private QMSUserManager userManager;

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 文件报批
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean addFile(@RequestBody FileModelDTO dto) {
        // 根据文件编号做唯一性校验
        FileModelQuery query = new FileModelQuery();
        query.setFileNo(dto.getFileNo());
        FileModelDTO modelDTO = fileManger.getFileByCondition(query);
        if (null != modelDTO) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件编号重复");
        }

        // 根据文件名做唯一性校验
        query.setFileNo(null);
        query.setFileName(dto.getFileName());
        FileModelDTO file = fileManger.getFileByCondition(query);
        if (null != file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件名重复");
        }

        long fileId = idGenerator.nextId();
        Date date = new Date();
        dto.setBizId(fileId);
        dto.setApplyDate(date);
        // 保存操作
        if (null == dto.getSubmitType() || SubmitTypeEnum.SubmitTypeEnum_1.getType().equals(dto.getSubmitType())) {
            dto.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_1.getStatus());
        }

        // 会签人1
        List<SimpleUserModel> signor1Model = userManager.listUserInfos(dto.getSignator1());
        if (CollectionUtils.isEmpty(signor1Model)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "会签人1 不存在，请重新选择");
        }
        // 会签人2
        List<SimpleUserModel> signor2Model = userManager.listUserInfos(dto.getSignator2());
        if (CollectionUtils.isEmpty(signor2Model)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "会签人2 不存在，请重新选择");
        }

        // 会签人2
        List<SimpleUserModel> approverModel = userManager.listUserInfos(dto.getApproverList());
        if (CollectionUtils.isEmpty(approverModel)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "批准人 不存在，请重新选择");
        }


        //  提交操作
        List<FileApproveLogDTO> logList = new ArrayList<>();
        if (SubmitTypeEnum.SubmitTypeEnum_4.getType().equals(dto.getSubmitType())) {
            // 新增当前提交人的日志表记录
            FileApproveLogDTO logDTO;
            logDTO = new FileApproveLogDTO();
            BeanUtils.copyProperties(dto, logDTO);
            logDTO.setBizId(idGenerator.nextId());
            logDTO.setFileId(fileId);
            logDTO.setBeginDate(date);
            logDTO.setFinshedDate(new Date());
            logDTO.setApproveUserId(dto.getCreatorId());
            logDTO.setApproveUserName(dto.getCreator());
            logDTO.setResult(LogResultEnum.LOG_RESULT_ENUM_4.getResult());
            logDTO.setRemark(Constants.USER_SUBMIT);
            logDTO.setOperatorType(1);
            logList.add(logDTO);

            // 新增下级审批人(即 会签人1)的日志表记录
            for (SimpleUserModel userModel : signor1Model) {
                logDTO = new FileApproveLogDTO();
                BeanUtils.copyProperties(dto, logDTO);
                logDTO.setBizId(idGenerator.nextId());
                logDTO.setFileId(fileId);
                logDTO.setBeginDate(date);
                logDTO.setApproveUserId(String.valueOf(userModel.getUserId()));
                logDTO.setApproveUserName(userModel.getUserName());
                logDTO.setResult(LogResultEnum.LOG_RESULT_ENUM_3.getResult());
                logDTO.setRemark(Constants.USER_SUBMIT);
                logList.add(logDTO);
            }

            try {
                // 保存日志记录到日志表
                logManager.batchInsert(logList);
            } catch (Exception e) {
                log.error("保存日志记录到日志表出错", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }

            // 如果有上传附件，则将附件保存在附件表
            if (StringUtils.isNotBlank(dto.getFileContent())) {
                FileAttachmentsDTO attachmentsDTO;
                List<FileAttachmentsDTO> dtos = new ArrayList<>();
                for (String fileUrl : Arrays.asList(StringUtils.split(dto.getFileContent(), ","))) {
                    attachmentsDTO = new FileAttachmentsDTO();
                    attachmentsDTO.setBizId(idGenerator.nextId());
                    attachmentsDTO.setFileId(fileId);
                    attachmentsDTO.setFilePath(fileUrl);
                    attachmentsDTO.setFileName(fileUrl.substring(fileUrl.lastIndexOf("/")).replace("/", ""));
                    attachmentsDTO.setCreator(dto.getCreator());
                    attachmentsDTO.setCreatorId(dto.getCreatorId());

                    dtos.add(attachmentsDTO);
                }

                try {
                    attachmentsManager.batchInsertFiles(dtos);
                } catch (Exception e) {
                    log.error("保存附件到附件表出错", e);
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
                }
            }

            // 文件状态改为 已提交
            dto.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_2.getStatus());
            dto.setReceiverId(String.valueOf(signor1Model.get(0).getUserId()));
            dto.setReceiverName(signor1Model.get(0).getUserName());
        }

        // 保存到文件表
        try {
            // 默认报批类型为文件新增
            dto.setFileApplyType(FileApplyTypeEnum.FileApplyTypeEnum_1.getType());
            dto.setSignator1Id(JSON.toJSONString(signor1Model));
            dto.setSignator2Id(JSON.toJSONString(signor2Model));
            dto.setApproverId(JSON.toJSONString(approverModel));
            dto.setApplyYear(Calendar.getInstance().get(Calendar.YEAR));
            fileManger.insert(dto);
        } catch (Exception e) {
            log.error("保存文件到文件表出错", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 修改文件
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class})
    public Boolean updateFile(@RequestBody FileModelDTO dto) {
        FileModelDTO file = checkExistOrNot(dto);
        FileModelQuery query;

        // 已提交的无法修改和保存
        if (!FileStatusEnum.FILE_STATUS_ENUM_1.getStatus().equals(file.getFileStatus()) & !FileStatusEnum.FILE_STATUS_ENUM_6.getStatus().equals(file.getFileStatus())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件在退回状态或者待提交状态才允许用户修改！");
        }

        // 根据文件编号做唯一性校验
        query = new FileModelQuery();
        query.setFileNo(dto.getFileNo());
        FileModelDTO dto1 = fileManger.getFileByCondition(query);
        if (StringUtils.equals(dto.getFileNo(), file.getFileNo()) && !dto.getBizId().equals(dto1.getBizId())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件编号重复");
        }

        // 根据文件名做唯一性校验
        query = new FileModelQuery();
        query.setFileName(dto.getFileName());
        FileModelDTO file1 = fileManger.getFileByCondition(query);
        if (StringUtils.equals(dto.getFileName(), file.getFileName()) && !dto.getBizId().equals(file1.getBizId())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件名重复");
        }

        /** 如果是直送
         * 1. 更新当前操作用户的倒数第二条记录
         * 2. 添加会签人的流程记录
         * */
        try {
            if (null != dto.getSubmitType() && SubmitTypeEnum.SubmitTypeEnum_5.getType().equals(dto.getSubmitType())) {
                Date date = new Date();
                // 查询该用户的最近两条记录
                FileApproveLogQuery logQuery = new FileApproveLogQuery();
                logQuery.setFileId(dto.getBizId());
                logQuery.setApproveUserId(dto.getModifierId());
                List<FileApproveLogDTO> logDTOS = logManager.queryLastTwoLog(logQuery);
                if (CollectionUtils.isNotEmpty(logDTOS)) {
                    FileApproveLogDTO logDTO = logDTOS.get(1);
                    logDTO.setResult(LogResultEnum.LOG_RESULT_ENUM_1.getResult());
                    logDTO.setRemark(SubmitTypeEnum.SubmitTypeEnum_5.getName());
                    logDTO.setOperatorType(2);
                    logDTO.setFinshedDate(date);
                    logManager.updateLog(logDTO);
                }

                List<FileApproveLogDTO> logList = new ArrayList<>();
                // 获取会签人
                List<SimpleUserModel> models = userManager.listUserInfos(dto.getSignator1());
                if (CollectionUtils.isEmpty(models)) {
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "会签人1 用户不存在，请重新选择");
                }

                FileApproveLogDTO logDTO;
                for (SimpleUserModel userModel : models) {
                    logDTO = new FileApproveLogDTO();
                    BeanUtils.copyProperties(dto, logDTO, "id", "bizId");
                    logDTO.setBizId(idGenerator.nextId());
                    logDTO.setFileId(dto.getBizId());
                    logDTO.setBeginDate(date);
                    logDTO.setApproveUserId(String.valueOf(userModel.getUserId()));
                    logDTO.setApproveUserName(userModel.getUserName());
                    logDTO.setResult(LogResultEnum.LOG_RESULT_ENUM_3.getResult());
                    logList.add(logDTO);
                }
                logManager.batchInsert(logList);

                dto.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_2.getStatus());
            }
        } catch (BeansException e) {
            log.error("直送时操作流程记录表失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }


        try {
            if (CollectionUtils.isNotEmpty(dto.getSignator1())) {
                List<SimpleUserModel> signor1Model = userManager.listUserInfos(dto.getSignator1());
                if (CollectionUtils.isEmpty(signor1Model)) {
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "会签人1 不存在，请重新选择");
                }
                dto.setSignator1Id(JSON.toJSONString(signor1Model));
            }

            if (CollectionUtils.isNotEmpty(dto.getSignator2())) {
                List<SimpleUserModel> signor2Model = userManager.listUserInfos(dto.getSignator2());
                if (CollectionUtils.isEmpty(signor2Model)) {
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "会签人2 不存在，请重新选择");
                }
                dto.setSignator2Id(JSON.toJSONString(signor2Model));
            }

            if (CollectionUtils.isNotEmpty(dto.getApproverList())) {
                List<SimpleUserModel> approverModel = userManager.listUserInfos(dto.getApproverList());
                if (CollectionUtils.isEmpty(approverModel)) {
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "批准人 不存在，请重新选择");
                }
                dto.setApproverId(JSON.toJSONString(approverModel));
            }

            fileManger.updateFile(dto);
        } catch (Exception e) {
            log.error("文件更新失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 文件删除（逻辑删除）
     **/
    @Override
    public Boolean deleteFile(@RequestBody FileModelDTO dto) {
        FileModelDTO file = checkExistOrNot(dto);

        // 除了未提交的，其他的均不能被删除
        if (!file.getFileStatus().equals(FileStatusEnum.FILE_STATUS_ENUM_1.getStatus())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "文件已提交,不可以删除！！");
        }

        try {
            dto.setIsDeleted(true);
            fileManger.updateFile(dto);
        } catch (Exception e) {
            log.error("文件删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileModel>
     * @description 文件列表（分页展示）
     **/
    @Override
    public PageInfo<FileModel> pageListFile(@RequestBody FileModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileModel> list = fileManger.listFiles(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }

        // 处理状态和文件类型
        for (FileModel model : list) {
            model.setFileStatusName(FileStatusEnum.getNameByStatus(model.getFileStatus()));
            model.setFileApplyTypeName(model.getFileApplyType() == null ? null : FileApplyTypeEnum.getNameByType(model.getFileApplyType()));
        }

        return new PageInfo<>(list);
    }

    /**
     * 分页查询归档/作废文件
     *
     * @param query
     * @return
     */
    @Override
    public PageInfo<ArchiveOrInvalidModel> pageListArchiveOrInvalidFile(@RequestBody FileModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ArchiveOrInvalidModel> models = fileManger.queryListArchiveOrInvalidFile(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * 退回文件（分页展示）
     *
     * @param query
     * @return
     */
    @Override
    public PageInfo<BackFileModel> pageListBackFile(@RequestBody FileModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<BackFileModel> models = fileManger.pageListBackFile(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.model.file.FileDetailModel
     * @description 文件报批详情回显
     **/
    @Override
    public FileDetailModel selectByPrimaryKey(@RequestBody FileModelQuery query) {
        FileDetailModel model = fileManger.selectByPrimaryKey(query.getBizId());
        if (null == model) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条记录不存在");
        }
        if (StringUtils.isNotBlank(model.getSignator1Id())) {
            List<SimpleUserModel> signator1Id = JSON.parseArray(model.getSignator1Id(), SimpleUserModel.class);
            model.setSignator1(signator1Id);
        }
        if (StringUtils.isNotBlank(model.getSignator2Id())) {
            List<SimpleUserModel> signator2Id = JSON.parseArray(model.getSignator2Id(), SimpleUserModel.class);
            model.setSignator2(signator2Id);
        }
        if (StringUtils.isNotBlank(model.getApproverId())) {
            List<SimpleUserModel> approverId = JSON.parseArray(model.getApproverId(), SimpleUserModel.class);
            model.setApproverList(approverId);
        }
        if (StringUtils.isNotBlank(model.getFileContent())) {
            List<String> urls = Arrays.asList(StringUtils.split(model.getFileContent(), ","));
            List<SimpleFileAttachmentModel> list = new ArrayList<>();
            SimpleFileAttachmentModel attachmentModel;
            for (String url : urls) {
                attachmentModel = new SimpleFileAttachmentModel();
                attachmentModel.setFileUrl(url);
                attachmentModel.setFileName(url.substring(url.lastIndexOf("/")).replace("/", ""));
                list.add(attachmentModel);
            }
            model.setFileContentList(list);
        } else {
            model.setFileContentList(new ArrayList<>());
        }

        return model;
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.common.model.file.ArchiveOrInvalidModel>
     * @description
     **/
    @Override
    public List<ArchiveOrInvalidModel> exportArchiveOrInvalidFile(@RequestBody FileModelQuery query) {
        List<ArchiveOrInvalidModel> models = fileManger.queryListArchiveOrInvalidFile(query);
        if (CollectionUtils.isEmpty(models)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "无满足条件的数据");
        }
        return models;
    }

    /**
     * 校验文件是否存在
     */
    private FileModelDTO checkExistOrNot(FileModelDTO dto) {
        FileModelQuery query;
        query = new FileModelQuery();
        query.setBizId(dto.getBizId());
        FileModelDTO file = fileManger.getFileByCondition(query);
        if (null == file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条记录已被删除,无法操作");
        }
        return file;
    }

}
