package com.medical.dtms.feignservice.item;

import com.medical.dtms.dto.item.QMSItemsDTO;
import com.medical.dtms.dto.item.query.QMSItemsQuery;
import com.medical.dtms.common.model.item.QMSItemsModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： QMSItemsService.java v 1.0, 2019年08月14日 14:14 wuxuelin Exp$
 * @Description 数据字典
 **/
@FeignClient("service-dtms")
public interface QMSItemsService {

    @RequestMapping(value = "/item/addQMSItems", method = RequestMethod.POST)
    Boolean addQMSItems(@RequestBody QMSItemsDTO itemsDTO);

    @RequestMapping(value = "/item/updateQMSItems", method = RequestMethod.POST)
    Boolean updateQMSItems(@RequestBody QMSItemsDTO dto);

    @RequestMapping(value = "/item/deleteQMSItems", method = RequestMethod.POST)
    Boolean deleteQMSItems(@RequestBody QMSItemsDTO dto);

    @RequestMapping(value = "/item/listQMSItems", method = RequestMethod.POST)
    List<QMSItemsModel> listQMSItems(@RequestBody QMSItemsQuery qmsItemsQuery);
}
