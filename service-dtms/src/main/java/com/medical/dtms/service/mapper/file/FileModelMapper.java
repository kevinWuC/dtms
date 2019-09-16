package com.medical.dtms.service.mapper.file;

import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.file.*;
import com.medical.dtms.common.model.train.TrainFileModel;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.dto.train.query.TrainFileQuery;
import com.medical.dtms.service.dataobject.file.FileModelDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "mapping/file_model-mapper.xml")
public interface FileModelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FileModelDO record);

    FileDetailModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FileModelDO record);

    FileModelDO getFileByCondition(FileModelQuery query);

    List<FileModel> listFiles(FileModelQuery query);

    List<FileQueryModel> listFileQueryFiles(FileModelQuery query);

    List<FileApprovalModel> pageListFilesInApproval(FileModelQuery query);

    List<FileReceiveModel> pageListForReceive(FileModelQuery query);

    List<DropDownModel> listFileTypes(DropDownQuery query);

    List<DropDownModel> listApplyTypesInFileModel(DropDownQuery query);

    List<DropDownModel> listApplyYears(DropDownQuery query);

    List<TrainFileModel> pageListForTrainFileSelect(TrainFileQuery query);

    List<TrainFileModel> pageListForTrainFileSelected(TrainFileQuery query);

    List<FileApplyModel> pageListApply(FileModelQuery query);

    List<DropDownModel> listApplyDeptInFileModel(DropDownQuery query);

    List<FileYearModel> pageListFileYear(FileModelQuery query);

    List<ArchiveOrInvalidModel> queryListArchiveOrInvalidFile(FileModelQuery query);

    List<BackFileModel> pageListBackFile(FileModelQuery query);
}