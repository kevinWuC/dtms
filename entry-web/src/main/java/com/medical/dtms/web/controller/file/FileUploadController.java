package com.medical.dtms.web.controller.file;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.file.FileModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.web.fileupload.UploadManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @version： FileUploadController.java v 1.0, 2019年08月27日 17:48 wuxuelin Exp$
 * @Description 文件上传控制类
 **/
@RestController
public class FileUploadController {

    @Autowired
    private UploadManager uploadManager;

    /**
     * @param [files, request]
     * @return com.medical.dtms.common.resp.Result<java.util.List < java.util.Map < java.lang.String, java.lang.String>>>
     * @description 文件上传
     **/
    @RequestMapping(value = "/file/uploadFiles")
    public Result<List<Map<String, String>>> uploadFiles(MultipartFile[] file, HttpServletRequest request) {
        if (null == file || file.length <= 0) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "未上传任何文件");
        }

        // 校验文件格式
        if (!uploadManager.checkFileFormat(file)) {
            return Result.buildFailed(ErrorCodeEnum.FAILED.getErrorCode(), "文件格式不正确，支持的格式有 docx, xlsx, pptx, doc, xls, ppt,jpg,png");
        }

        List<Map<String, String>> list = uploadManager.saveFiles(file, request);
        return Result.buildSuccess(list);
    }

    /**
     * @param [uploadFileId]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 删除文件
     **/
    @RequestMapping(value = "/file/deleteUploadFile", method = RequestMethod.POST)
    public Result<Boolean> deleteUploadFile(@RequestBody FileModel model) {
        if (null == model || StringUtils.isBlank(model.getFileName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }

        uploadManager.deleteUploadFile(model.getFileName());
        return Result.buildSuccess(true);
    }

}
