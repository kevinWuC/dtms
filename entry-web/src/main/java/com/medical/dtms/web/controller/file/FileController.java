package com.medical.dtms.web.controller.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.enumeration.file.FileStatusEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.file.ArchiveOrInvalidModel;
import com.medical.dtms.common.model.file.BackFileModel;
import com.medical.dtms.common.model.file.FileDetailModel;
import com.medical.dtms.common.model.file.FileModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.export.ExcelHandlerService;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version： FileController.java v 1.0, 2019年08月23日 18:28 wuxuelin Exp$
 * @Description 文件管理控制类
 **/
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 文件报批
     **/
    @RequestMapping(value = "/file/addFile", method = RequestMethod.POST)
    public Result<Boolean> addFile(@RequestBody FileModelDTO dto) {
        if (null == dto) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "内容为空，请填写完整");
        }
        Result<Boolean> x = checkParams(dto);
        if (x != null) return x;

        OperatorInfo operator = SessionTools.getOperator();
        dto.setCreator(operator.getDspName());
        dto.setCreatorId(operator.getUserId());
        dto.setApplyUserId(operator.getBizId());
        fileService.addFile(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 编辑/直送文件
     **/
    @RequestMapping(value = "/file/updateFile", method = RequestMethod.POST)
    public Result<Boolean> updateFile(@RequestBody FileModelDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }
        Result<Boolean> x = checkParams(dto);
        if (x != null) return x;

        OperatorInfo operator = SessionTools.getOperator();
        dto.setModifier(operator.getDspName());
        dto.setModifierId(operator.getUserId());
        fileService.updateFile(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 删除文件（逻辑删除）
     **/
    @RequestMapping(value = "/file/deleteFile", method = RequestMethod.POST)
    public Result<Boolean> deleteFile(@RequestBody FileModelDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }

        OperatorInfo operator = SessionTools.getOperator();
        dto.setModifier(operator.getDspName());
        dto.setModifierId(operator.getUserId());
        fileService.deleteFile(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.file.FileModel>>
     * @description 文件报批 - 文件列表（分页展示，只展示申请人 为当前登陆人的）
     **/
    @RequestMapping(value = "/file/pageListFile", method = RequestMethod.POST)
    public Result<PageInfo<FileModel>> pageListFile(@RequestBody FileModelQuery query) {
        OperatorInfo operator = SessionTools.getOperator();
        checkQueryParams(query);
        query.setApplyUserId(operator.getBizId());
        PageInfo<FileModel> pageInfo = fileService.pageListFile(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 归档文件 （分页展示）
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/file/queryListArchiveFile", method = RequestMethod.POST)
    public Result<PageInfo<ArchiveOrInvalidModel>> queryListArchiveFile(@RequestBody FileModelQuery query) {
        checkQueryParams(query);
        query.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_4.getStatus());
        PageInfo<ArchiveOrInvalidModel> pageInfo = fileService.pageListArchiveOrInvalidFile(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 归档文件导出
     */
    @RequestMapping(value = "/file/exportArchiveFile", method = RequestMethod.GET)
    public void exportArchiveFile(FileModelQuery query, HttpServletRequest request, HttpServletResponse response) {
        checkQueryParams(query);
        query.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_4.getStatus());
        List<ArchiveOrInvalidModel> models = fileService.exportArchiveOrInvalidFile(query);
        // 导出
        String fileName = "文件列表.xls";
        String title = "文件列表";
        String[] header = {"文件编号", " 文件名称", "版本/修订号", "生效日期", "编写部门", "归档日期", " 归档人"};
        ExcelHandlerService.handlerArchiveOrInvalidFile(models, null, title, fileName, header, request, response);
    }

    /**
     * 作废文件导出
     */
    @RequestMapping(value = "/file/exportInvalidFile", method = RequestMethod.GET)
    public void exportInvalidFile(FileModelQuery query, HttpServletRequest request, HttpServletResponse response) {
        checkQueryParams(query);
        query.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_5.getStatus());
        List<ArchiveOrInvalidModel> models = fileService.exportArchiveOrInvalidFile(query);
        // 导出
        String fileName = "文件列表.xls";
        String title = "文件列表";
        String[] header = {"文件编号", " 文件名称", "版本/修订号", "生效日期", "编写部门", "作废日期", " 作废人"};
        ExcelHandlerService.handlerArchiveOrInvalidFile(models, null, title, fileName, header, request, response);
    }


    /**
     * 作废文件 （分页展示）
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/file/queryListInvalidFile", method = RequestMethod.POST)
    public Result<PageInfo<ArchiveOrInvalidModel>> queryListInvalidFile(@RequestBody FileModelQuery query) {
        checkQueryParams(query);
        query.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_5.getStatus());
        PageInfo<ArchiveOrInvalidModel> pageInfo = fileService.pageListArchiveOrInvalidFile(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 退回文件（分页展示）
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/file/pageListBackFile", method = RequestMethod.POST)
    public Result<PageInfo<BackFileModel>> pageListBackFile(@RequestBody FileModelQuery query) {
        checkQueryParams(query);
        query.setFileStatus(FileStatusEnum.FILE_STATUS_ENUM_6.getStatus());
        PageInfo<BackFileModel> pageInfo = fileService.pageListBackFile(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.medical.dtms.dto.file.FileModelDTO>
     * @description 文件报批回显
     **/
    @RequestMapping(value = "/file/selectByPrimaryKey", method = RequestMethod.POST)
    public Result<FileDetailModel> selectByPrimaryKey(@RequestBody FileModelQuery query) {
        if (null == query || null == query.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }
        FileDetailModel model = fileService.selectByPrimaryKey(query);
        return Result.buildSuccess(model);
    }


    /**
     * 参数非空校验
     */
    private Result<Boolean> checkParams(FileModelDTO dto) {
        if (StringUtils.isBlank(dto.getFileNo()) || StringUtils.isBlank(dto.getFileName()) || StringUtils.isBlank(dto.getFileInfo()) || StringUtils.isBlank(dto.getFileVersion())
                || null == dto.getIsAnnualVerification()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件编号/文件名称/编写依据/版本号/是否年审 不能为空，请填写完整");
        }
        if (dto.getIsAnnualVerification() && null == dto.getAnnualVerificationDeptId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "年审部门未选择");
        }
        if (CollectionUtils.isEmpty(dto.getSignator1()) && CollectionUtils.isEmpty(dto.getSignator2())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "审批人或者会签人或者批准人不可为空！");
        }
        return null;
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
