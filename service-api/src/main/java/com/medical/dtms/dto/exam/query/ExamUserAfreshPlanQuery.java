package com.medical.dtms.dto.exam.query;

import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import lombok.Data;

import java.util.List;

/**
 * @version： ExamUserAfreshPlanQuery.java v 1.0, 2019年09月20日 11:05 huangshuaiquan Exp$
 * @Description
 **/
@Data
public class ExamUserAfreshPlanQuery {

    /**补考人员信息*/
    List<ExamUserPlanModelDTO> afreshExamUserList;

    /***/
    private Long    createUserId;
    /***/
    private String  createUserName;
    /***/
    private Long    modifyUserId;
    /***/
    private String  modifyUserName;

}
