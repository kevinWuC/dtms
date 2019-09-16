package com.medical.dtms.service.serviceimpl.file;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.file.FileYearModel;
import com.medical.dtms.common.model.file.FileYearRecordExportModel;
import com.medical.dtms.common.model.file.FileYearRecordModel;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.FileYearDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.feignservice.file.FileYearService;
import com.medical.dtms.service.manager.file.FileModelManager;
import com.medical.dtms.service.manager.file.FileYearManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * @version： FileYearServiceImpl.java v 1.0, 2019年08月28日 18:29 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class FileYearServiceImpl implements FileYearService {

    @Autowired
    private FileYearManager yearManager;
    @Autowired
    private FileModelManager fileModelManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * 新增文件年审
     *
     * @param dto
     * @return
     */
    @Override
    public Boolean insertFileYear(@RequestBody FileYearDTO dto) {
        FileModelQuery query = new FileModelQuery();
        query.setBizId(dto.getFileId());
        FileModelDTO file = fileModelManager.getFileByCondition(query);
        if (null == file) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条记录不存在，无法操作！");
        }

        // 年审是否通过的标准是 文件年审模块，所有结论是否均为 是
        if (dto.getVersionEffect() && dto.getAdvancement() && dto.getFormat() && dto.getOperability() && dto.getApplicability()) {
            dto.setYearConclusion(true);
        }

        dto.setBizId(idGenerator.nextId());
        dto.setExaminedUserId(dto.getCreatorId());
        dto.setExaminedUserName(dto.getCreator());

        try {
            yearManager.insert(dto);
        } catch (Exception e) {
            log.error("新增年审记录失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * 年审记录（分页展示）
     *
     * @param query
     * @return
     */
    @Override
    public PageInfo<FileYearRecordModel> pageListFileYearRecord(@RequestBody FileModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileYearRecordModel> models = yearManager.pageListFileYearRecord(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * 文件年审（分页展示）
     *
     * @param query
     * @return
     */
    @Override
    public PageInfo<FileYearModel> pageListFileYear(@RequestBody FileModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<FileYearModel> models = fileModelManager.pageListFileYear(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.common.model.file.FileYearRecordModel>
     * @description 导出 文件年审记录
     **/
    @Override
    public List<FileYearRecordExportModel> exportFileYearRecord(@RequestBody FileModelQuery query) {
        List<FileYearRecordModel> models = yearManager.pageListFileYearRecord(query);
        if (CollectionUtils.isEmpty(models)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "无满足条件数据");
        }

        List<FileYearRecordExportModel> list = new ArrayList<>();
        FileYearRecordExportModel exportModel;
        for (FileYearRecordModel model : models) {
            exportModel = new FileYearRecordExportModel();
            BeanUtils.copyProperties(model, exportModel);
            exportModel.setAdvancement(model.getAdvancement() == true ? Constants.YES : Constants.NO);
            exportModel.setApplicability(model.getApplicability() == true ? Constants.YES : Constants.NO);
            exportModel.setFormat(model.getFormat() == true ? Constants.YES : Constants.NO);
            exportModel.setOperability(model.getOperability() == true ? Constants.YES : Constants.NO);
            exportModel.setVersionEffect(model.getVersionEffect() == true ? Constants.YES : Constants.NO);
            exportModel.setYearConclusion(model.getYearConclusion() == true ? Constants.PASS : Constants.FAIL);
            exportModel.setEffectDate(null == model.getEffectDate() == true ? null : DateUtils.format(model.getEffectDate(), DateUtils.YYYY_MM_DD_HH_mm_ss));
            exportModel.setExaminedDate(null == model.getExaminedDate() == true ? null : DateUtils.format(model.getExaminedDate(), DateUtils.YYYY_MM_DD_HH_mm_ss));
        }

        return list;
    }


}
