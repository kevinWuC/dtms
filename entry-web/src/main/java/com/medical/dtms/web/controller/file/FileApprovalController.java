package com.medical.dtms.web.controller.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.file.FileApprovalModel;
import com.medical.dtms.common.model.file.FileAttachmentModel;
import com.medical.dtms.common.model.file.FileReceiveModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.file.FileAttachmentsDTO;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileApprovalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version： FileApprovalController.java v 1.0, 2019年08月29日 16:02 wuxuelin Exp$
 * @Description 文件审批、文件接收控制类
 **/
@RestController
public class FileApprovalController {

    @Autowired
    private FileApprovalService approvalService;

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.file.FileModel>>
     * @description 文件审批 - 列表展示（分页，展示 审批人 为当前登陆人的,并且状态为 待审批的(即 已提交的)）
     **/
    @RequestMapping(value = "/approval/pageListFilesInApproval", method = RequestMethod.POST)
    public Result<PageInfo<FileApprovalModel>> pageListFilesInApproval(@RequestBody FileModelQuery query) {
        checkQueryParams(query);
        OperatorInfo operator = SessionTools.getOperator();
        query.setSignor1Id(operator.getUserId());
        query.setSignator2Id(operator.getUserId());
        query.setApproverId(operator.getUserId());
        PageInfo<FileApprovalModel> pageInfo = approvalService.pageListFilesInApproval(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 文件审批
     **/
    @RequestMapping(value = "/approval/startFileApproval", method = RequestMethod.POST)
    public Result<Boolean> startFileApproval(@RequestBody FileModelDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        OperatorInfo operator = SessionTools.getOperator();
        if (null != dto.getReceiveUserId() && dto.getReceiveUserId().equals(operator.getBizId())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "不能委托给自己，谢谢！");
        }

        dto.setModifierId(operator.getUserId());
        dto.setModifier(operator.getDspName());
        approvalService.startFileApproval(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.dto.file.FileAttachmentsDTO>>
     * @description 文件附件
     **/
    @RequestMapping(value = "/approval/pageListFileAttach", method = RequestMethod.POST)
    public Result<List<FileAttachmentsDTO>> pageListFileAttach(@RequestBody FileModelQuery query) {
        if (null == query || null == query.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        List<FileAttachmentModel> dtos = approvalService.pageListFileAttach(query);
        return Result.buildSuccess(dtos);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.file.FileReceiveModel>>
     * @description 文件接收列表查询（分页）
     **/
    @RequestMapping(value = "/receive/pageListForReceive", method = RequestMethod.POST)
    public Result<PageInfo<FileReceiveModel>> pageListForReceive(@RequestBody FileModelQuery query) {
        checkQueryParams(query);
        PageInfo<FileReceiveModel> pageInfo = approvalService.pageListForReceive(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 文件接收 - 确认接收
     **/
    @RequestMapping(value = "/receive/confirmReceive", method = RequestMethod.POST)
    public Result<Boolean> confirmReceive(@RequestBody FileModelDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        OperatorInfo operator = SessionTools.getOperator();
        dto.setModifierId(operator.getUserId());
        dto.setModifier(operator.getDspName());
        approvalService.confirmReceive(dto);
        return Result.buildSuccess(true);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkQueryParams(FileModelQuery query) {
        if (null == query) {
            query = new FileModelQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
        query.setFileName(
                StringUtils.isBlank(query.getFileName()) == true ? null : query.getFileName().replaceAll("%", "\\\\%"));
        query.setApplyUser(
                StringUtils.isBlank(query.getApplyUser()) == true ? null : query.getApplyUser().replaceAll("%", "\\\\%"));
    }
}
