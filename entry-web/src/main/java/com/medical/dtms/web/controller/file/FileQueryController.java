package com.medical.dtms.web.controller.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.file.ExportModel;
import com.medical.dtms.common.model.file.FileQueryModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.export.ExcelHandlerService;
import com.medical.dtms.dto.file.FileQueryDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileQueryService;
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
 * @version： FileInDeptRoleController.java v 1.0, 2019年08月28日 11:26 wuxuelin Exp$
 * @Description 我的文档、文件查询 页面控制类
 **/
@RestController
public class FileQueryController {

    @Autowired
    private FileQueryService queryService;

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 文件查询 -  新增文件的部门/角色授权
     **/
    @RequestMapping(value = "/query/addFileInDeptRole", method = RequestMethod.POST)
    public Result<Boolean> addFileInDeptRole(@RequestBody FileQueryDTO dto) {
        if (null == dto | CollectionUtils.isEmpty(dto.getRoleIdList()) & CollectionUtils.isEmpty(dto.getDeptIdList())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "角色/部门为未选择");
        }

        if (null == dto.getFileId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }

        OperatorInfo operator = SessionTools.getOperator();
        dto.setCreator(operator.getDspName());
        dto.setCreatorId(operator.getUserId());

        queryService.addFileInDeptRole(dto);

        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 文件查询 - 删除记录
     **/
    @RequestMapping(value = "/query/deleteFileInFileQuery", method = RequestMethod.POST)
    public Result<Boolean> deleteFileInFileQuery(@RequestBody FileQueryDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 不能为空");
        }

        OperatorInfo operator = SessionTools.getOperator();
        dto.setModifier(operator.getDspName());
        dto.setModifierId(operator.getUserId());
        queryService.deleteFileInFileQuery(dto);
        return Result.buildSuccess();
    }

    /**
     * @param [query, request, response]
     * @return void
     * @description 导出文件(导出所有记录)  TODO 调整表格样式
     **/
    @RequestMapping(value = "/query/exportFiles", method = RequestMethod.GET)
    public void exportFiles(FileModelQuery query, HttpServletRequest request, HttpServletResponse response) {
        checkQueryParams(query);
        List<ExportModel> list  = queryService.exportFiles(query);
        // 导出
        String fileName = "文件列表.xls";
        String title = "文件列表";
        String[] header = {"文件编号", " 文件名称", "版本/修订号", "生效日期", "文件类别", "编写部门", " 编写人", " 审核人", "审批人"};
        ExcelHandlerService.handleWorkBook(list, null, title, fileName, header, request, response);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.file.FileQueryModel>>
     * @description 文件管理 - 文件查询 - 分页展示
     **/
    @RequestMapping(value = "/query/pageListQueryFiles", method = RequestMethod.POST)
    public Result<PageInfo<FileQueryModel>> pageListQueryFiles(@RequestBody FileModelQuery query) {
        checkParams(query);
        PageInfo<FileQueryModel> pageInfo = queryService.pageListQueryFiles(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.file.FileQueryModel>>
     * @description 文件管理 - 我的文档 - 分页展示
     **/
    @RequestMapping(value = "/query/pageListMyFiles", method = RequestMethod.POST)
    public Result<PageInfo<FileQueryModel>> pageListMyFiles(@RequestBody FileModelQuery query) {
        checkParams(query);
        OperatorInfo operator = SessionTools.getOperator();
        query.setUserId(operator.getUserId());
        PageInfo<FileQueryModel> pageInfo = queryService.pageListMyFiles(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 参数校验
     *
     * @param query
     */
    private void checkQueryParams(FileModelQuery query) {
        if (null == query) {
            query = new FileModelQuery();
        }
        query.setFileName(
                StringUtils.isBlank(query.getFileName()) == true ? null : query.getFileName().replaceAll("%", "\\\\%"));
        query.setApplyUser(
                StringUtils.isBlank(query.getApplyUser()) == true ? null : query.getApplyUser().replaceAll("%", "\\\\%"));
        query.setAuditorName(
                StringUtils.isBlank(query.getAuditorName()) == true ? null : query.getAuditorName().replaceAll("%", "\\\\%"));
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

        query.setFileName(
                StringUtils.isBlank(query.getFileName()) == true ? null : query.getFileName().replaceAll("%", "\\\\%"));
        query.setApplyUser(
                StringUtils.isBlank(query.getApplyUser()) == true ? null : query.getApplyUser().replaceAll("%", "\\\\%"));
        query.setAuditorName(
                StringUtils.isBlank(query.getAuditorName()) == true ? null : query.getAuditorName().replaceAll("%", "\\\\%"));
    }

}
