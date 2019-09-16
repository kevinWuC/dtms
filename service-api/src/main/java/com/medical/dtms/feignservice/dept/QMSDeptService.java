package com.medical.dtms.feignservice.dept;

import com.medical.dtms.common.model.dept.QMSDeptModel;
import com.medical.dtms.dto.dept.QMSDeptDTO;
import com.medical.dtms.dto.dept.query.QMSDeptQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * @version： QMSDeptService.java v 1.0, 2019年08月14日 14:14  Exp$
 * @Description 部门设置
 **/
@FeignClient("service-dtms")
public interface QMSDeptService {

    @RequestMapping(value = "/dept/addQMSDept", method = RequestMethod.POST)
    Boolean addQMSDept(@RequestBody QMSDeptDTO deptDTO);

    @RequestMapping(value = "/dept/updateQMSDept", method = RequestMethod.POST)
    Boolean updateQMSDept(@RequestBody QMSDeptDTO dto);

    @RequestMapping(value = "/dept/deleteQMSDept", method = RequestMethod.POST)
    Boolean deleteQMSDept(@RequestBody QMSDeptDTO dto);

    @RequestMapping(value = "/dept/listQMSDept", method = RequestMethod.POST)
    List<QMSDeptModel> listQMSDept(@RequestBody QMSDeptQuery query);
}
