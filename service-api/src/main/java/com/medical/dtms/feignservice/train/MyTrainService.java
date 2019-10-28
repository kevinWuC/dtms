package com.medical.dtms.feignservice.train;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.train.MyTrainBeginModel;
import com.medical.dtms.common.model.train.MyTrainPageModel;
import com.medical.dtms.common.model.train.MyTrainSubmitModel;
import com.medical.dtms.common.model.train.query.MyTrainPageQuery;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： MyTrainService.java v 1.0, 2019年10月11日 14:21 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface MyTrainService {

    @RequestMapping(value = "/train/pageListMyTrain",method = RequestMethod.POST)
    PageInfo<MyTrainPageModel> pageListMyTrain(@RequestBody MyTrainPageQuery query);

    @RequestMapping(value = "/train/beginTrainExam", method = RequestMethod.POST)
    MyTrainBeginModel beginTrainExam(@RequestBody TrainQuestionProcessDTO processDTO);

    @RequestMapping(value = "/train/submitTrainAnswer", method = RequestMethod.POST)
    Boolean submitTrainAnswer(@RequestBody MyTrainSubmitModel model);
}
