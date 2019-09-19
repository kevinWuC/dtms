package com.medical.dtms.service.serviceimpl.item;

import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.item.QMSItemDetailsModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.feignservice.item.QMSItemsService;
import com.medical.dtms.common.model.item.QMSItemsModel;
import com.medical.dtms.service.dataobject.item.QMSItemDetailsDO;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.item.QMSItemsManager;
import com.medical.dtms.dto.item.QMSItemsDTO;
import com.medical.dtms.dto.item.query.QMSItemsQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSItemsServiceImpl.java v 1.0, 2019年08月14日 14:16 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class QMSItemsServiceImpl implements QMSItemsService {

    @Autowired
    private QMSItemsManager qmsItemsManager;
    @Autowired
    private QMSItemDetailsManager detailsManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [itemsDTO]
     * @return java.lang.Boolean
     * @description 字典类别 - 添加功能
     **/
    @Override
    public Boolean addQMSItems(@RequestBody QMSItemsDTO itemsDTO) {
        // 根据编号 做唯一性校验
        QMSItemsQuery query = new QMSItemsQuery();
        query.setCode(itemsDTO.getCode());
        query.setParentId(itemsDTO.getParentId());
        QMSItemsDTO dto = qmsItemsManager.getQMSItemByCode(query);
        if (null != dto) {
            // 存在,提示重复
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "编号重复，请重新填写");
        }
        // 不存在，则直接新增
        try {
            itemsDTO.setBizId(idGenerator.nextId());
            qmsItemsManager.insert(itemsDTO);
        } catch (Exception e) {
            log.error("新增字典类别失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 字典类别 - 编辑功能
     **/
    @Override
    public Boolean updateQMSItems(@RequestBody QMSItemsDTO dto) {
        // 校验是否被删除
        QMSItemsDTO itemsDTO = qmsItemsManager.selectByPrimaryKey(dto.getBizId());
        if (null == itemsDTO) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该类别不存在");
        }

        // 校验是否可以被编辑
        if (null != itemsDTO.getAllowEdit() && !itemsDTO.getAllowEdit()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该类别不允许被编辑");
        }

        // 重复性校验
        QMSItemsQuery query = new QMSItemsQuery();
        query.setCode(itemsDTO.getCode());
        query.setParentId(itemsDTO.getParentId());
        QMSItemsDTO item = qmsItemsManager.getQMSItemByCode(query);
        if (null != item && !item.getBizId().equals(dto.getBizId())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "编号重复，请重新填写");
        }

        // 未被删除且允许编辑，直接更新
        try {
            qmsItemsManager.updateByPrimaryKeySelective(dto);
        } catch (Exception e) {
            log.error("更新字典类别失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 字典类别 - 删除功能
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean deleteQMSItems(@RequestBody QMSItemsDTO dto) {
        // 校验是否被删除
        QMSItemsDTO itemsDTO = qmsItemsManager.selectByPrimaryKey(dto.getBizId());
        // 校验是否允许被删除
        if (null != itemsDTO && !itemsDTO.getAllowDelete()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该类别不允许被删除");
        }
        // 校验数据字典明细表 是否有被删除
        QMSItemDetailsQuery query = new QMSItemDetailsQuery();
        query.setItemsId(dto.getBizId());
        List<QMSItemDetailsModel> dos = detailsManager.listDetailsByItemsId(query);
        if (null == itemsDTO) {
            if (CollectionUtils.isEmpty(dos)) {
                return true;
            }
            // 数据字典表有被删除，但是 明细表未被删除，此时删除明细表
            try {
                detailsManager.deleteByItemId(dto.getBizId());
            } catch (Exception e) {
                log.error("明细表删除失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
            return true;
        }

        // 未被删除，校验关联的数据字典明细表是否有被删除
        if (CollectionUtils.isNotEmpty(dos)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), itemsDTO.getFullName() + "有明细不允许被删除，有明细还未被删除");
        }

        // 关联的数据字典明细表全部被删除
        try {
            dto.setIsDeleted(true);
            qmsItemsManager.updateByPrimaryKeySelective(dto);
        } catch (Exception e) {
            log.error("数据字典表删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param []
     * @return java.util.List<com.medical.dtms.model.item.QMSItemsModel>
     * @description 字典类别 - 列表
     **/
    @Override
    public List<QMSItemsModel> listQMSItems(@RequestBody QMSItemsQuery query) {
        if (null == query || null == query.getParentId()) {
            // 一级类别
            query.setParentId(Constants.PARENT_ID);
        }

        List<QMSItemsModel> models = qmsItemsManager.listQMSItems(query);
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }

        for (QMSItemsModel model : models) {
            query.setParentId(model.getBizId());
            List<QMSItemsModel> list = qmsItemsManager.listQMSItems(query);
            if (CollectionUtils.isEmpty(list)) {
                model.setLastOrNot(true);
                model.setList(new ArrayList<>());
                continue;
            }
            model.setLastOrNot(false);
            model.setList(list);
        }

        return models;
    }

}
