package com.medical.dtms.web.controller.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.file.FileApproveLogModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.feignservice.file.FileApproveLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： FileApproveLogController.java v 1.0, 2019年08月27日 21:17 wuxuelin Exp$
 * @Description 流程记录 控制类
 **/
@RestController
public class FileApproveLogController {

    @Autowired
    private FileApproveLogService logService;

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApproveLogModel>>
     * @description 文件报批 - 流程记录
     **/
    @RequestMapping(value = "/log/pageListLogInfo", method = RequestMethod.POST)
    public Result<PageInfo<FileApproveLogModel>> pageListLogInfo(@RequestBody FileApproveLogQuery query) {
        if (null == query || null == query.getFileId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        checkQueryParams(query);
        PageInfo<FileApproveLogModel> pageInfo = logService.pageListLogInfo(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
    *
    * @description 文件查询 - 流程记录 - 新增记录
    * @param  [query]
    * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApproveLogModel>>
    **/
    @RequestMapping(value = "/log/pageListInsertLog", method = RequestMethod.POST)
    public Result<PageInfo<FileApproveLogModel>> pageListInsertLog(@RequestBody FileApproveLogQuery query){
        if (null == query || null == query.getFileId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        query.setOperatorType(1);
        checkQueryParams(query);
        PageInfo<FileApproveLogModel> pageInfo = logService.pageListInsertLog(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     *
     * @description 文件查询 - 流程记录 - 修改记录
     * @param  [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApproveLogModel>>
     **/
    @RequestMapping(value = "/log/pageListUpdateLog", method = RequestMethod.POST)
    public Result<PageInfo<FileApproveLogModel>> pageListUpdateLog(@RequestBody FileApproveLogQuery query){
        if (null == query || null == query.getFileId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "文件id为空");
        }
        query.setOperatorType(2);
        checkQueryParams(query);
        PageInfo<FileApproveLogModel> pageInfo = logService.pageListUpdateLog(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkQueryParams(FileApproveLogQuery query) {
        if (null == query) {
            query = new FileApproveLogQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }

}
