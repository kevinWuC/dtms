package com.medical.dtms.web.controller.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.file.ApplyCheckModel;
import com.medical.dtms.common.model.file.FileApplyDetailModel;
import com.medical.dtms.common.model.file.FileApplyModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.file.FileApplyDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @version： FileApplyController.java v 1.0, 2019年08月30日 14:42 wuxuelin Exp$
 * @Description 申请控制类
 **/
@RestController
public class FileApplyController {

    @Autowired
    private FileApplyService applyService;

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 申请修改/作废
     **/
    @RequestMapping(value = "/apply/addFileApply", method = RequestMethod.POST)
    public Result<Boolean> addFileApply(@RequestBody FileApplyDTO dto) {
        if (null == dto || StringUtils.isBlank(dto.getContent())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "修改内容不能为空");
        }
        if (null == dto.getFileId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件 id 不能为空");
        }

        OperatorInfo info = SessionTools.getOperator();
        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());
        dto.setMakeDeptId(info.getDeptId());
        dto.setMakeDeptName(info.getDeptName());
        applyService.addFileApply(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.file.FileApplyModel>>
     * @description 修改申请列表（分页）
     **/
    @RequestMapping(value = "/apply/pageListApply", method = RequestMethod.POST)
    public Result<PageInfo<FileApplyModel>> pageListApply(@RequestBody FileModelQuery query) {
        checkParams(query);
        PageInfo<FileApplyModel> pageInfo = applyService.pageListApply(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 申请审核
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/apply/agreeOrDisThisApply", method = RequestMethod.POST)
    public Result<Boolean> agreeOrDisThisApply(@RequestBody FileApplyDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "申请id为空");
        }
        if (null == dto.getFileId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        OperatorInfo operator = SessionTools.getOperator();
        dto.setModifier(operator.getDspName());
        dto.setModifierId(operator.getUserId());

        applyService.agreeOrDisThisApply(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 校验 修改/作废申请 是否合法
     **/
    @RequestMapping(value = "/apply/checkOptionLegalOrNot", method = RequestMethod.POST)
    public Result<Boolean> checkOptionLegalOrNot(@RequestBody FileApplyDTO dto) {
        if (null == dto || null == dto.getFileId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        applyService.checkOptionLegalOrNot(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.file.ApplyCheckModel>>
     * @description 申请审核 分页查询
     **/
    @RequestMapping(value = "/apply/pageListApplyCheck", method = RequestMethod.POST)
    public Result<PageInfo<ApplyCheckModel>> pageListApplyCheck(@RequestBody FileModelQuery query) {
        checkParams(query);
        PageInfo<ApplyCheckModel> pageInfo = applyService.pageListApplyCheck(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.medical.dtms.common.model.file.FileApplyDetailModel>
     * @description 申请详情回显
     **/
    @RequestMapping(value = "/apply/selectByPrimaryKey", method = RequestMethod.POST)
    public Result<FileApplyDetailModel> selectByPrimaryKey(@RequestBody FileModelQuery query) {
        if (null == query || null == query.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }
        FileApplyDetailModel model = applyService.selectByPrimaryKey(query);
        return Result.buildSuccess(model);
    }


    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(FileModelQuery query) {
        if (null == query) {
            query = new FileModelQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }

}
