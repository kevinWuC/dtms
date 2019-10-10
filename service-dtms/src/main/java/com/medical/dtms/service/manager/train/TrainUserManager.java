package com.medical.dtms.service.manager.train;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.exam.ExamTotalModel;
import com.medical.dtms.common.model.train.MyTrainTestModel;
import com.medical.dtms.common.model.train.TrainUserModel;
import com.medical.dtms.common.model.train.TrainUserQueryModel;
import com.medical.dtms.common.model.train.query.TrainSubmitAnswerQuery;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.dto.train.query.TrainUserQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.train.TrainUserDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.train.TrainUserMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： TrainUserManager.java v 1.0, 2019年08月20日 17:43  Exp$
 * @Description
 **/
@Service
public class TrainUserManager {

    @Autowired
    private TrainUserMapper trainUserMapper;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;


    /**
     * 校验培训用户是否存在
     * @return
     */
    public TrainUserDTO getTrainUserByCondition(Long bizId) {
        return trainUserMapper.getTrainUserByCondition(bizId);
    }


    /**
     * 培训统计 - 分页展示查看查询
     */
    public List<TrainUserModel> pageListTrainUser(TrainUserQuery query) {
        List<TrainUserModel> trainDos = trainUserMapper.pageListTrainUser(query);
        if (CollectionUtils.isEmpty(trainDos)) {
            return new ArrayList<>();
        }
        return trainDos;
    }

    /**
     * 培训管理 - 通过 培训ID/用户ID 查询关联
     **/
    public List<TrainUserDTO> listTrainUserByCondition(TrainUserQuery query) {
        List<TrainUserDO> dos = trainUserMapper.listTrainUserByCondition(query);
        return BeanConvertUtils.convertList(dos, TrainUserDTO.class);
    }

    /**
     * 培训管理 - 新增培训人员
     **/
    public Integer insert(List<TrainUserDTO> dtos) {
        List<TrainUserDO> dos = BeanConvertUtils.convertList(dtos, TrainUserDO.class);
        return trainUserMapper.insert(dos);
    }

    /**
     * 培训管理 - 通过培训id 删除数据
     **/
    public Integer deleteTrainUserByTrainID(Long trainID) {
        TrainUserDO aDo = new TrainUserDO();
        aDo.setTrainId(trainID);
        return trainUserMapper.deleteTrainUserByCondition(aDo);
    }

    /**
     * 培训管理 - 通过用户id 删除数据
     **/
    public Integer deleteTrainUserByUserID(Long userId) {
        TrainUserDO aDo = new TrainUserDO();
        aDo.setTrainId(userId);
        return trainUserMapper.deleteTrainUserByCondition(aDo);
    }

    /**
     * 培训统计-导出
     */
    public List<TrainUserModel> exportTrain(TrainUserQuery query) {
        List<TrainUserModel> trainDos = trainUserMapper.pageListTrainUser(query);
        if (CollectionUtils.isEmpty(trainDos)) {
            return new ArrayList<>();
        }
        return trainDos;
    }


    /**
     * 培训及时率 - 列表查询
     */
    public List<TrainUserModel> listTrainTimely(TrainUserQuery query) {
        List<TrainUserModel> trainDos = trainUserMapper.listTrainTimely(query);
        if (CollectionUtils.isEmpty(trainDos)) {
            return new ArrayList<>();
        }
        return trainDos;
    }


    /**
     * 我的培训 - 查看
     */
    public List<TrainUserModel> viewMyTrain(TrainUserQuery query) {
        return trainUserMapper.viewMyTrain(query);
    }


    /**
     * 我的培训 - 开始考试
     */
    public List<TrainUserModel> beginTrainExam(TrainUserQuery query) {
        List<TrainUserModel> trainDos = trainUserMapper.beginTrainExam(query);
        if (CollectionUtils.isEmpty(trainDos)) {
            return new ArrayList<>();
        }
        return trainDos;
    }


    /**
     * 我的培训 - 查询培训试卷
     */
    public List<TrainUserQueryModel> listTrainExam(TrainUserQuery query) {
        List<TrainUserQueryModel> trainDos = trainUserMapper.listTrainExam(query);
        if (CollectionUtils.isEmpty(trainDos)) {
            return new ArrayList<>();
        }
        return trainDos;
    }

    /**
     * 新增记录
     */
    public Integer addTrainUser(TrainUserDTO dto) {
        TrainUserDO newDo = BeanConvertUtils.convert(dto, TrainUserDO.class);

        TrainUserDO oldDo = new TrainUserDO();
        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_INSERT.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "培训管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return trainUserMapper.addTrainUser(newDo);
    }


    /**
     * 考试统计 - 分页展示查看查询
     */
    public List<ExamTotalModel> pageListExamTotal(TrainUserQuery query) {
        List<ExamTotalModel> trainDos = trainUserMapper.pageListExamTotal(query);
        if (CollectionUtils.isEmpty(trainDos)) {
            return new ArrayList<>();
        }
        return trainDos;
    }

    /**
     * 考试统计-导出
     */
    public List<ExamTotalModel> exportExam(TrainUserQuery query) {
        List<ExamTotalModel> trainDos = trainUserMapper.pageListExamTotal(query);
        if (CollectionUtils.isEmpty(trainDos)) {
            return new ArrayList<>();
        }
        return trainDos;
    }

    /**
     * 主键查询 用户 - 培训 是否存在
     */
    public TrainUserDTO getTrainUserByPrimaryKey(TrainSubmitAnswerQuery query) {
        TrainUserDO userDO = trainUserMapper.getTrainUserByPrimaryKey(query);
        return BeanConvertUtils.convert(userDO, TrainUserDTO.class);
    }

    /**
     * 查询考试 id
     */
    public MyTrainTestModel listExamIds(Long bizId) {
        return trainUserMapper.listExamIds(bizId);
    }
}
