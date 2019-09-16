package com.medical.dtms.service.serviceimpl.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.feignservice.demo.DemoService;

import com.medical.dtms.dto.demo.LeaveDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * $Id：DemoServiceImpl.java v 1.0, 2019/8/8 9:44 wuxuelin Exp$
 *
 * @Description
 **/
@RestController
@Slf4j
public class DemoServiceImpl implements DemoService {

//    @Autowired
//    private DemoManager demoManager;
//    @Autowired
//    private IdGenerator idGenerator;

    /**
     * @Description 提交申请，启动流程
     * @Param [leave]
     * @Return void
     */
    @Override
    @Transactional(rollbackFor = { BizException.class })
    public void startApply(@RequestBody LeaveDTO leave) throws BizException {
        Map<String, Object> params = new HashMap<>();
        leave.setUserId("xiaoli");
        leave.setApproveId("xiaoming");

        params.put("userId", leave.getUserId());
//        try {
            // 记录流程发起人id
//            identityService.setAuthenticatedUserId(leave.getUserId());
            // 启动流程
//            ProcessInstance instance = runtimeService.startProcessInstanceByKey("demo2", params);

            // 流程定义 id
//            String definitionId = instance.getProcessDefinitionId();
            // 流程实例 id
//            String instanceId = instance.getProcessInstanceId();

//            leave.setProcDefId(definitionId);
//            leave.setProcInstId(instanceId);
//            leave.setBizId(idGenerator.nextId());
//            demoManager.startApply(leave);
//        } catch (Exception e) {
//            log.error("开启流程失败", e);
//            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(),
//                ErrorCodeEnum.FAILED.getErrorMessage());
//        }
    }

    /**
     * @Description 提交申请
     * @Param [leave]
     * @Return void
     */
    @Override
    @Transactional(rollbackFor = { BizException.class })
    public void apply(@RequestBody LeaveDTO leave) throws BizException {
        Map<String, Object> params = new HashMap<>();
        params.put("approveId", "xiaoming");

//        try {
//            // 流程继续
//            taskService.complete(leave.getTaskId(), params);
//        } catch (Exception e) {
//            log.error("申请提交失败", e);
//            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(),
//                ErrorCodeEnum.FAILED.getErrorMessage());
//        }
    }

    /**
     * @Description 领导审批
     * @Param [leave]
     * @Return void
     */
    @Override
    @Transactional(rollbackFor = { BizException.class })
    public void leaderCheck(@RequestBody LeaveDTO leave) throws BizException {
        leave.setApproveId("xiaoli");

        Map<String, Object> params = new HashMap<>();
        params.put("pass", leave.getPass());
        params.put("approveId", leave.getApproveId());

//        try {
//            // 流程继续
//            taskService.complete(leave.getTaskId(), params);
//        } catch (Exception e) {
//            log.error("领导审批提交失败", e);
//            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(),
//                ErrorCodeEnum.FAILED.getErrorMessage());
//        }
    }

    /**
     * @Description 修改申请
     * @Param [leave]
     * @Return void
     */
    @Override
    @Transactional(rollbackFor = { BizException.class })
    public void studentModify(@RequestBody LeaveDTO leave) throws BizException {
        Map<String, Object> params = new HashMap<>();
        leave.setApproveId("xiaoming");

        params.put("pass", leave.getPass());
        params.put("approveId", leave.getApproveId());

//        try {
//            // 流程继续
//            taskService.complete(leave.getTaskId(), params);
//        } catch (Exception e) {
//            log.error("申请修改提交失败", e);
//            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(),
//                ErrorCodeEnum.FAILED.getErrorMessage());
//        }
    }

}
