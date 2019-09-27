package com.medical.dtms.service.dataobject.file;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.medical.dtms.common.convert.LongJsonDeserializer;
import com.medical.dtms.common.convert.LongJsonSerializer;
import com.medical.dtms.logclient.annotation.LogTag;
import com.medical.dtms.logclient.handler.BuiltinTypeHandler;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "tb_dtms_file_approve_log")
public class FileApproveLogDO {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 业务主键
     */
    @LogTag(alias = "bizId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long bizId;
    /**
     * id 集合
     */
    @LogTag(alias = "bizIdList", builtinType = BuiltinTypeHandler.NORMAL)
    private List<Long> bizIdList;
    /**
     * 文件id
     */
    @LogTag(alias = "fileId", builtinType = BuiltinTypeHandler.NORMAL)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long fileId;
    /**
     * 文件编号
     */
    @LogTag(alias = "fileNo", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileNo;
    /**
     * 文件名
     */
    @LogTag(alias = "fileName", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileName;
    /**
     * 文件版本
     */
    @LogTag(alias = "fileVersion", builtinType = BuiltinTypeHandler.NORMAL)
    private String fileVersion;
    /**
     * 到达时间
     */
    @LogTag(alias = "beginDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date beginDate;
    /**
     * 完成时间
     */
    @LogTag(alias = "finshedDate", builtinType = BuiltinTypeHandler.NORMAL)
    private Date finshedDate;
    /**
     * 处理人id
     */
    @LogTag(alias = "approveUserId", builtinType = BuiltinTypeHandler.NORMAL)
    private String approveUserId;
    /**
     * 处理人
     */
    @LogTag(alias = "approveUserName", builtinType = BuiltinTypeHandler.NORMAL)
    private String approveUserName;
    /**
     * 结果 1提交 2 待处理 3 拒绝 4 ok
     */
    @LogTag(alias = "result", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer result;
    /**
     * 意见
     */
    @LogTag(alias = "remark", builtinType = BuiltinTypeHandler.NORMAL)
    private String remark;
    /**
     * 记录类型 1新增记录 2 修改记录
     */
    @LogTag(alias = "operatorType", builtinType = BuiltinTypeHandler.NORMAL)
    private Integer operatorType;
    /**
     * 0-未删除  1-删除
     */
    @LogTag(alias = "isDeleted", builtinType = BuiltinTypeHandler.NORMAL)
    private Boolean isDeleted;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     *  修改人
     */
    private String modifier;
    /**
     * 修改人id
     */
    private String modifierId;
    /**
     * 修改时间
     */
    private Date gmtModified;
}