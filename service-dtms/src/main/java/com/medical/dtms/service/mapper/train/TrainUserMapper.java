package com.medical.dtms.service.mapper.train;

import com.medical.dtms.common.model.train.TrainUserModel;
import com.medical.dtms.common.model.train.TrainUserQueryModel;
import com.medical.dtms.dto.train.query.TrainUserQuery;
import com.medical.dtms.service.dataobject.train.TrainUserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @version： TrainUserMapper.java v 1.0, 2019年08月20日 17:43  Exp$
 * @Description
 **/
@Repository
public interface TrainUserMapper {
    //培训
    List<TrainUserModel> pageListTrainUser(TrainUserQuery query);

    List<TrainUserModel> listTrainTimely(TrainUserQuery query);

    List<TrainUserModel> viewMyTrain(TrainUserQuery query);

    List<TrainUserQueryModel> listTrainExam(TrainUserQuery query);

    List<TrainUserModel> beginTrainExam(TrainUserQuery query);

    TrainUserDO getTrainUserByCondition(TrainUserQuery query);

    int insert(List<TrainUserDO> dos);

    int deleteTrainUserByCondition(TrainUserDO userDO);

    List<TrainUserDO> listTrainUserByCondition(TrainUserQuery query);

    Integer addTrainUser(TrainUserDO dto);

    //考试
    List<TrainUserModel> pageListExamTotal(TrainUserQuery query);
}