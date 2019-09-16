package com.medical.dtms.service.mapper.qms;

import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @version： DataBaMapper.java v 1.0, 2019年08月22日 9:44 wuxuelin Exp$
 * @Description
 **/
@Repository
public interface DataBaseMapper {

    List<String> showTables(String dataBaseName);
}
