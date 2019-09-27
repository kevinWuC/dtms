package com.medical.dtms.service.dataobject.train;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @version： TrainConfigDO.java v 1.0, 2019年08月29日 9:56 huangshuaiquan Exp$
 * @Description  培训配置
 **/
@Data
@Table(name = "tb_dtms_train_config")
public class TrainConfigDO {
    /**
     * 自增主键
     **/
    private Long id;
    /**
     * 业务主键
     **/
    @LogTag(alias = "bizId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * 培训类型ID
     **/
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    @LogTag(alias = "trainTypeId", builtinType = BuiltinTypeHandler.NORMAL)
    private Long trainTypeId;
    /**
     * 培训名称
     **/
    @LogTag(alias = "trainName", builtinType = BuiltinTypeHandler.NORMAL)
    private String trainName;
    /**
     * 培训内容
     **/
    @LogTag(alias = "trainDescription", builtinType = BuiltinTypeHandler.NORMAL)
    private String trainDescription;
    /**
     * 开始日期
     **/
    @LogTag(alias = "startDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date startDate;
    /**
     * 结束日期
     **/
    @LogTag(alias = "endDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date endDate;
    /**
     * 是否启用 false-不启用 true-启用
     **/
    @LogTag(alias = "isStart", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isStart;
    /**
     * 结果
     **/
    @LogTag(alias = "answer", builtinType = BuiltinTypeHandler.NORMAL)
    private String answer;
    /**
     * 培训信息
     **/
    @LogTag(alias = "trainInfo", builtinType = BuiltinTypeHandler.NORMAL)
    private String trainInfo;
    /**
     * 考试Id
     **/
    @LogTag(alias = "examId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long examId;
    /**
     * 考试名称
     **/
    @LogTag(alias = "examName", builtinType = BuiltinTypeHandler.NORMAL)
    private String examName;
    /**
     * 及格分数
     **/
    @LogTag(alias = "passPoint", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer passPoint;
    /**
     * 总分
     **/
    @LogTag(alias = "totalPoint", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer totalPoint;
    /**
     * 应培训时数
     **/
    @LogTag(alias = "readFen", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer readFen;
    /**
     * 删除标记:true-删除，false-正常
     */
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isDeleted;
    /**
     * 创建用户
     */
    private String creator;
    /**
     * 创建用户主键
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 修改用户
     */
    private String modifier;
    /**
     * 修改用户主键
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}