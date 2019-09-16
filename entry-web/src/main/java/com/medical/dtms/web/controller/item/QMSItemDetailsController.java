package com.medical.dtms.web.controller.item;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.feignservice.item.QMSItemDetailsService;
import com.medical.dtms.common.model.item.QMSItemDetailsModel;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： QMSItemDetailsController.java v 1.0, 2019年08月16日 14:56 wuxuelin Exp$
 * @Description 字典明细
 **/
@RestController
public class QMSItemDetailsController {

    @Autowired
    private QMSItemDetailsService detailsService;

    /**
     * @param [detailsDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 字典明细 - 添加功能
     **/
    @RequestMapping(value = "/detail/addQMSItemDetails", method = RequestMethod.POST)
    public Result<Boolean> addQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO) {
        if (null == detailsDTO) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "所有内容均为必填项，请填写完整");
        }
        if (StringUtils.isBlank(detailsDTO.getItemCode())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "代码为空");
        }
        if (StringUtils.isBlank(detailsDTO.getItemName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "名称为空");
        }
        if (null == detailsDTO.getEnabled() || null == detailsDTO.getAllowEdit() || null == detailsDTO.getAllowDelete()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "选项为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        detailsDTO.setCreator(info.getDspName());
        detailsDTO.setCreatorId(info.getUserId());
        detailsService.addQMSItemDetails(detailsDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [detailsDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 字典明细 - 编辑功能
     **/
    @RequestMapping(value = "/detail/updateQMSItemDetails", method = RequestMethod.POST)
    public Result<Boolean> updateQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO) {
        if (null == detailsDTO || null == detailsDTO.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }

        OperatorInfo info = SessionTools.getOperator();
        detailsDTO.setModifier(info.getDspName());
        detailsDTO.setModifierId(info.getUserId());
        detailsService.updateQMSItemDetails(detailsDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [detailsDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 字典明细 - 删除功能
     **/
    @RequestMapping(value = "/detail/deleteQMSItemDetails", method = RequestMethod.POST)
    public Result<Boolean> deleteQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO) {
        if (null == detailsDTO || null == detailsDTO.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }

        OperatorInfo info = SessionTools.getOperator();
        detailsDTO.setModifier(info.getDspName());
        detailsDTO.setModifierId(info.getUserId());
        detailsService.deleteQMSItemDetails(detailsDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo<com.medical.dtms.model.item.QMSItemDetailsModel>>
     * @description 字典明细 - 分页展示功能
     **/
    @RequestMapping(value = "/detail/pageListQMSItemDetails", method = RequestMethod.POST)
    public Result<PageInfo<QMSItemDetailsModel>> pageListQMSItemDetails(@RequestBody QMSItemDetailsQuery query) {
        if (null == query || null == query.getItemsId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "类别id 为空");
        }
        checkParams(query);
        PageInfo<QMSItemDetailsModel> pageInfo = detailsService.pageListQMSItemDetails(query);
        return Result.buildSuccess(pageInfo);
    }


    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(QMSItemDetailsQuery query) {
        if (null == query) {
            query = new QMSItemDetailsQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }

}
