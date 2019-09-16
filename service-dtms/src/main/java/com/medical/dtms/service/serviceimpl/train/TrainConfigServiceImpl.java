package com.medical.dtms.service.serviceimpl.train;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.train.TrainConfigModel;
import com.medical.dtms.common.model.train.TrainConfigQueryModel;
import com.medical.dtms.common.model.train.TrainConfigStatementModel;
import com.medical.dtms.common.model.train.TrainFileModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.TrainFileDTO;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.dto.train.query.TrainConfigQuery;
import com.medical.dtms.dto.train.query.TrainFileQuery;
import com.medical.dtms.dto.train.query.TrainUserQuery;
import com.medical.dtms.dto.user.QMSUserDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.feignservice.train.TrainConfigService;
import com.medical.dtms.service.manager.file.FileModelManager;
import com.medical.dtms.service.manager.train.TrainConfigManager;
import com.medical.dtms.service.manager.train.TrainFilesManager;
import com.medical.dtms.service.manager.train.TrainUserManager;
import com.medical.dtms.service.manager.user.QMSUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： TrainConfigServiceImpl.java v 1.0, 2019年08月29日 14:53 huangshuaiquan Exp$
 * @Description
 **/
@RestController
@Slf4j
public class TrainConfigServiceImpl implements TrainConfigService {

    @Autowired
    private TrainConfigManager trainConfigManager;
    @Autowired
    private TrainFilesManager trainFilesManager;
    @Autowired
    private FileModelManager fileModelManager;
    @Autowired
    private TrainUserManager trainUserManager;
    @Autowired
    private QMSUserManager qmsUserManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [trainConfigDTO]
     * @return java.lang.Boolean
     * @description 培训管理 - 新增培训
     **/
    @Override
    public Boolean addTrain(@RequestBody TrainConfigDTO trainConfigDTO) {
        //根据培训名称做唯一性校验
        TrainConfigQuery query = new TrainConfigQuery(trainConfigDTO.getTrainName());
        TrainConfigDTO dto = trainConfigManager.getTrainConfigByTrainName(query);
        if (null != dto) {
            // 存在,提示重复
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "培训已存在，请重新填写");
        }

        if (null == trainConfigDTO.getIsStart()) {
            trainConfigDTO.setIsStart(false);
        }

