package com.medical.dtms.service.manager.item;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.dropdown.DropDownDetailsModel;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.item.QMSItemDetailsModel;
import com.medical.dtms.common.model.item.SimpleQMSItemDetailsModel;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.item.QMSItemDetailsDO;
import com.medical.dtms.service.dataobject.item.QMSItemsDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
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
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;

    /**
     * 根据数据字典表主键查询关联的 下拉列表
     **/
    public List<DropDownDetailsModel> listTypeDetailsName(Long itemsId) {
        return detailsMapper.listTypeDetailsName(itemsId);
    }


    /**
     * 根据数据字典表主键查询关联的 明细表数据
     */
    public List<QMSItemDetailsModel> listDetailsByItemsId(QMSItemDetailsQuery query) {
        return detailsMapper.listDetailsByItemsId(query);
    }

    public QMSItemDetailsDTO queryQMSItemDetails(String itemsName) {
        QMSItemDetailsDO qmsItemDetailsDO = detailsMapper.queryQMSItemDetails(itemsName);
        return BeanConvertUtils.convert(qmsItemDetailsDO, QMSItemDetailsDTO.class);
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
    public Integer insert(QMSItemDetailsDTO dto) {
        QMSItemDetailsDO newDo = BeanConvertUtils.convert(dto, QMSItemDetailsDO.class);

        QMSItemDetailsDO oldDo = new QMSItemDetailsDO();
        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_INSERT.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "字典类别明细管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return detailsMapper.insert(newDo);
    }

    /**
     * 字典明细表 - 编辑功能
     */
    public Integer updateQMSItemDetails(QMSItemDetailsDTO detailsDTO) {

        QMSItemDetailsQuery query = new QMSItemDetailsQuery();
        query.setBizId(detailsDTO.getBizId());
        QMSItemDetailsDO oldDetail = detailsMapper.getQMSItemDetailsByCondition(query);

        QMSItemDetailsDO newDetail = BeanConvertUtils.convert(detailsDTO, QMSItemDetailsDO.class);

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDetail.getBizId()),
                // 操作人
                detailsDTO.getModifier(),
                // 操作类型
                detailsDTO.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : detailsDTO.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDetail.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(detailsDTO.getModifierId()),
                // 备注，这里是操作模块名
                "字典类别明细管理",
                // 旧值
                oldDetail,
                // 新值
                newDetail
        );

        return detailsMapper.updateByPrimaryKeySelective(newDetail);
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
        return null;
    }

    /**
     * 根据id 集合批量查询数据
     */
    public List<SimpleQMSItemDetailsModel> getQMSItemDetailsByIds(List<Long> trainTypeIds) {
        return detailsMapper.getQMSItemDetailsByIds(trainTypeIds);
    }


}
