package com.medical.dtms.service.manager.file;

import com.github.yeecode.objectlogger.client.service.LogClient;
import com.medical.dtms.common.model.file.ApplyCheckModel;
import com.medical.dtms.common.model.file.FileApplyDetailModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.file.FileApplyDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import com.medical.dtms.service.dataobject.file.FileApplyDO;
import com.medical.dtms.service.mapper.file.FileApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： FileApplyManager.java v 1.0, 2019年08月30日 15:59 wuxuelin Exp$
 * @Description
 **/
@Service
public class FileApplyManager {

    @Autowired
    private FileApplyMapper applyMapper;
    @Autowired
    private LogClient logClient;

    /**
     * 添加申请
     */
    public Integer addFileApply(FileApplyDTO dto) {
        FileApplyDO aDo = BeanConvertUtils.convert(dto, FileApplyDO.class);
        return applyMapper.insert(aDo);
    }

    /**
     * 获取申请详情
     */
    public FileApplyDTO getApplyDetail(Long bizId) {
        FileApplyDO aDo = applyMapper.selectByPrimaryKey(bizId);
        return BeanConvertUtils.convert(aDo, FileApplyDTO.class);
    }

    /**
     * 更新申请结果
     */
    public Integer update(FileApplyDTO apply) {
        FileApplyDO aDo = BeanConvertUtils.convert(apply, FileApplyDO.class);
        return applyMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 申请审核 分页查询
     */
    public List<ApplyCheckModel> pageListApplyCheck(FileModelQuery query) {
        return applyMapper.pageListApplyCheck(query);
    }

    /**
     * 申请回显
     */
    public FileApplyDetailModel selectById(Long id) {
        return applyMapper.selectById(id);
    }
}
