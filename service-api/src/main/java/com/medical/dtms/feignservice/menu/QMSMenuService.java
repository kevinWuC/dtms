package com.medical.dtms.feignservice.menu;

import com.medical.dtms.dto.menu.QMSMenuDTO;
import com.medical.dtms.dto.menu.query.QMSMenuQuery;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： QMSMenuService.java v 1.0, 2019年08月19日 16:40 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface QMSMenuService {

    @RequestMapping(value = "/menu/addMenu", method = RequestMethod.POST)
    Boolean addMenu(@RequestBody QMSMenuDTO menuDTO);

    @RequestMapping(value = "/menu/updateMenu", method = RequestMethod.POST)
    Boolean updateMenu(@RequestBody QMSMenuDTO menuDTO);

    @RequestMapping(value = "/menu/deleteMenu", method = RequestMethod.POST)
    Boolean deleteMenu(@RequestBody QMSMenuDTO menuDTO);

    @RequestMapping(value = "/menu/listQMSMenus", method = RequestMethod.POST)
    List<QMSMenuModel> listQMSMenus(@RequestBody QMSMenuQuery query);
}
