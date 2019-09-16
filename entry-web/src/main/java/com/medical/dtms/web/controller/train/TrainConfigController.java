package com.medical.dtms.web.controller.train;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.train.TrainConfigModel;
import com.medical.dtms.common.model.train.TrainConfigStatementModel;
import com.medical.dtms.common.model.train.TrainFileModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.TrainFileDTO;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.dto.train.query.TrainConfigQuery;
import com.medical.dtms.dto.train.query.TrainFileQuery;
import com.medical.dtms.feignservice.train.TrainConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： TrainConfigController.java v 1.0, 2019年09月03日 17:40 huangshuaiquan Exp$
 * @Description 培训管理
 **/
@RestController
public class TrainConfigController {

    @Autowired
    private TrainConfigService trainConfigService;

    /**
     * @param [trainConfigDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 培训管理 - 新增培训
     **/
    @RequestMapping(value = "/trainConfig/addTrain", method = RequestMethod.POST)
    public Result<Boolean> addTrain(@RequestBody TrainConfigDTO trainConfigDTO) {
        if (null == trainConfigDTO) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "内容为空,请填写");
        }

        if (null == trainConfigDTO.getTrainTypeId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "培训类型ID为空,请填写");
        }

        if (StringUtils.isBlank(trainConfigDTO.getTrainName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "培训名称为空,请填写");
        }

        if (StringUtils.isBlank(trainConfigDTO.getTrainDescription())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "培训内容为空,请填写");
        }

        if (null == trainConfigDTO.getStartDate() || null == trainConfigDTO.getEndDate()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "培训内开始日期或结束日期为空,请填写");
        }

        if (1 == trainConfigDTO.getStartDate().compareTo(trainConfigDTO.getEndDate())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_ERROR.getErrorCode(), "培训开始日期不能大于结束日期,请确认");
        }

        if (null == trainConfigDTO.getExamId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "考试ID为空,请填写");
        }

        if (null == trainConfigDTO.getPassPoint()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "考试及格分为空,请填写");
        }

        if (null == trainConfigDTO.getReadFen()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "应培训时数为空,请填写");
        }

        OperatorInfo info = SessionTools.getOperator();
        trainConfigDTO.setCreator(info.getDspName());
        trainConfigDTO.setCreatorId(info.getUserId());

        trainConfigService.addTrain(trainConfigDTO);

        return Result.buildSuccess(true);
    }


    /**
     * @param [trainConfigDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 培训管理 - 删除培训（逻辑删除）
     **/
    @RequestMapping(value = "/trainConfig/deleteTrain", method = RequestMethod.POST)
    public Result<Boolean> deleteTrain(@RequestBody TrainConfigDTO trainConfigDTO) {
        if (null == trainConfigDTO || null == trainConfigDTO.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        trainConfigDTO.setModifier(info.getDspName());
        trainConfigDTO.setModifierId(info.getUserId());
        trainConfigService.deleteTrain(trainConfigDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [trainConfigDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 培训管理 - 更新培训（
     **/
    @RequestMapping(value = "/trainConfig/updateTrain", method = RequestMethod.POST)
    public Result<Boolean> updateTrain(@RequestBody TrainConfigDTO trainConfigDTO) {
        if (null == trainConfigDTO || null == trainConfigDTO.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        trainConfigDTO.setModifier(info.getDspName());
        trainConfigDTO.setModifierId(info.getUserId());
        trainConfigService.updateTrain(trainConfigDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.train.TrainConfigModel>>
     * @description 培训管理 - 分页查询培训列表
     **/
    @RequestMapping(value = "/trainConfig/pageListTrains", method = RequestMethod.POST)
    public Result<PageInfo<TrainConfigModel>> getPageListTrains(@RequestBody TrainConfigQuery query) {
        checkParams(query);
        PageInfo<TrainConfigModel> pageListJobs = trainConfigService.getPageListTrains(query);
        return Result.buildSuccess(pageListJobs);

    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(TrainConfigQuery query) {
        if (null == query) {
            query = new TrainConfigQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(20);
        }
    }

    /**
     * @param [trainFileDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 培训管理 - 添加培训文件
     **/
    @RequestMapping(value = "/trainConfig/addTrainFile", method = RequestMethod.POST)
    public Result<Boolean> addTrainFile(@RequestBody TrainFileDTO trainFileDTO) {
        if (null == trainFileDTO || null == trainFileDTO.getTrainId() || CollectionUtils.isEmpty(trainFileDTO.getFileIdList())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        trainFileDTO.setCreator(info.getDspName());
        trainFileDTO.setCreatorId(info.getUserId());
        trainConfigService.addTrainFile(trainFileDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 培训管理 - 删除培训文件
     **/
    @RequestMapping(value = "/trainConfig/deleteTrainFile", method = RequestMethod.POST)
    public Result<Boolean> deleteTrainFile(@RequestBody TrainFileDTO trainFileDTO) {
        if (null == trainFileDTO || CollectionUtils.isEmpty(trainFileDTO.getFileIdList())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        trainFileDTO.setModifier(info.getDspName());
        trainFileDTO.setModifierId(info.getUserId());
        trainConfigService.deleteTrainFile(trainFileDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.train.TrainFileQueryModel>>
     * @description 培训管理 - 分页查询待选文件列表
     **/
    @RequestMapping(value = "/trainConfig/pageListTrainFilesSelect", method = RequestMethod.POST)
    public Result<PageInfo<TrainFileModel>> getPageListTrainFilesSelect(@RequestBody TrainFileQuery query) {
        if (null == query || null == query.getTrainId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        fileCheckParams(query);
        PageInfo<TrainFileModel> filesSelect = trainConfigService.getPageListTrainFilesSelect(query);
        return Result.buildSuccess(filesSelect);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.train.TrainFileQueryModel>>
     * @description 培训管理 - 分页查询已选文件列表
     **/
    @RequestMapping(value = "/trainConfig/pageListTrainFilesSelected", method = RequestMethod.POST)
    public Result<PageInfo<TrainFileModel>> getPageListTrainFilesSelected(@RequestBody TrainFileQuery query) {
        if (null == query || null == query.getTrainId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        fileCheckParams(query);
        PageInfo<TrainFileModel> filesSelected = trainConfigService.getPageListTrainFilesSelected(query);
        return Result.buildSuccess(filesSelected);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void fileCheckParams(TrainFileQuery query) {
        if (null == query) {
            query = new TrainFileQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(20);
        }
    }

    /**
     * @param [trainUserDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 培训管理 - 新增培训用户
     **/
    @RequestMapping(value = "/trainConfig/addTrainUser", method = RequestMethod.POST)
    public Result<Boolean> addTrainUser(@RequestBody TrainUserDTO trainUserDTO) {
        if (null == trainUserDTO || null == trainUserDTO.getTrainId() || CollectionUtils.isEmpty(trainUserDTO.getUserIdList())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        OperatorInfo info = SessionTools.getOperator();
        trainUserDTO.setCreator(info.getDspName());
        trainUserDTO.setCreatorId(info.getUserId());
        trainConfigService.addTrainUser(trainUserDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [trainConfigDTO]
     * @return com.medical.dtms.common.resp.Result<com.medical.dtms.common.model.train.TrainConfigStatementModel>
     * @description 培训管理 - 培训报表
     **/
    @RequestMapping(value = "/trainConfig/getTrainConfigStatement", method = RequestMethod.POST)
    public Result<TrainConfigStatementModel> getTrainConfigStatement(@RequestBody TrainConfigDTO trainConfigDTO) {
        if (null == trainConfigDTO || null == trainConfigDTO.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        TrainConfigStatementModel statement = trainConfigService.getTrainConfigStatement(trainConfigDTO);
        return Result.buildSuccess(statement);
    }


}
