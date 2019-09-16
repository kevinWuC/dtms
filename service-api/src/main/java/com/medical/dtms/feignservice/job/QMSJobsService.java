package com.medical.dtms.feignservice.job;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.dto.job.QMSJobsDTO;
import com.medical.dtms.dto.job.query.QMSJobsQuery;
import com.medical.dtms.common.model.job.QMSJobsModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： QMSJobsService.java v 1.0, 2019年08月23日 11:27 huangshuaiquan Exp$
 * @Description 岗位设置
 **/
@FeignClient("service-dtms")
public interface QMSJobsService {

    @RequestMapping(value = "/jobs/addQMSJob",method = RequestMethod.POST)
    Boolean addQMSJob(@RequestBody QMSJobsDTO jobsDTO);

    @RequestMapping(value = "/jobs/deleteQMSJob",method = RequestMethod.POST)
    Boolean deleteQMSJob(@RequestBody QMSJobsDTO jobsDTO);

    @RequestMapping(value = "/jobs/updateQMSJob",method = RequestMethod.POST)
    Boolean updateQMSJob(@RequestBody QMSJobsDTO jobsDTO);

    @RequestMapping(value = "/jobs/getPageListJobs",method = RequestMethod.POST)
    PageInfo<QMSJobsModel> getPageListJobs(@RequestBody QMSJobsQuery query);
}
