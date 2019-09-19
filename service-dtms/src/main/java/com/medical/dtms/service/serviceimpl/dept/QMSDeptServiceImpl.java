package com.medical.dtms.service.serviceimpl.dept;

import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.dept.QMSDeptInJobModel;
import com.medical.dtms.common.model.dept.QMSDeptModel;
import com.medical.dtms.common.model.job.QMSJobsModel;
import com.medical.dtms.common.model.job.SimpleQMSJobsModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.dept.QMSDeptDTO;
import com.medical.dtms.dto.dept.query.QMSDeptQuery;
import com.medical.dtms.feignservice.dept.QMSDeptService;
import com.medical.dtms.service.manager.dept.QMSDeptManager;
import com.medical.dtms.service.manager.job.QMSJobsManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @version： QMSDeptServiceImpl.java v 1.0, 2019年08月14日 14:16  Exp$
 * @Description
 **/
@RestController
@Slf4j
public class QMSDeptServiceImpl implements QMSDeptService {

    @Autowired
    private QMSDeptManager qmsDeptManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private QMSJobsManager jobsManager;


    /**
     * @param [deptDTO]
     * @return java.lang.Boolean
     * @description 部门类别 - 添加功能
     **/
    @Override
    public Boolean addQMSDept(@RequestBody QMSDeptDTO deptDTO) {
        // 根据编号 做唯一性校验
        QMSDeptQuery query = new QMSDeptQuery();
        query.setCode(deptDTO.getCode());
        QMSDeptDTO dto = qmsDeptManager.getQMSDeptByCode(query);
        if (null != dto) {
            // 存在,提示重复
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "编号重复，请重新填写");
        }
        // 不存在，则直接新增
        try {
            deptDTO.setBizId(idGenerator.nextId());
            qmsDeptManager.insert(deptDTO);
        } catch (Exception e) {
            log.error("新增部门类别失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 部门类别 - 编辑功能
     **/
    @Override
    public Boolean updateQMSDept(@RequestBody QMSDeptDTO dto) {
        // 校验是否被删除
        QMSDeptDTO deptDTO = qmsDeptManager.selectByPrimaryKey(dto.getBizId());
        if (null == deptDTO) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该类别不存在");
        }
        // 未被删除且允许编辑，直接更新
        try {
            qmsDeptManager.updateByPrimaryKeySelective(dto);
        } catch (Exception e) {
            log.error("更新部门类别失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 部门类别 - 删除功能(逻辑删除)
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean deleteQMSDept(@RequestBody QMSDeptDTO deptDTO) {
        // 校验是否被删除
        QMSDeptQuery query = new QMSDeptQuery(deptDTO.getBizId());
        QMSDeptDTO dept = qmsDeptManager.getQMSDeptByCode(query);
        if (null == dept) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该部门已被删除，无法操作");
        }
        // 校验是否允许被删除
        if (null != dept.getAllowDelete() && !dept.getAllowDelete()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该类别不允许被删除");
        }


        // 删除子部门
        query = new QMSDeptQuery();
        query.setParentId(deptDTO.getBizId());
        List<QMSDeptModel> models = qmsDeptManager.listQMSDept(query);
        if (CollectionUtils.isNotEmpty(models)) {
            try {
                qmsDeptManager.deleteByPatentId(deptDTO.getBizId());
            } catch (Exception e) {
                log.error("子部门删除失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        try {
            // 删除用户与部门关联表的关联
            qmsDeptManager.deleteByPatentId(deptDTO.getBizId());
        } catch (Exception e) {
            log.error("用户与部门关联表删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        try {
            deptDTO.setIsDeleted(true);
            qmsDeptManager.updateByPrimaryKeySelective(deptDTO);
        } catch (Exception e) {
            log.error("删除部门失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param []
     * @return java.util.List<com.medical.dtms.model.dept.QMSDeptModel>
     * @description 部门类别 - 列表
     **/
    @Override
    public List<QMSDeptModel> listQMSDept(@RequestBody QMSDeptQuery query) {
        if (null == query || null == query.getParentId()) {
            // 一级类别
            query.setParentId(Constants.PARENT_ID);
        }

        List<QMSDeptModel> models = qmsDeptManager.listQMSDept(query);

        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }

        for (QMSDeptModel model : models) {
            query.setParentId(model.getBizId());
            List<QMSDeptModel> list = qmsDeptManager.listQMSDept(query);
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


    /**
     * @param []
     * @return java.util.List<com.medical.dtms.common.model.dept.QMSDeptInJobModel>
     * @description 用户管理 - 职位授权 - 部门 和 职位列表
     **/
    @Override
    public List<QMSDeptInJobModel> listDeptInJobs() {
        // 查询部门列表
        QMSDeptQuery query = new QMSDeptQuery();
        List<QMSDeptModel> models = qmsDeptManager.listQMSDept(query);
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }

        List<String> lastIds = new ArrayList<>();
        Map<Long, List<QMSDeptModel>> map = models.stream().collect(Collectors.groupingBy(QMSDeptModel::getParentId));

        getList(lastIds, map);

        if (CollectionUtils.isEmpty(lastIds)) {
            return new ArrayList<>();
        }

        lastIds = lastIds.stream().distinct().collect(Collectors.toList());

        List<QMSDeptInJobModel> jobModelList = new ArrayList<>();

        // 获取职位
        List<QMSJobsModel> jobsModels = jobsManager.listJobsByDeptIds(lastIds);
        if (CollectionUtils.isNotEmpty(jobsModels)){
            Map<String, List<QMSJobsModel>> jobMap = jobsModels.stream().collect(Collectors.groupingBy(QMSJobsModel::getDeptId));
            QMSDeptInJobModel deptInJobModel;

            for (QMSDeptModel model : models) {
                if (!lastIds.contains(String.valueOf(model.getBizId()))){
                    continue;
                }
                deptInJobModel = new QMSDeptInJobModel();
                BeanUtils.copyProperties(model,deptInJobModel);
                List<QMSJobsModel> list = jobMap.get(String.valueOf(model.getBizId()));
                if (CollectionUtils.isEmpty(list)){
                    deptInJobModel.setJobsModels(new ArrayList<>());
                }else {
                    List<SimpleQMSJobsModel> qmsJobsModels = BeanConvertUtils.convertList(list, SimpleQMSJobsModel.class);
                    deptInJobModel.setJobsModels(qmsJobsModels);
                }

                jobModelList.add(deptInJobModel);
            }
        }

        return jobModelList;
    }



    /**
     * 递归取末级id
     */
    private List<QMSDeptInJobModel> getList(List<String> lastIds, Map<Long, List<QMSDeptModel>> map) {
        if (null == map || map.size() == 0) {
            return new ArrayList<>();
        }

        for (Long aLong : map.keySet()) {
            List<QMSDeptModel> list = map.get(aLong);
            getLastIds(lastIds, map, list);
        }
        return new ArrayList<>();
    }

    private void getLastIds(List<String> lastIds, Map<Long, List<QMSDeptModel>> map, List<QMSDeptModel> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            for (QMSDeptModel deptModel : list) {
                List<QMSDeptModel> models = map.get(deptModel.getBizId());
                if (CollectionUtils.isEmpty(models)){
                    lastIds.add(String.valueOf(deptModel.getBizId()));
                    continue;
                }
                getLastIds(lastIds,map,models);
            }
        }
    }

    /**
     * 递归查询部门
     */
    private void getDeptList(QMSDeptQuery query, QMSDeptModel model) {
        List<QMSDeptModel> models = qmsDeptManager.listQMSDept(query);
        if (CollectionUtils.isEmpty(models)) {
            model.setLastOrNot(true);
            model.setList(new ArrayList<>());
        } else {
            model.setLastOrNot(false);
            model.setList(models);
        }
        for (QMSDeptModel deptModel : models) {
            query.setParentId(deptModel.getBizId());
            getDeptList(query, deptModel);
        }
    }
}