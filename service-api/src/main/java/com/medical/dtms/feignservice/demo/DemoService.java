package com.medical.dtms.feignservice.demo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.medical.dtms.common.eception.BizException;

import com.medical.dtms.dto.demo.LeaveDTO;

/**
 * $Id：DemoService.java v 1.0, 2019/8/8 9:43 wuxuelin Exp$
 *
 * @Description
 **/
@FeignClient("service-dtms")
public interface DemoService {

    @RequestMapping(value = "/capa/startApply", method = RequestMethod.POST)
    void startApply(@RequestBody LeaveDTO leave) throws BizException;

    @RequestMapping(value = "/capa/apply", method = RequestMethod.POST)
    void apply(@RequestBody LeaveDTO leave) throws BizException;

    @RequestMapping(value = "/capa/leaderCheck", method = RequestMethod.POST)
    void leaderCheck(@RequestBody LeaveDTO leave) throws BizException;

    @RequestMapping(value = "/capa/studentModify", method = RequestMethod.POST)
    void studentModify(@RequestBody LeaveDTO leave) throws BizException;
}
