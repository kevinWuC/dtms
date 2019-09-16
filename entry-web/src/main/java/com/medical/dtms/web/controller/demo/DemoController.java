package com.medical.dtms.web.controller.demo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.feignservice.demo.DemoService;

import com.medical.dtms.dto.demo.LeaveDTO;

/**
 * $Id：DemoController.java v 1.0, 2019/8/7 17:45 wuxuelin Exp$
 *
 * @Description 控制类
 **/
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    /**
     * @Description 启动流程
     * @Param []
     * @Return com.medical.capa.common.resp.Result<java.lang.Boolean>
     */
    @RequestMapping(value = "/capa/startApply", method = RequestMethod.POST)
    public Result<Boolean> startApply(@RequestBody LeaveDTO leave) {
        demoService.startApply(leave);
        return Result.buildSuccess(true);
    }

    /**
    *   @Description  发起申请
    *   @Param   [leave]
    *   @Return  com.medical.capa.common.resp.Result<java.lang.Boolean>
    */
    @RequestMapping(value = "/capa/apply", method = RequestMethod.POST)
    public Result<Boolean> apply(@RequestBody LeaveDTO leave) {
        if (null == leave) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(),
                ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        if (StringUtils.isBlank(leave.getContent())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "请假内容必填");
        }
        demoService.apply(leave);
        return Result.buildSuccess(true);
    }

    /**
     * @Description 领导审批
     * @Param [leave]
     * @Return com.medical.capa.common.resp.Result<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @RequestMapping(value = "/capa/leaderCheck", method = RequestMethod.POST)
    public Result<Boolean> leaderCheck(@RequestBody LeaveDTO leave) {
        if (null == leave) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(),
                ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        demoService.leaderCheck(leave);
        return Result.buildSuccess(true);
    }

    /**
    *   @Description 修改申请
    *   @Param   [leave]
    *   @Return  com.medical.capa.common.resp.Result<java.lang.Boolean>
    */
    @RequestMapping(value = "/capa/studentModify", method = RequestMethod.POST)
    public Result<Boolean> studentModify(@RequestBody LeaveDTO leave) {
        demoService.studentModify(leave);
        return Result.buildSuccess(true);
    }
}
