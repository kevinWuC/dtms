package com.medical.dtms.common.model.train;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @version： TrainConfigStatementModel.java v 1.0, 2019年09月03日 14:17 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class TrainConfigStatementModel {

    /**
     * 培训类型名称
     **/
    private String trainTypeName;
    /**
     * 培训名称
     **/
    private String trainName;
    /**
     * 培训内容
     **/
    private String trainDescription;
    /**
     * 开始日期
     **/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;
    /**
     * 结束日期
     **/
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;
    /**
     * 培训文件列表
     **/
    private List<TrainFileModel> listFile;
    /**
     * 考试名称
     **/
    private String examName;
    /**
     * 培训用户姓名串串
     **/
    private String trainUserNames;

}
