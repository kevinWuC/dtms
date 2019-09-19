package com.medical.dtms.web.controller.train;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.exam.ExamExcelModel;
import com.medical.dtms.common.model.exam.ExamStartModel;
import com.medical.dtms.common.model.train.MyTrainTestModel;
import com.medical.dtms.common.model.train.TrainExcelModel;
import com.medical.dtms.common.model.train.TrainUserModel;
import com.medical.dtms.common.model.train.TrainUserQueryModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.export.ExcelHandlerService;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.dto.train.query.TrainUserQuery;
import com.medical.dtms.feignservice.train.TrainUserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version： TrainUserController.java v 1.0, 2019年08月19日 16:31  Exp$
 * @Description 培训用户控制类
 **/
@RestController
public class TrainUserController {

    @Autowired
    private TrainUserService trainUserService;

    /**
     * 分页参数校验
     *
     * @param [query]
     */
    private void checkParams(TrainUserQuery query) {
        if (null == query) {
            query = new TrainUserQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }


    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 培训统计 - 用户列表查询（分页展示）
     **/
    @RequestMapping(value = "/train/pageListTrainUser", method = RequestMethod.POST)
    public Result<TrainUserModel> pageListTrainUser(@RequestBody TrainUserQuery query) {
        checkParams(query);
        PageInfo<TrainUserModel> info = trainUserService.pageListTrainUser(query);
        return Result.buildSuccess(info);
    }


    /**
     * @param [query, request, response]
     * @return void
     * @description 培训统计-导出考试人员的成绩信息(导出所有记录)  TODO 调整表格样式
     **/
    @RequestMapping(value = "/train/exportTrain", method = RequestMethod.GET)
    public void exportTrain(TrainUserQuery query, HttpServletRequest request, HttpServletResponse response) {
        checkParams(query);
        List<TrainExcelModel> list = trainUserService.exportTrain(query);
        // 导出
        String fileName = "培训统计.xls";
        String title = "培训统计";
        String[] header = {"姓名", " 部门", "培训名称", "培训文件", "培训测试得分/总分", "完成时间", "是否合格"};
        ExcelHandlerService.handleWorksBook(list, null, title, fileName, header, request, response);
    }


    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.model.train.trainuserModel>>
     * @description 培训及时率 - 列表查询功能
     **/
    @RequestMapping(value = "/train/listTrainTimely", method = RequestMethod.POST)
    public Result<PageInfo<TrainUserModel>> listTrainTimely(@RequestBody TrainUserQuery query) {
        checkParams(query);
        PageInfo<TrainUserModel> pageInfo = trainUserService.listTrainTimely(query);
        return Result.buildSuccess(pageInfo);
    }


    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.model.train.trainuserModel>>
     * @description 我的培训- 查看考试信息
     **/
    @RequestMapping(value = "/train/viewMyTrain", method = RequestMethod.POST)
    public Result<MyTrainTestModel> viewMyTrain(@RequestBody TrainUserDTO trainUserDTO) {
        OperatorInfo operatorInfo = SessionTools.getOperator();
        trainUserDTO.setCreatorId(operatorInfo.getBizId().toString());

        MyTrainTestModel testModel = trainUserService.beginTrainExam(trainUserDTO);
        return Result.buildSuccess(testModel);
    }

    /**
     * @param userPlanModelDTO
     * @return
     * @description 我的培训- 开始考试
     **/
    @RequestMapping(value = "/train/beginTrainExam", method = RequestMethod.POST)
    public Result<MyTrainTestModel> beginTrainExam(@RequestBody TrainUserDTO trainUserDTO) {
        if (null == trainUserDTO || null == trainUserDTO.getBizId()){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "主键为空");
        }

        OperatorInfo operatorInfo = SessionTools.getOperator();
        trainUserDTO.setCreatorId(operatorInfo.getBizId().toString());
        trainUserDTO.setCreator(operatorInfo.getDspName());

        MyTrainTestModel testModel = trainUserService.beginTrainExam(trainUserDTO);
        return Result.buildSuccess(testModel);
    }


    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.model.train.trainuserModel>>
     * @description 我的培训- 查询培训试卷
     **/
    @RequestMapping(value = "/train/listTrainExam", method = RequestMethod.POST)
    public Result<PageInfo<TrainUserQueryModel>> listTrainExam(@RequestBody TrainUserQuery query) {
        checkParams(query);
        PageInfo<TrainUserQueryModel> pageInfo = trainUserService.listTrainExam(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 确认交卷
     *
     * @param processDTO
     * @return
     */
    @RequestMapping(value = "/train/addTrainUser", method = RequestMethod.POST)
    public Result<Boolean> addTrainUser(@RequestBody TrainUserDTO processDTO) {
        if (null == processDTO) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "内容为空");
        }
        if (null == processDTO.getTrainId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "培训id 不能为空");
        }

        OperatorInfo operatorInfo = SessionTools.getOperator();
        processDTO.setCreatorId(operatorInfo.getUserId());
        processDTO.setCreator(operatorInfo.getDspName());
        processDTO.setUserId(operatorInfo.getBizId());

        trainUserService.addTrainUser(processDTO);

        return Result.buildSuccess(true);
    }







    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 考试统计 - 查询查看（分页展示）
     **/
    @RequestMapping(value = "/train/pageListExamTotal", method = RequestMethod.POST)
    public Result<TrainUserModel> pageListExamTotal(@RequestBody TrainUserQuery query) {
        checkParams(query);
        PageInfo<TrainUserModel> info = trainUserService.pageListExamTotal(query);
        return Result.buildSuccess(info);
    }


    /**
     * @param [query, request, response]
     * @return void
     * @description 考试统计-导出考试人员的成绩信息(导出所有记录)  TODO 调整表格样式
     **/
    @RequestMapping(value = "/train/exportExam", method = RequestMethod.GET)
    public void exportExam(TrainUserQuery query, HttpServletRequest request, HttpServletResponse response) {
        checkParams(query);
        List<ExamExcelModel> list = trainUserService.exportExam(query);
        // 导出
        String fileName = "考试统计.xls";
        String title = "考试统计";
        String[] header = {"姓名", " 部门", "考试名称", "得分", "及格/总分", "完成时间", "是否合格"};
        ExcelHandlerService.handleWorkBookExam(list, null, title, fileName, header, request, response);
    }

}
