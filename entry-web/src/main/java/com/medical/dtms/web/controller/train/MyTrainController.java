package com.medical.dtms.web.controller.train;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.train.MyTrainBeginModel;
import com.medical.dtms.common.model.train.MyTrainPageModel;
import com.medical.dtms.common.model.train.MyTrainSubmitModel;
import com.medical.dtms.common.model.train.query.MyTrainPageQuery;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import com.medical.dtms.feignservice.train.MyTrainService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： MyTrainController.java v 1.0, 2019年10月11日 11:04 wuxuelin Exp$
 * @Description 我的培训
 **/
@RestController
public class MyTrainController {

    @Autowired
    private MyTrainService trainService;

    /**
     * 培训管理 - 我的培训 - 分页展示
     *
     * @param query
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.train.MyTrainPageModel>>
     **/
    @RequestMapping(value = "/train/listTrainExam", method = RequestMethod.POST)
    public Result<PageInfo<MyTrainPageModel>> pageListMyTrain(@RequestBody MyTrainPageQuery query) {
        OperatorInfo operator = SessionTools.getOperator();
        query.setUserId(operator.getBizId());
        checkParams(query);
        PageInfo<MyTrainPageModel> info = trainService.pageListMyTrain(query);
        return Result.buildSuccess(info);
    }

    /**
     * 开始考试
     *
     * @param
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     **/
    @RequestMapping(value = "/train/beginTrainExam", method = RequestMethod.POST)
    public Result<MyTrainBeginModel> beginTrainExam(@RequestBody TrainQuestionProcessDTO processDTO) {
        if (null == processDTO) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo operator = SessionTools.getOperator();
        processDTO.setCreator(operator.getDspName());
        processDTO.setCreatorId(operator.getUserId());
        MyTrainBeginModel beginModel = trainService.beginTrainExam(processDTO);
        return Result.buildSuccess(beginModel);
    }

    /**
     * 提交试卷
     *
     * @param
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     **/
    @RequestMapping(value = "/train/submitTrainAnswer", method = RequestMethod.POST)
    public Result<Boolean> submitTrainAnswer(@RequestBody MyTrainSubmitModel model) {
        if (null == model || null == model.getExamId()
                || null == model.getTrainId()
                || CollectionUtils.isEmpty(model.getQuestions())
        ) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        OperatorInfo operator = SessionTools.getOperator();
        model.setModifier(operator.getDspName());
        model.setModifierId(operator.getUserId());
        model.setUserId(Long.parseLong(operator.getUserId()));
        trainService.submitTrainAnswer(model);
        return Result.buildSuccess(true);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(MyTrainPageQuery query) {
        if (null == query) {
            query = new MyTrainPageQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
        if (null == query.getUserId()) {
            query.setUserId(SessionTools.getOperator().getBizId());
        }
    }

}