        try {
            trainConfigDTO.setBizId(idGenerator.nextId());
            trainConfigManager.insert(trainConfigDTO);
        } catch (Exception e) {
            log.error("新增培训失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }


    /**
     * @param [trainConfigDTO]
     * @return java.lang.Boolean
     * @description 培训管理 - 删除培训（逻辑删除）
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean deleteTrain(@RequestBody TrainConfigDTO trainConfigDTO) {
        //校验是否被删除
        TrainConfigDTO dto = trainConfigManager.selectByPrimaryKey(trainConfigDTO.getBizId());
        if (null == dto) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该培训已被删除，无法操作");
        }

        // 未被删除
        // 校验能否被删除
        if (null != dto.getIsStart() && dto.getIsStart()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "已启动培训项目不能编辑");
        }

        //删除与文件的关联
        TrainFileQuery fileQuery = new TrainFileQuery();
        fileQuery.setTrainId(trainConfigDTO.getBizId());
        List<TrainFileDTO> fileDTOS = trainFilesManager.listTrainFiles(fileQuery);
        if (CollectionUtils.isNotEmpty(fileDTOS)) {
            try {
                trainFilesManager.deleteTrainFilesByTrainId(trainConfigDTO.getBizId());
            } catch (Exception e) {
                log.error("删除培训时，删除文件关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        //删除与用户的关联
        TrainUserQuery query = new TrainUserQuery();
        query.setTrainId(trainConfigDTO.getBizId());
        List<TrainUserDTO> userDTOS = trainUserManager.listTrainUserByCondition(query);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            try {
                trainUserManager.deleteTrainUserByTrainID(trainConfigDTO.getBizId());
            } catch (Exception e) {
                log.error("删除培训时,删除用户关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        try {
            trainConfigManager.deleteByPrimaryKey(trainConfigDTO.getBizId());
        } catch (Exception e) {
            log.error("删除培训失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }


        return true;
    }

    /**
     * @param [trainConfigDTO]
     * @return java.lang.Boolean
     * @description 培训管理 - 更新培训
     **/
    @Override
    public Boolean updateTrain(@RequestBody TrainConfigDTO trainConfigDTO) {
        //校验是否被删除
        TrainConfigDTO dto = trainConfigManager.selectByPrimaryKey(trainConfigDTO.getBizId());
        if (null == dto) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该培训已被删除，无法操作");
        }

        // 未被删除
        // 校验能否被编辑
        if (null != dto.getIsStart() && dto.getIsStart()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "已启动培训项目不能编辑");
        }

        try {
            trainConfigManager.updateByPrimaryKeySelective(trainConfigDTO);
        } catch (Exception e) {
            log.error("更新培训失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.train.TrainConfigModel>
     * @description 培训管理 - 分页查询培训列表
     **/
    @Override
    public PageInfo<TrainConfigModel> getPageListTrains(@RequestBody TrainConfigQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<TrainConfigModel> models = trainConfigManager.listTrainConfigs(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [trainFileDTO]
     * @return java.lang.Boolean
     * @description 培训管理 - 添加培训文件
     **/
    @Override
    public Boolean addTrainFile(@RequestBody TrainFileDTO trainFileDTO) {
        //查询与培训的关联
        TrainFileQuery query = new TrainFileQuery();
        query.setTrainId(trainFileDTO.getTrainId());
        List<TrainFileDTO> fileDTOS = trainFilesManager.listTrainFiles(query);
        if (CollectionUtils.isNotEmpty(fileDTOS)) {
            try {
                trainFilesManager.deleteTrainFilesByTrainId(trainFileDTO.getTrainId());
            } catch (Exception e) {
                log.error("删除培训时,删除用户关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        //新增关联
        List<TrainFileDTO> dtos = new ArrayList<>();
        TrainFileDTO fileDTO;
        for (Long fileId : trainFileDTO.getFileIdList()) {
            fileDTO = new TrainFileDTO();
            BeanUtils.copyProperties(trainFileDTO, fileDTO);
            fileDTO.setBizId(idGenerator.nextId());
            fileDTO.setFileId(fileId);
            dtos.add(fileDTO);
        }

        try {
            trainFilesManager.insert(dtos);
        } catch (Exception e) {
            log.error("新增培训文件失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return java.lang.Boolean
     * @description 培训管理 - 删除培训文件
     **/
    @Override
    public Boolean deleteTrainFile(@RequestBody TrainFileDTO trainFileDTO) {

        for (Long fileId:trainFileDTO.getFileIdList()) {
            TrainFileDTO dto = trainFilesManager.selectByPrimaryKey(fileId);
            if (null == dto) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该培训与文件关联已被删除，无法操作");
            }
        }

        try {
            trainFilesManager.deleteByPrimaryKeyList(trainFileDTO.getFileIdList());
        } catch (Exception e) {
            log.error("该文件与培训的关联删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.train.TrainFileQueryModel>
     * @description 培训管理 - 分页查询待选文件列表
     **/
    @Override
    public PageInfo<TrainFileModel> getPageListTrainFilesSelect(@RequestBody TrainFileQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<TrainFileModel> models = fileModelManager.pageListForTrainFileSelect(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.train.TrainFileQueryModel>
     * @description 培训管理 - 分页查询已选文件列表
     **/
    @Override
    public PageInfo<TrainFileModel> getPageListTrainFilesSelected(@RequestBody TrainFileQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<TrainFileModel> models = fileModelManager.pageListForTrainFileSelected(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [trainUserDTO]
     * @return java.lang.Boolean
     * @description 培训管理 - 新增培训用户
     **/
    @Override
    public Boolean addTrainUser(@RequestBody TrainUserDTO trainUserDTO) {
        //查询与用户的关联
        TrainUserQuery query = new TrainUserQuery();
        query.setTrainId(trainUserDTO.getTrainId());
        List<TrainUserDTO> userDTOS = trainUserManager.listTrainUserByCondition(query);
        if (CollectionUtils.isNotEmpty(userDTOS)) {
            try {
                trainUserManager.deleteTrainUserByTrainID(trainUserDTO.getTrainId());
            } catch (Exception e) {
                log.error("删除培训时,删除用户关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        //新增关联
        List<TrainUserDTO> dtos = new ArrayList<>();
        TrainUserDTO userDTO;
        for (Long userId : trainUserDTO.getUserIdList()) {
            userDTO = new TrainUserDTO();
            BeanUtils.copyProperties(trainUserDTO, userDTO);
            userDTO.setBizId(idGenerator.nextId());
            userDTO.setUserId(userId);
            dtos.add(userDTO);
        }

        try {
            trainUserManager.insert(dtos);
        } catch (Exception e) {
            log.error("添加培训与用户关联关系失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [trainConfigDTO]
     * @return com.medical.dtms.common.model.train.TrainConfigStatementModel
     * @description 培训管理 - 培训报表
     **/
    @Override
    public TrainConfigStatementModel getTrainConfigStatement(@RequestBody TrainConfigDTO trainConfigDTO) {
        TrainConfigStatementModel model = new TrainConfigStatementModel();
        //获取培训表相关信息
        TrainConfigQueryModel configModel = trainConfigManager.selectTrainConfig(trainConfigDTO.getBizId());
        model.setTrainTypeName(configModel.getTrainTypeName());
        model.setTrainName(configModel.getTrainName());
        model.setStartDate(configModel.getStartDate());
        model.setEndDate(configModel.getEndDate());
        model.setExamName(configModel.getExamName());
        model.setTrainDescription(configModel.getTrainDescription());
        //获取培训文件信息
        TrainFileQuery fileQuery = new TrainFileQuery();
        fileQuery.setTrainId(trainConfigDTO.getBizId());
        List<TrainFileModel> trainFileModels = fileModelManager.pageListForTrainFileSelected(fileQuery);
        model.setListFile(trainFileModels);
        //获取参加培训用户名
        TrainUserQuery userQuery = new TrainUserQuery();
        userQuery.setTrainId(trainConfigDTO.getBizId());
        List<TrainUserDTO> dtos = trainUserManager.listTrainUserByCondition(userQuery);
        if (CollectionUtils.isNotEmpty(dtos)){
            StringBuilder userNames = new StringBuilder();
            BaseUserQuery query = null;
            for (TrainUserDTO trainUserDTO : dtos) {
                query = new BaseUserQuery();
                query.setUserId(trainUserDTO.getUserId());
                QMSUserDTO userDTO = qmsUserManager.getUserByCondition(query);
                userNames.append(userDTO.getDspName()).append(" | ");
            }
            String userNamesResult = "" + userNames;
            userNamesResult = userNamesResult.substring(0, userNamesResult.length() - 3);
            model.setTrainUserNames(userNamesResult);
        }

        return model;
    }

}
