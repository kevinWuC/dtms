package com.medical.dtms.service.manager.item;

import com.medical.dtms.common.model.dropdown.DropDownDetailsModel;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.service.dataobject.item.QMSItemDetailsDO;
import com.medical.dtms.service.mapper.qms.QMSItemDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @version： QMSItemDetailsManager.java v 1.0, 2019年08月14日 18:08 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSItemDetailsManager {

    @Autowired
    private QMSItemDetailsMapper detailsMapper;

    /**
     * 根据数据字典表主键查询关联的 下拉列表
     **/
    public List<DropDownDetailsModel> listTypeDetailsName(Long itemsId){
        return detailsMapper.listTypeDetailsName(itemsId);
    }


    /**
     * 根据数据字典表主键查询关联的 明细表数据
     */
    public List<QMSItemDetailsDO> listDetailsByItemsId(QMSItemDetailsQuery query) {
        return detailsMapper.listDetailsByItemsId(query);
    }

    /**
     * 文件类别/申请原因下拉
     */
    public List<DropDownModel> listAllFileTypes(DropDownQuery query) {
        return detailsMapper.listAllFileTypes(query);
    }

    /**
     * 根据数据字典表 id 删除数据
     */
    public Integer deleteByItemId(Long itemsId) {
        return detailsMapper.deleteByItemId(itemsId);
    }

    /**
     * 校验明细是否存在
     */
    public QMSItemDetailsDTO getQMSItemDetailsByCondition(QMSItemDetailsQuery query) {
        QMSItemDetailsDO detailsDO = detailsMapper.getQMSItemDetailsByCondition(query);
        if (null == detailsDO) {
            return null;
        }
        return BeanConvertUtils.convert(detailsDO, QMSItemDetailsDTO.class);
    }

    /**
     * 字典明细表 - 添加功能
     */
    public Integer insert(QMSItemDetailsDTO detailsDTO) {
        QMSItemDetailsDO aDo = BeanConvertUtils.convert(detailsDTO, QMSItemDetailsDO.class);
        return detailsMapper.insert(aDo);
    }

    /**
     * 字典明细表 - 编辑功能
     */
    public Integer updateQMSItemDetails(QMSItemDetailsDTO detailsDTO) {
        QMSItemDetailsDO aDo = BeanConvertUtils.convert(detailsDTO, QMSItemDetailsDO.class);
        return detailsMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 字典明细表 - 删除功能
     */
    public Integer deleteQMSItemDetails(Long bizId) {
        return detailsMapper.deleteByPrimaryKey(bizId);
    }

    public List<QuestionItemModel> listDetailsByItemsIdForQuestion(Long itemsId) {
        return detailsMapper.listDetailsByItemsIdForQuestion(itemsId);
    }
    /**
     * 我的培训 - 试题类型id转name
     */
    public List<QuestionItemModel> queryDetailsList(List<Long> questionsTypeIds) {
        return detailsMapper.queryDetailsList(questionsTypeIds);
    }
}
