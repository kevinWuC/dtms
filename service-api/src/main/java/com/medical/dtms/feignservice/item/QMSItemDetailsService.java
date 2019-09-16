package com.medical.dtms.feignservice.item;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.common.model.item.QMSItemDetailsModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： QMSItemDetailsService.java v 1.0, 2019年08月16日 15:06 wuxuelin Exp$
 * @Description 字典明细
 **/
@FeignClient("service-dtms")
public interface QMSItemDetailsService {

    @RequestMapping(value = "/detail/addQMSItemDetails", method = RequestMethod.POST)
    Boolean addQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO);

    @RequestMapping(value = "/detail/updateQMSItemDetails", method = RequestMethod.POST)
    Boolean updateQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO);

    @RequestMapping(value = "/detail/deleteQMSItemDetails", method = RequestMethod.POST)
    Boolean deleteQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO);

    @RequestMapping(value = "/detail/pageListQMSItemDetails", method = RequestMethod.POST)
    PageInfo<QMSItemDetailsModel> pageListQMSItemDetails(@RequestBody QMSItemDetailsQuery query);
}
