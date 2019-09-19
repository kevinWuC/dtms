package com.medical.dtms.web.controller.dept;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.dept.QMSDeptInJobModel;
import com.medical.dtms.common.model.dept.QMSDeptModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.dept.QMSDeptDTO;
import com.medical.dtms.dto.dept.query.QMSDeptQuery;
import com.medical.dtms.feignservice.dept.QMSDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version： QMSDeptController.java v 1.0, 2019年08月14日 14:14  Exp$
 * @Description 部门设置 控制类
 **/
@RestController
public class QMSDeptController {

    @Autowired
    private QMSDeptService qmsDeptService;

    /**
     * @param [deptDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 部门类别 - 添加功能
     **/
    @RequestMapping(value = "/dept/addQMSDept", method = RequestMethod.POST)
    public Result<Boolean> addQMSDept(@RequestBody QMSDeptDTO deptDTO) {
        if (null == deptDTO) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "所有内容均为必填项，请填写完整");
        }
        if (StringUtils.isBlank(deptDTO.getCode())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "编号为空");
        }
        if (StringUtils.isBlank(deptDTO.getDeptName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "名称为空");
        }
        if (null == deptDTO.getSortCode()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "排序码为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        deptDTO.setCreator(info.getDspName());
        deptDTO.setCreatorId(info.getUserId());
        qmsDeptService.addQMSDept(deptDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 部门类别 - 编辑功能
     **/
    @RequestMapping(value = "/dept/updateQMSDept", method = RequestMethod.POST)
    public Result<Boolean> updateQMSDept(@RequestBody QMSDeptDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());
        qmsDeptService.updateQMSDept(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 部门类别 - 删除功能
     **/
    @RequestMapping(value = "/dept/deleteQMSDept", method = RequestMethod.POST)
    public Result<Boolean> deleteQMSDept(@RequestBody QMSDeptDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());
        qmsDeptService.deleteQMSDept(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.model.dept.QMSDeptModel>>
     * @description 部门管理 - 列表展示
     **/
    @RequestMapping(value = "/dept/listQMSDept", method = RequestMethod.POST)
    public Result<List<QMSDeptModel>> listQMSDept(@RequestBody QMSDeptQuery query) {
        List<QMSDeptModel> list = qmsDeptService.listQMSDept(query);
        return Result.buildSuccess(list);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dept.QMSDeptInJobModel>>
     * @description 用户管理 - 职位授权 - 部门 和 职位列表
     **/
    @RequestMapping(value = "/dept/listDeptInJob")
    public Result<List<QMSDeptInJobModel>> listDeptInJobs() {
        List<QMSDeptInJobModel> models = qmsDeptService.listDeptInJobs();
        return Result.buildSuccess(models);
    }


}
