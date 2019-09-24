package com.medical.dtms.service.serviceimpl.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.enumeration.file.FileApplyTypeEnum;
import com.medical.dtms.common.enumeration.file.FileStatusEnum;
import com.medical.dtms.common.enumeration.operate.SubmitTypeEnum;
import com.medical.dtms.common.enumeration.result.LogResultEnum;
import com.medical.dtms.common.model.file.FileApprovalModel;
import com.medical.dtms.common.model.file.FileAttachmentModel;
import com.medical.dtms.common.model.file.FileReceiveModel;
import com.medical.dtms.common.model.user.SimpleUserModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.dept.QMSDeptDTO;
import com.medical.dtms.dto.file.FileApproveLogDTO;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.FileQueryDTO;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.dto.file.query.FileInDeptRoleQuery;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileApprovalService;
import com.medical.dtms.service.manager.dept.QMSDeptManager;
import com.medical.dtms.service.manager.file.FileApproveLogManager;
import com.medical.dtms.service.manager.file.FileAttachmentsManager;
import com.medical.dtms.service.manager.file.FileModelManager;
import com.medical.dtms.service.manager.file.FileQueryManager;
import com.medical.dtms.service.manager.user.QMSUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version： FileApprovalServiceImpl.java v 1.0, 2019年08月29日 16:04 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class FileApprovalServiceImpl implements FileApprovalService {

    @Autowired
    private FileModelManager fileModelManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private FileApproveLogManager logManager;
    @Autowired
    private FileQueryManager queryManager;
    @Autowired
    private QMSDeptManager deptManager;
    @Autowired
    private FileAttachmentsManager attachmentsManager;
    @Autowired
    private QMSUserManager userManager;

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApprovalModel>
     * @description 文件审批 - 列表展示（分页，展示 会签人 为当前登陆人的,并且状态为 待审批的）
     **/
    @Override
    public PageInfo<FileApprovalModel> pageListFilesInApproval(@RequestBody FileModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileApprovalModel> models = fileModelManager.pageListFilesInApproval(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }

        // 如果该文件最近一次为退回，表明是退回后 点击 直送的，此时在该列表展示的状态为 待审批
        // 如果该文件无退回记录，则展示状态为 待审核
        List<Long> fileIds = models.stream().map(FileApprovalModel::getBizId).distinct().collect(Collectors.toList());
//        List<FileApprovalModel> modelList = logManager.listLogs(fileIds);
        for (FileApprovalModel model : models) {
            if (null != model.getFileStatus() && FileStatusEnum.FILE_STATUS_ENUM_2.getStatus().equals(model.getFileStatus())) {
//                model.setFileStatusName(FileStatusEnum.FILE_STATUS_ENUM_7.getName());
            }

            model.setFileApplyTypeName(model.getFileApplyType() == null ? null : FileApplyTypeEnum.getNameByType(model.getFileApplyType()));
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 文件审批
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class, Exception.class})
    public Boolean startFileApproval(@RequestBody FileModelDTO dto) {
        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getBizId());
        FileModelDTO file = fileModelManager.getFileByCondition(query);
        if (null == file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "记录被删除,无法操作");
        }

        // 更新关于该条记录的日志记录(待处理的)
        FileApproveLogQuery query1 = new FileApproveLogQuery();
        query1.setFileId(dto.getBizId());
