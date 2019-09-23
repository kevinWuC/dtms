/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.web.controller.question;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.feignservice.item.QMSItemDetailsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.question.QuestionExcelModel;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.ExcelImportUtils;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.feignservice.question.DtmsQuestionService;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

/**
 * 试题管理
 * @author shenqifeng
 * @version $Id: DtmsQuestionController.java, v 0.1 2019年8月27日 下午9:51:53 shenqifeng Exp $
 */
@RestController
@Slf4j
public class DtmsQuestionController {

    @Autowired
    private DtmsQuestionService questionService;
    @Autowired
    private QMSItemDetailsService qmsItemDetailsService;

    /**
     * 新增试题
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/question/saveQuestion", method = RequestMethod.POST)
    public Result<Boolean> saveQuestion(@RequestBody DtmsQuestionsDTO questionsDTO) {
        //参数校验
        if (null == questionsDTO || null == questionsDTO.getAnswer()
            || null == questionsDTO.getExamTypeId() || null == questionsDTO.getQuestionsBankTypeId()
            || null == questionsDTO.getQuestionsTypeId()
            || StringUtils.isBlank(questionsDTO.getQuestionA())
            || StringUtils.isBlank(questionsDTO.getQuestionB())
            || StringUtils.isBlank(questionsDTO.getQuestionC())
            || StringUtils.isBlank(questionsDTO.getQuestionD())
            || StringUtils.isBlank(questionsDTO.getQuestionTitle())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        OperatorInfo operatorInfo = SessionTools.getOperator();
        questionsDTO.setCreateUserId(operatorInfo.getBizId());
        questionsDTO.setCreateUserName(operatorInfo.getDspName());
        questionsDTO.setModifyUserId(operatorInfo.getBizId());
        questionsDTO.setModifyUserName(operatorInfo.getDspName());

        Boolean flag = questionService.saveQuestion(questionsDTO);

        if (!flag) {
            return new Result<Boolean>(ErrorCodeEnum.FAILED.getErrorCode(), flag, "新增失败", flag);
        }
        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), flag, "新增成功", flag);
    }

    /**
     * 修改试题
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/question/updateQuestion", method = RequestMethod.POST)
    public Result<Boolean> updateQuestion(@RequestBody DtmsQuestionsDTO questionsDTO) {
        //参数校验
        if (null == questionsDTO || null == questionsDTO.getBizId()
            || null == questionsDTO.getAnswer() || null == questionsDTO.getExamTypeId()
            || null == questionsDTO.getQuestionsBankTypeId()
            || null == questionsDTO.getQuestionsTypeId()
            || StringUtils.isBlank(questionsDTO.getQuestionA())
            || StringUtils.isBlank(questionsDTO.getQuestionB())
            || StringUtils.isBlank(questionsDTO.getQuestionC())
            || StringUtils.isBlank(questionsDTO.getQuestionD())
            || StringUtils.isBlank(questionsDTO.getQuestionTitle())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        OperatorInfo operatorInfo = SessionTools.getOperator();
        questionsDTO.setModifyUserId(operatorInfo.getBizId());
        questionsDTO.setModifyUserName(operatorInfo.getDspName());

        Boolean flag = questionService.updateQuestion(questionsDTO);

        if (!flag) {
            return new Result<Boolean>(ErrorCodeEnum.FAILED.getErrorCode(), flag, "修改失败", flag);
        }
        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), flag, "修改成功", flag);
    }

    /**
     * 删除试题
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/question/removeQuestion", method = RequestMethod.POST)
    public Result<Boolean> removeQuestion(@RequestBody DtmsQuestionsDTO questionsDTO) {
        //参数校验
        if (null == questionsDTO || null == questionsDTO.getBizId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        OperatorInfo operatorInfo = SessionTools.getOperator();
        questionsDTO.setModifyUserId(operatorInfo.getBizId());
        questionsDTO.setModifyUserName(operatorInfo.getDspName());
        questionsDTO.setDeleted(Boolean.TRUE);
        Boolean flag = questionService.updateQuestion(questionsDTO);

        if (!flag) {
            return new Result<Boolean>(ErrorCodeEnum.FAILED.getErrorCode(), flag, "删除失败", flag);
        }
        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), flag, "删除成功", flag);
    }

    /**
     * 查询试题详情
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/question/getQuestionById", method = RequestMethod.POST)
    public Result<DtmsQuestionsDTO> getQuestionById(@RequestBody DtmsQuestionsDTO questionsDTO) {
        if (null == questionsDTO || null == questionsDTO.getBizId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        questionsDTO = questionService.getQuestionById(questionsDTO.getBizId());
        return new Result<DtmsQuestionsDTO>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "查询成功",
            questionsDTO);
    }

    /**
     * 分页查询试题
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/question/listQuestionsByQuery", method = RequestMethod.POST)
    public Result<PageInfo> listQuestionsByQuery(@RequestBody QuestionQuery query) {
        if (null == query) {
            query.setPageNo(1);
            query.setPageSize(10);
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
        PageInfo pageInfo = questionService.listQuestionsByQuery(query);
        return new Result<PageInfo>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "查询成功", pageInfo);
    }

    /**
     * 导入试题
     * 
     * @param file
     * @return
     */
    @RequestMapping(value = "/question/upload", method = RequestMethod.POST)
    public Result<Boolean> upload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), false, "文件为空");
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            if (!StringUtils.equals(suffixName, ".xlsx")
                && !StringUtils.equals(suffixName, ".xls")) {
                log.error("不是excel文件");
                return new Result<>(ErrorCodeEnum.PARAM_ERROR.getErrorCode(), false, "请上传excel文件");
            }
            //创建一个临时文件，用于暂时存放
            File tmpFile = File.createTempFile(System.currentTimeMillis() + "", suffixName);
            //将MultipartFile 转换为 File 临时文件
            file.transferTo(tmpFile);

            List<QuestionExcelModel> list = ExcelImportUtils.readFile(tmpFile,
                QuestionExcelModel.class);
            if (Collections.isEmpty(list)) {
                log.error("空excel");
                return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), false, "空excel");
            }
            List<DtmsQuestionsDTO> questionsDTOs = new ArrayList<DtmsQuestionsDTO>();
            questionsDTOs = BeanConvertUtils.convertList(list, DtmsQuestionsDTO.class);
            //获取用户信息
            OperatorInfo operatorInfo = SessionTools.getOperator();
            for (DtmsQuestionsDTO questionsDTO : questionsDTOs) {
                QMSItemDetailsDTO qmsItemDetailsDTO1 = qmsItemDetailsService.queryQMSItemDetails(questionsDTO.getExamTypeId());
                if (qmsItemDetailsDTO1 == null){
                    log.error("培训类别"+questionsDTO.getExamTypeId()+"在数据库中未找到，请重新填写");
                    return new Result<>(ErrorCodeEnum.PARAM_ERROR.getErrorCode(), false, "培训类别"+questionsDTO.getExamTypeId()+"在数据库中未找到，请重新填写");
                }
                QMSItemDetailsDTO qmsItemDetailsDTO2 = qmsItemDetailsService.queryQMSItemDetails(questionsDTO.getQuestionsBankTypeId());
                if (qmsItemDetailsDTO2 == null){
                    log.error("试题库类别"+questionsDTO.getQuestionsBankTypeId()+"在数据库中未找到，请重新填写");
                    return new Result<>(ErrorCodeEnum.PARAM_ERROR.getErrorCode(), false, "试题库类别"+questionsDTO.getQuestionsBankTypeId()+"在数据库中未找到，请重新填写");
                }
                QMSItemDetailsDTO qmsItemDetailsDTO3 = qmsItemDetailsService.queryQMSItemDetails(questionsDTO.getQuestionsTypeId());
                if (qmsItemDetailsDTO3 == null){
                    log.error("试题类别"+questionsDTO.getQuestionsTypeId()+"在数据库中未找到，请重新填写");
                    return new Result<>(ErrorCodeEnum.PARAM_ERROR.getErrorCode(), false, "试题类别"+questionsDTO.getQuestionsTypeId()+"在数据库中未找到，请重新填写");
                }
                questionsDTO.setCreateUserId(operatorInfo.getBizId());
                questionsDTO.setCreateUserName(operatorInfo.getDspName());
                questionsDTO.setModifyUserId(operatorInfo.getBizId());
                questionsDTO.setModifyUserName(operatorInfo.getDspName());
            }
            questionService.saveBatchQuestion(questionsDTOs);
            //上传完成 删除临时文件
            tmpFile.delete();
            return new Result<>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "导入成功");
        } catch (Exception e) {
            log.error("上传失败", e);
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), false, "上传失败");
        }
    }

    /**
     * 查询类别
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/question/listDetailsByItemsIdForQuestion", method = RequestMethod.POST)
    public Result<List<QuestionItemModel>> listDetailsByItemsIdForQuestion(@RequestBody QuestionQuery query) {
        if (null == query || StringUtils.isBlank(query.getType())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        List<QuestionItemModel> list = questionService
            .listDetailsByItemsIdForQuestion(query.getType());
        return new Result<List<QuestionItemModel>>(ErrorCodeEnum.SUCCESS.getErrorCode(), true,
            "查询成功", list);
    }

}
