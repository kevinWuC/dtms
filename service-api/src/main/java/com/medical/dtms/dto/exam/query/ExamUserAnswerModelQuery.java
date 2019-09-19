package com.medical.dtms.dto.exam.query;

import lombok.Data;

/**
 * @version: ExamUserAnswerModelQuery$.java v 1.0,2019$年09$月17$日19:06$ ruanqiuhan Exp$
 * @Descrption 用户 - 考试 查询 query
 **/
@Data
public class ExamUserAnswerModelQuery {

    /**
     * 试卷ID
     **/
    private Long examId;
    /**
     * 用户ID
     **/
    private Long userId;
}
