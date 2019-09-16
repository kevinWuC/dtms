package com.medical.dtms.web.controller.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.file.FileYearModel;
import com.medical.dtms.common.model.file.FileYearRecordExportModel;
import com.medical.dtms.common.model.file.FileYearRecordModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.export.ExcelHandlerService;
import com.medical.dtms.dto.file.FileYearDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileYearService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version： FileYearController.java v 1.0, 2019年08月28日 18:28 wuxuelin Exp$
 * @Description 文件年审控制类
 **/
@RestController
public class FileYearController {

    @Autowired
    private FileYearService yearService;

    /**
     * 新增文件年审
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/year/insertFileYear", method = RequestMethod.POST)
    public Result<Boolean> insertFileYear(@RequestBody FileYearDTO dto) {
        if (null == dto || null == dto.getFileId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }

        if (StringUtils.isBlank(dto.getVersionEffectInfo()) || StringUtils.isBlank(dto.getAdvancementInfo()) || StringUtils.isBlank(dto.getFormatInfo())
                || StringUtils.isBlank(dto.getApplicabilityInfo()) || StringUtils.isBlank(dto.getOperabilityInfo())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "修订内容模块有内容为空");
        }

        OperatorInfo operator = SessionTools.getOperator();
        dto.setCreator(operator.getDspName());
        dto.setCreatorId(operator.getUserId());

        yearService.insertFileYear(dto);

        return Result.buildSuccess(true);
    }

    /**
     * 年审记录（分页展示）
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/year/pageListFileYearRecord", method = RequestMethod.POST)
    public Result<PageInfo<FileYearRecordModel>> pageListFileYearRecord(@RequestBody FileModelQuery query) {
        checkQueryParams(query);
        PageInfo<FileYearRecordModel> pageInfo = yearService.pageListFileYearRecord(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 文件年审（分页展示）
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/year/pageListFileYear", method = RequestMethod.POST)
    public Result<PageInfo<FileYearModel>> pageListFileYear(@RequestBody FileModelQuery query) {
        checkQueryParams(query);
        PageInfo<FileYearModel> pageInfo = yearService.pageListFileYear(query);
        return Result.buildSuccess(pageInfo);
    }


    /**
     * 年审记录 导出
     */
    @RequestMapping(value = "/file/exportFileYearRecord", method = RequestMethod.GET)
    public void exportFileYearRecord(FileModelQuery query, HttpServletRequest request, HttpServletResponse response) {
        checkQueryParams(query);
        List<FileYearRecordExportModel> models = yearService.exportFileYearRecord(query);
        // 导出
        String fileName = "文件列表.xls";
        String title = "文件列表";
        String[] header = {
                "文件编号", " 文件名称", "版本/修订号", "生效日期", "年审日期", "年审结论",
                "版本有效性", "修订内容", " 技术先进性", "修订内容1", "格式规范化", "修订内容2",
                "执行可操作性", "修订内容3", "内容适用性", "修订内容4", " 其他", "评审人"};
        ExcelHandlerService.handlerFileYearRecord(models, null, title, fileName, header, request, response);
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
