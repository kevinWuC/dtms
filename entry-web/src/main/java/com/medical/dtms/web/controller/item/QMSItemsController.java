package com.medical.dtms.web.controller.item;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.item.query.QMSItemsQuery;
import com.medical.dtms.feignservice.item.QMSItemsService;
import com.medical.dtms.dto.item.QMSItemsDTO;
import com.medical.dtms.common.model.item.QMSItemsModel;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version： QMSItemsController.java v 1.0, 2019年08月14日 14:14 wuxuelin Exp$
 * @Description 数据字典 控制类
 **/
@RestController
public class QMSItemsController {

    @Autowired
    private QMSItemsService qmsItemsService;

    /**
     * @param [itemsDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 字典类别 - 添加功能
     **/
    @RequestMapping(value = "/item/addQMSItems", method = RequestMethod.POST)
    public Result<Boolean> addQMSItems(@RequestBody QMSItemsDTO itemsDTO) {
        if (null == itemsDTO) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "所有内容均为必填项，请填写完整");
        }
        if (StringUtils.isBlank(itemsDTO.getCode())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "编号为空");
        }
        if (StringUtils.isBlank(itemsDTO.getFullName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "名称为空");
        }
        if (StringUtils.isBlank(itemsDTO.getCategory())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "分类为空");
        }
        if (null == itemsDTO.getEnabled() || null == itemsDTO.getAllowEdit() || null == itemsDTO.getAllowDelete()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "选项为空");
        }
        if (null == itemsDTO.getSortCode()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "排序码为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        itemsDTO.setModifier(info.getDspName());
        itemsDTO.setModifierId(info.getUserId());
        qmsItemsService.addQMSItems(itemsDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 字典类别 - 编辑功能
     **/
    @RequestMapping(value = "/item/updateQMSItems", method = RequestMethod.POST)
    public Result<Boolean> updateQMSItems(@RequestBody QMSItemsDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setModifier(info.getDspName());
        dto.setModifierId(info.getUserId());
        qmsItemsService.updateQMSItems(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 字典类别 - 删除功能
     **/
    @RequestMapping(value = "/item/deleteQMSItems", method = RequestMethod.POST)
    public Result<Boolean> deleteQMSItems(@RequestBody QMSItemsDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setModifier(info.getDspName());
        dto.setModifierId(info.getUserId());
        qmsItemsService.deleteQMSItems(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [qmsItemsQuery]
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.model.item.QMSItemsModel>>
     * @description 字典类别 - 列表
     **/
    @RequestMapping(value = "/item/listQMSItems", method = RequestMethod.POST)
    public Result<List<QMSItemsModel>> listQMSItems(@RequestBody QMSItemsQuery qmsItemsQuery) {
        List<QMSItemsModel> models = qmsItemsService.listQMSItems(qmsItemsQuery);
        return Result.buildSuccess(models);
    }


}
