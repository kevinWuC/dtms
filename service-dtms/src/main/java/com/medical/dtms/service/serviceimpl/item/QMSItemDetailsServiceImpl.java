package com.medical.dtms.service.serviceimpl.item;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.dto.item.QMSItemsDTO;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.feignservice.item.QMSItemDetailsService;
import com.medical.dtms.common.model.item.QMSItemDetailsModel;
import com.medical.dtms.service.dataobject.item.QMSItemDetailsDO;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.item.QMSItemsManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSItemDetailsServiceImpl.java v 1.0, 2019年08月16日 15:09 wuxuelin Exp$
 * @Description 字典明细
 **/
@RestController
@Slf4j
public class QMSItemDetailsServiceImpl implements QMSItemDetailsService {

    @Autowired
    private QMSItemDetailsManager detailsManager;
    @Autowired
    private QMSItemsManager qmsItemsManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [detailsDTO]
     * @return java.lang.Boolean
     * @description 字典明细 - 添加功能
     **/
    @Override
    public Boolean addQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO) {
        // 校验字典类别是否有被删除
        QMSItemsDTO qmsItemsDTO = qmsItemsManager.selectByPrimaryKey(detailsDTO.getItemsId());
        if (null == qmsItemsDTO) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "字典类别不存在，请重新选择");
        }

        // 根据编号 做唯一性校验
        QMSItemDetailsQuery query = new QMSItemDetailsQuery(detailsDTO.getItemCode(), detailsDTO.getItemsId());
        QMSItemDetailsDTO dto = detailsManager.getQMSItemDetailsByCondition(query);
        if (null != dto) {
            // 存在,提示重复
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "编号重复，请重新填写");
        }

        try {
            // 不存在，直接新增
            detailsDTO.setBizId(idGenerator.nextId());
            detailsManager.insert(detailsDTO);
        } catch (Exception e) {
            log.error("新增字典类别明细失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [detailsDTO]
     * @return java.lang.Boolean
     * @description 字典明细 - 编辑功能
     **/
    @Override
    public Boolean updateQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO) {
        // 校验是否被删除
        QMSItemDetailsQuery query = new QMSItemDetailsQuery(detailsDTO.getBizId());
        QMSItemDetailsDTO dto = detailsManager.getQMSItemDetailsByCondition(query);
        if (null == dto) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条明细已被删除,无法操作");
        }

        // 未被删除
        // 校验能否被编辑
        if (null != dto.getAllowEdit() && !dto.getAllowEdit()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条明细不允许被编辑");
        }

        // 重复性校验
        if (StringUtils.isNotBlank(detailsDTO.getItemCode())) {
            query = new QMSItemDetailsQuery(detailsDTO.getItemCode(), dto.getItemsId());
            QMSItemDetailsDTO details = detailsManager.getQMSItemDetailsByCondition(query);
            if (null != details && !details.getBizId().equals(detailsDTO.getBizId())) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "编号重复，请重新填写");
            }
        }

        try {
            // 能被编辑,更新
            detailsManager.updateQMSItemDetails(detailsDTO);
        } catch (Exception e) {
            log.error("更新字典明细失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [detailsDTO]
     * @return java.lang.Boolean
     * @description 字典明细 - 删除功能(逻辑删除)
     **/
    @Override
    public Boolean deleteQMSItemDetails(@RequestBody QMSItemDetailsDTO detailsDTO) {
        // 校验是否被删除
        QMSItemDetailsQuery query = new QMSItemDetailsQuery(detailsDTO.getBizId());
        QMSItemDetailsDTO dto = detailsManager.getQMSItemDetailsByCondition(query);
        if (null == dto) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条明细已被删除，请勿重复操作");
        }

        // 未被删除
        // 校验能否被删除
        if (null != dto.getAllowDelete() && !dto.getAllowDelete()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条明细不允许被删除");
        }

        try {
            detailsDTO.setIsDeleted(true);
            detailsManager.updateQMSItemDetails(detailsDTO);
        } catch (Exception e) {
            log.error("数据字典明细删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.item.QMSItemDetailsModel>
     * @description 字典明细 - 分页展示功能
     **/
    @Override
    public PageInfo<QMSItemDetailsModel> pageListQMSItemDetails(@RequestBody QMSItemDetailsQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<QMSItemDetailsModel> dos = detailsManager.listDetailsByItemsId(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(dos);
    }
}
