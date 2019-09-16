package com.medical.dtms.service.manager.train;

import com.medical.dtms.common.model.train.SimpleTrainFileModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.train.TrainFileDTO;
import com.medical.dtms.dto.train.query.TrainFileQuery;
import com.medical.dtms.service.dataobject.train.TrainFilesDO;
import com.medical.dtms.service.mapper.train.TrainFilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： TrainFilesManager.java v 1.0, 2019年08月30日 16:15 huangshuaiquan Exp$
 * @Description
 **/
@Service
public class TrainFilesManager {

    @Autowired
    private TrainFilesMapper trainFilesMapper;

    /**
     * 培训与文件关联 - 新增
     **/
    public int insert(List<TrainFileDTO> dtos) {
        List<TrainFilesDO> filesDOS = BeanConvertUtils.convertList(dtos, TrainFilesDO.class);
        return trainFilesMapper.insert(filesDOS);
    }

    /**
     * 培训与文件关联 - 删除
     **/
    public int deleteByPrimaryKey(Long bizId) {
        return trainFilesMapper.deleteByPrimaryKey(bizId);
    }

    /**
     * 培训与文件关联 - 多选删除
     **/
    public int deleteByPrimaryKeyList(List<Long> bizIds) {
        return trainFilesMapper.deleteByPrimaryKeyList(bizIds);
    }

    /**
     * 主键查询培训与文件关联是否存在
     **/
    public TrainFileDTO selectByPrimaryKey(Long bizId) {
        TrainFilesDO aDo = trainFilesMapper.selectByPrimaryKey(bizId);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, TrainFileDTO.class);
    }

    /**
     * 根据培训ID删除关联
     **/
    public int deleteTrainFilesByTrainId(Long TrainId) {
        TrainFilesDO aDo = new TrainFilesDO();
        aDo.setTrainId(TrainId);
        return trainFilesMapper.deleteByCondition(aDo);
    }

    /**
     * 根据文件ID删除关联
     **/
    public int deleteTrainFilesByFileId(Long FileId) {
        TrainFilesDO aDo = new TrainFilesDO();
        aDo.setFileId(FileId);
        return trainFilesMapper.deleteByCondition(aDo);
    }

    /**
     * 查询培训和文件存在的关联
     **/
    public List<TrainFileDTO> listTrainFiles(TrainFileQuery query) {
        List<TrainFilesDO> dos = trainFilesMapper.listTrainFiles(query);
        return BeanConvertUtils.convertList(dos, TrainFileDTO.class);
    }

    /**
     * 通过 培训ids 查询培训文件信息
     */
    public List<SimpleTrainFileModel> listFileInfoByTrainIds(List<Long> trainIds) {
        return trainFilesMapper.listFileInfoByTrainIds(trainIds);
    }

}
