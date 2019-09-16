package com.medical.dtms.service.serviceimpl.file;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.result.ApplyResultEnum;
import com.medical.dtms.common.enumeration.result.LogResultEnum;
import com.medical.dtms.common.model.file.FileApproveLogModel;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import com.medical.dtms.feignservice.file.FileApproveLogService;
import com.medical.dtms.service.manager.file.FileApproveLogManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： FileApproveLogServiceImpl.java v 1.0, 2019年08月28日 9:58 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class FileApproveLogServiceImpl implements FileApproveLogService {

    @Autowired
    private FileApproveLogManager logManager;

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApproveLogModel>
     * @description 文件报批 - 流程记录
     **/
    @Override
    public PageInfo<FileApproveLogModel> pageListLogInfo(@RequestBody FileApproveLogQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileApproveLogModel> list = logManager.pageListLogInfo(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }
        for (FileApproveLogModel logModel : list) {
            logModel.setResultName(logModel.getResult() == null ? null : LogResultEnum.getNameByResult(logModel.getResult()));
        }
        return new PageInfo<>(list);
    }

    /**
    *
    * @description
    * @param  [query]
    * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApproveLogModel>
    **/
    @Override
    public PageInfo<FileApproveLogModel> pageListInsertLog(@RequestBody FileApproveLogQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileApproveLogModel> list = logManager.pageListLogInfo(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }
        for (FileApproveLogModel logModel : list) {
            logModel.setResultName(logModel.getResult() == null ? null : LogResultEnum.getNameByResult(logModel.getResult()));
        }
        return new PageInfo<>(list);
    }

    /**
     *
     * @description
     * @param  [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.file.FileApproveLogModel>
     **/
    @Override
    public PageInfo<FileApproveLogModel> pageListUpdateLog(@RequestBody FileApproveLogQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileApproveLogModel> list = logManager.pageListLogInfo(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }
        for (FileApproveLogModel logModel : list) {
            logModel.setResultName(logModel.getResult() == null ? null : LogResultEnum.getNameByResult(logModel.getResult()));
        }
        return new PageInfo<>(list);
    }
}