//        query1.setEmpty(true);
        List<FileApproveLogDTO> list = logManager.listLogs(query1);
        // 过滤出未处理的记录
        List<FileApproveLogDTO> dtos = list.stream().filter(fileApproveLogDTO -> LogResultEnum.LOG_RESULT_ENUM_3.getResult().equals(fileApproveLogDTO.getResult())).collect(Collectors.toList());
        // 过滤出已同意的
        List<FileApproveLogDTO> agreeDtos = list.stream().filter(fileApproveLogDTO -> LogResultEnum.LOG_RESULT_ENUM_1.getResult().equals(fileApproveLogDTO.getResult())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(dtos)) {
            // 获取当前文件所有未审批的流程记录 id
            List<Long> totalBizIds = dtos.stream().map(FileApproveLogDTO::getBizId).distinct().collect(Collectors.toList());
            // 过滤出处理人为当前登录人的
            List<Long> bizIds = dtos.stream().filter(a -> StringUtils.equals(a.getApproveUserId(), dto.getModifierId())).map(FileApproveLogDTO::getBizId).distinct().collect(Collectors.toList());
            Date date = new Date();
            /** 会签人1 点击了 退回
             *  1. 更新流程记录表该条记录的数据
             *  （1）到达时间：之前提交时已添加，不用动
             *  （2）完成时间为 当前操作时间
             *  （3）处理人：所有会签人,之前已添加，不用动
             *  （4）结果：退回
             *  （5）意见：当前登陆人为 会签人时，为 审核意见，如果不是，则为 其他人已退回,系统自动关闭
             * 2. 1 中更新的同时 新增一条下一步即申请人的日志记录，
             *
             * 3. 更新文件状态为 退回的同时更新该条记录
             *
             * */
            if (SubmitTypeEnum.SubmitTypeEnum_3.getType().equals(dto.getSubmitType())) {

                FileApproveLogDTO logDTO = new FileApproveLogDTO();
                BeanUtils.copyProperties(dto, logDTO, "bizId", "id");
                setFileNameNoVersion(dto, file, logDTO);

                logDTO.setFileId(dto.getBizId());
                logDTO.setFinshedDate(date);
                logDTO.setResult(LogResultEnum.LOG_RESULT_ENUM_2.getResult());
                logDTO.setRemark(dto.getApproverContent());
                logDTO.setBizIdList(bizIds);
                logDTO.setOperatorType(2);
                logManager.updateLogByIds(logDTO);

                totalBizIds.removeAll(bizIds);
                FileApproveLogDTO leftLog = new FileApproveLogDTO();
                BeanUtils.copyProperties(dto, leftLog, "bizId", "id");
                setFileNameNoVersion(dto, file, leftLog);

                leftLog.setFileId(dto.getBizId());
                leftLog.setFinshedDate(date);
                leftLog.setResult(LogResultEnum.LOG_RESULT_ENUM_2.getResult());
                leftLog.setRemark(Constants.REMARK);
                leftLog.setBizIdList(totalBizIds);
                leftLog.setOperatorType(2);

                logManager.updateLogByIds(leftLog);


                // 新增申请人记录
                FileApproveLogDTO applyUserLog = new FileApproveLogDTO();
                BeanUtils.copyProperties(dto, applyUserLog, "bizId", "id");
                setFileNameNoVersion(dto, file, applyUserLog);
                applyUserLog.setBizId(idGenerator.nextId());
                applyUserLog.setFileId(dto.getBizId());
                applyUserLog.setBeginDate(date);
                applyUserLog.setApproveUserId(file.getCreatorId());
                applyUserLog.setApproveUserName(file.getCreator());
                applyUserLog.setResult(LogResultEnum.LOG_RESULT_ENUM_3.getResult());
                logManager.insert(applyUserLog);
                applyUserLog.setOperatorType(2);

                dto.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_6.getStatus());
            }

            /** 会签人点了 委托
             * 1. 该条记录会签人1 更新为 被委托人
             * 2. 新增一条流程记录：
             * */
            if (SubmitTypeEnum.SubmitTypeEnum_6.getType().equals(dto.getSubmitType())) {
                // 更新该条记录会签人为被委托人
                SimpleUserModel userModel = new SimpleUserModel();
                userModel.setUserId(dto.getReceiveUserId());
                userModel.setUserName(dto.getReceiveUserName());
                List<SimpleUserModel> userModels = new ArrayList<>();
                userModels.add(userModel);
                dto.setSignator1Id(JSON.toJSONString(userModels));

                // 更新最新流程记录为委托，同时意见为：委托给xxx处理
                FileApproveLogQuery logQuery = new FileApproveLogQuery();
                logQuery.setFileId(dto.getBizId());
                logQuery.setApproveUserId(dto.getModifierId());
                List<FileApproveLogDTO> logDTOS = logManager.queryLastTwoLog(logQuery);
                if (CollectionUtils.isNotEmpty(logDTOS)) {
                    FileApproveLogDTO logDTO = logDTOS.get(0);
                    logDTO.setFinshedDate(date);
                    logDTO.setResult(LogResultEnum.LOG_RESULT_ENUM_5.getResult());
                    logDTO.setOperatorType(2);
                    logDTO.setRemark(Constants.MANDATORY.replace("receiveUserName", dto.getReceiveUserName()));
                    logManager.updateLog(logDTO);
                }

                // 新增一条流程记录
                FileApproveLogDTO applyUserLog = new FileApproveLogDTO();
                BeanUtils.copyProperties(dto, applyUserLog, "bizId", "id");
                setFileNameNoVersion(dto, file, applyUserLog);
                applyUserLog.setBizId(idGenerator.nextId());
                applyUserLog.setFileId(dto.getBizId());
                applyUserLog.setBeginDate(date);
                applyUserLog.setApproveUserId(String.valueOf(dto.getReceiveUserId()));
                applyUserLog.setApproveUserName(dto.getReceiveUserName());
                applyUserLog.setResult(LogResultEnum.LOG_RESULT_ENUM_3.getResult());
                applyUserLog.setOperatorType(2);
                logManager.insert(applyUserLog);
            }


            /** 审批通过
             *  1. 会签人1、2、批准人均只有一人并且均为当前登陆人，此时同意，则全部同意，文件生效
             *  2. 三种角色人员不同，需要新增他人 待处理 流程记录
             * */
            if (SubmitTypeEnum.SubmitTypeEnum_2.getType().equals(dto.getSubmitType())) {

                // 获取会签人1、2、批准人 id、name
                List<SimpleUserModel> signator1;
                List<SimpleUserModel> signator2;
                List<SimpleUserModel> approverList;

//                signator1 = userManager.listUserInfos(file.getSignator1());
//                if (CollectionUtils.isEmpty(signator1)) {
//
//                }
                signator1 = JSONArray.parseArray(file.getSignator1Id(), SimpleUserModel.class);

//                signator2 = userManager.listUserInfos(file.getSignator2());
//                if (CollectionUtils.isEmpty(signator2)) {
//
//                }
                signator2 = JSONArray.parseArray(file.getSignator2Id(), SimpleUserModel.class);

//                approverList = userManager.listUserInfos(file.getApproverList());
//                if (CollectionUtils.isEmpty(approverList)) {
//
//                }
                approverList = JSONArray.parseArray(file.getApproverId(), SimpleUserModel.class);

                List<Long> signator1UserIds = signator1.stream().map(SimpleUserModel::getUserId).collect(Collectors.toList());
                List<Long> signator2UserIds = signator2.stream().map(SimpleUserModel::getUserId).collect(Collectors.toList());
                List<Long> approverUserIds = approverList.stream().map(SimpleUserModel::getUserId).collect(Collectors.toList());

                // 无论其他人有没有处理，都将当前登陆人的处理意见改为 同意 S
                // 开始时间不为空的
                List<FileApproveLogDTO> logDTOS = dtos.stream().filter(dto1 -> StringUtils.equals(dto1.getApproveUserId(), dto.getModifierId())).distinct().collect(Collectors.toList());
                List<Long> beginIds = logDTOS.stream().filter(fileLog -> null != fileLog.getBeginDate()).map(FileApproveLogDTO::getBizId).distinct().collect(Collectors.toList());

                startTimeIsNotEmpty(dto, date, beginIds, file);

                List<Long> myBizIds = new ArrayList<>();
                myBizIds.addAll(bizIds);

                bizIds.removeAll(beginIds);
                handlerLeft(dto, date, bizIds, file);

                // 无论其他人有没有处理，都将当前登陆人的处理意见改为 同意 S

                if (1 == myBizIds.size()) {
                    // 判断会签人1 是否全部处理了
                    List<FileApproveLogDTO> disAggreeLog = new ArrayList<>();
                    List<FileApproveLogDTO> agreeLog = new ArrayList<>();
                    signator1UserIds.remove(dto.getModifierId());
                    for (Long userId : signator1UserIds) {
                        disAggreeLog = dtos.stream().filter(fileApproveLogDTO -> StringUtils.equals(fileApproveLogDTO.getApproveUserId(), String.valueOf(userId))).collect(Collectors.toList());
                    }

                    // 会签人1 全部处理了
                    if (CollectionUtils.isEmpty(disAggreeLog)) {
                        // 判断会签人2 是否全部通过
                        signator2UserIds.removeAll(signator1UserIds);
                        signator2UserIds.remove(dto.getModifierId());
                        for (Long signator2UserId : signator2UserIds) {
                            disAggreeLog = dtos.stream().filter(fileApproveLogDTO -> StringUtils.equals(fileApproveLogDTO.getApproveUserId(), String.valueOf(signator2UserId))).collect(Collectors.toList());
                            if (CollectionUtils.isNotEmpty(agreeDtos)) {
                                agreeLog = agreeDtos.stream().filter(fileApproveLogDTO -> !StringUtils.equals(fileApproveLogDTO.getApproveUserId(),  String.valueOf(signator2UserId))).collect(Collectors.toList());
                            }
                        }
                        // 会签人2 全部通过 或者还没到
                        if (CollectionUtils.isEmpty(disAggreeLog)) {
                            // 还没到，或者 仅此一人此时新增会签人2的流程记录
                            if (CollectionUtils.isEmpty(agreeLog)) {
                                if (CollectionUtils.isNotEmpty(signator2UserIds)) {
                                    return setData(dto, date, signator2, file);
                                }
                            }

                            approverUserIds.removeAll(signator1UserIds);
                            approverUserIds.removeAll(signator2UserIds);
                            approverUserIds.remove(dto.getModifierId());
                            // 判断批准人是否全部通过
                            for (Long approverUserId : approverUserIds) {
                                disAggreeLog = dtos.stream().filter(fileApproveLogDTO -> !StringUtils.equals(fileApproveLogDTO.getApproveUserId(),  String.valueOf(approverUserId))).collect(Collectors.toList());
                                if (CollectionUtils.isNotEmpty(agreeDtos)) {
                                    agreeLog = agreeDtos.stream().filter(fileApproveLogDTO -> !StringUtils.equals(fileApproveLogDTO.getApproveUserId(), String.valueOf(approverUserId))).collect(Collectors.toList());
                                }
                            }
                            // 批准人全部未操作 或者还没到
                            if (CollectionUtils.isEmpty(disAggreeLog)) {
                                // 还没到或者仅此一人，此时新增审批人的流程记录
                                if (CollectionUtils.isEmpty(agreeLog)) {
                                    if (CollectionUtils.isNotEmpty(approverUserIds)) {
                                        return setData(dto, date, approverList, file);
                                    } else {
                                        dto.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_3.getStatus());
                                        dto.setEffectDate(new Date());
                                        return updateFileInfo(dto);
                                    }
                                }
                            }

                            // 还有其他人未通过，则返回 true
                            return true;
                        } else if (disAggreeLog.size() == signator2UserIds.size()) {
                            return setData(dto, date, signator2, file);
                        }
                    } else if (disAggreeLog.size() == signator1UserIds.size()) {
                        return setData(dto, date, signator1, file);
                    }
                }
            }

        }
        return updateFileInfo(dto);

    }


    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileReceiveModel>
     * @description 文件接收列表查询（分页）
     **/
    @Override
    public PageInfo<FileReceiveModel> pageListForReceive(@RequestBody FileModelQuery query) {
        if (null != query.getReceiveDeptId()) {
            FileInDeptRoleQuery roleQuery = new FileInDeptRoleQuery();
            List<Long> deptIdList = new ArrayList<>();
            deptIdList.add(query.getReceiveDeptId());
            roleQuery.setDeptIdList(deptIdList);
            List<FileQueryDTO> dtos = queryManager.listFileInDeptRole(roleQuery);
            if (CollectionUtils.isEmpty(dtos)) {
                return new PageInfo<>(new ArrayList<>());
            }
            List<Long> fileIds = dtos.stream().map(FileQueryDTO::getFileId).distinct().collect(Collectors.toList());
            query.setFileIds(fileIds);
        }

        // 查询接收部门name
        QMSDeptDTO dto = deptManager.selectByPrimaryKey(query.getReceiveDeptId());

        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileReceiveModel> list = fileModelManager.pageListForReceive(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }
        for (FileReceiveModel model : list) {
            model.setReceiveDeptName(dto == null ? null : StringUtils.isBlank(dto.getDeptName()) == true ? null : dto.getDeptName());
        }

        return new PageInfo<>(list);
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 文件接收 - 确认接收
     **/
    @Override
    public Boolean confirmReceive(@RequestBody FileModelDTO dto) {
        // 重复接收校验
        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getBizId());
        FileModelDTO file = fileModelManager.getFileByCondition(query);
        if (null == file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "记录被删除,无法操作");
        }

        if (null != file.getIsReceived() && file.getIsReceived()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "已经接收，请勿重复操作");
        }

        dto.setIsReceived(true);
        dto.setReceiveDate(new Date());
        dto.setReceiverId(dto.getModifierId());
        dto.setReceiverName(dto.getModifier());

        try {
            fileModelManager.updateFile(dto);
        } catch (Exception e) {
            log.error("文件接收 - 确认接收失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.dto.file.FileAttachmentsDTO>
     * @description 文件附件列表
     **/
    @Override
    public List<FileAttachmentModel> pageListFileAttach(@RequestBody FileModelQuery query) {
        List<FileAttachmentModel> models = attachmentsManager.pageListFileAttach(query);
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        for (FileAttachmentModel model : models) {
            String attachmentFormat = model.getAttachmentName().substring(model.getAttachmentName().lastIndexOf("."));
            model.setAttachmentFormat(attachmentFormat);
        }
        return models;
    }

    /**
     * 封装待审批人流程记录
     */
    private Boolean setData(FileModelDTO dto, Date date, List<SimpleUserModel> models, FileModelDTO file) {
        List<FileApproveLogDTO> logList = new ArrayList<>();
        FileApproveLogDTO logDTO;
        for (SimpleUserModel model : models) {
            if (!StringUtils.equals(String.valueOf(model.getUserId()), dto.getModifierId())) {
                logDTO = new FileApproveLogDTO();
                BeanUtils.copyProperties(dto, logDTO, "id", "bizId");
                setFileNameNoVersion(dto, file, logDTO);
                logDTO.setBizId(idGenerator.nextId());
                logDTO.setFileId(dto.getBizId());
                logDTO.setBeginDate(date);
                logDTO.setApproveUserId(String.valueOf(model.getUserId()));
                logDTO.setApproveUserName(model.getUserName());
                logDTO.setResult(LogResultEnum.LOG_RESULT_ENUM_3.getResult());
                logDTO.setOperatorType(2);
                logList.add(logDTO);
            }
        }

        if (CollectionUtils.isNotEmpty(logList)) {
            try {
                logManager.batchInsert(logList);
            } catch (Exception e) {
                log.error("流程记录表新增失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }
        return true;
    }

    /**
     * 赋值数据
     */
    private void setFileNameNoVersion(FileModelDTO dto, FileModelDTO file, FileApproveLogDTO logDTO) {
        if (StringUtils.isBlank(dto.getFileName())) {
            logDTO.setFileName(file.getFileName());
        }
        if (StringUtils.isBlank(dto.getFileNo())) {
            logDTO.setFileNo(file.getFileNo());
        }
        if (StringUtils.isBlank(dto.getFileVersion())) {
            logDTO.setFileVersion(file.getFileVersion());
        }
    }


    /**
     * 开始时间为空的，开始时间、结束时间都设置为当前时间
     */
    private void handlerLeft(FileModelDTO dto, Date date, List<Long> alreadyIds, FileModelDTO file) {
        if (CollectionUtils.isNotEmpty(alreadyIds)) {
            FileApproveLogDTO leftLog = new FileApproveLogDTO();
            BeanUtils.copyProperties(dto, leftLog, "bizId", "id");
            setFileNameNoVersion(dto, file, leftLog);
            leftLog.setFileId(dto.getBizId());
            leftLog.setBeginDate(date);
            leftLog.setFinshedDate(date);
            leftLog.setResult(LogResultEnum.LOG_RESULT_ENUM_1.getResult());
            leftLog.setRemark(dto.getApproverContent());
            leftLog.setBizIdList(alreadyIds);
            leftLog.setOperatorType(2);

            logManager.updateLogByIds(leftLog);
        }
    }

    /**
     * 开始时间不为空的，只将结束时间设置为当前时间
     */
    private void startTimeIsNotEmpty(FileModelDTO dto, Date date, List<Long> alreadyIds, FileModelDTO file) {
        if (CollectionUtils.isNotEmpty(alreadyIds)) {
            FileApproveLogDTO alreadyLog = new FileApproveLogDTO();
            BeanUtils.copyProperties(dto, alreadyLog, "bizId", "id");
            setFileNameNoVersion(dto, file, alreadyLog);
            alreadyLog.setFileId(dto.getBizId());
            alreadyLog.setFinshedDate(date);
            alreadyLog.setResult(LogResultEnum.LOG_RESULT_ENUM_1.getResult());
            alreadyLog.setRemark(dto.getApproverContent());
            alreadyLog.setBizIdList(alreadyIds);
            alreadyLog.setOperatorType(2);
            logManager.updateLogByIds(alreadyLog);
        }
    }

    /**
     * 更新文件状态
     */
    private Boolean updateFileInfo(FileModelDTO dto) {
        try {
            fileModelManager.updateFile(dto);
        } catch (Exception e) {
            log.error("文件更新失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }
}
