package com.medical.dtms.feignservice.train;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.train.TrainConfigModel;
import com.medical.dtms.common.model.train.TrainConfigStatementModel;
import com.medical.dtms.common.model.train.TrainFileModel;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.TrainFileDTO;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.dto.train.query.TrainConfigQuery;
import com.medical.dtms.dto.train.query.TrainFileQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： TrainConfigService.java v 1.0, 2019年08月29日 14:25 huangshuaiquan Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface TrainConfigService {

    @RequestMapping(value = "/trainConfig/addTrain", method = RequestMethod.POST)
    Boolean addTrain(@RequestBody TrainConfigDTO trainConfigDTO);

    @RequestMapping(value = "/trainConfig/deleteTrain", method = RequestMethod.POST)
    Boolean  deleteTrain(@RequestBody TrainConfigDTO trainConfigDTO);

    @RequestMapping(value = "/trainConfig/updateTrain", method = RequestMethod.POST)
    Boolean updateTrain(@RequestBody TrainConfigDTO trainConfigDTO);

    @RequestMapping(value = "/trainConfig/pageListTrains", method = RequestMethod.POST)
    PageInfo<TrainConfigModel> getPageListTrains(@RequestBody TrainConfigQuery query);

    @RequestMapping(value = "/trainConfig/addTrainFile", method = RequestMethod.POST)
    Boolean addTrainFile(@RequestBody TrainFileDTO trainFileDTO);

    @RequestMapping(value = "/trainConfig/deleteTrainFile", method = RequestMethod.POST)
    Boolean deleteTrainFile(@RequestBody TrainFileDTO trainFileDTO);

    @RequestMapping(value = "/trainConfig/pageListTrainFilesSelect", method = RequestMethod.POST)
    PageInfo<TrainFileModel> getPageListTrainFilesSelect(@RequestBody TrainFileQuery query);

    @RequestMapping(value = "/trainConfig/pageListTrainFilesSelected", method = RequestMethod.POST)
    PageInfo<TrainFileModel> getPageListTrainFilesSelected(@RequestBody TrainFileQuery query);

    @RequestMapping(value = "/trainConfig/addTrainUser", method = RequestMethod.POST)
    Boolean addTrainUser(@RequestBody TrainUserDTO trainUserDTO);

    @RequestMapping(value = "/trainConfig/getTrainConfigStatement", method = RequestMethod.POST)
    TrainConfigStatementModel getTrainConfigStatement(@RequestBody TrainConfigDTO trainConfigDTO);

}
