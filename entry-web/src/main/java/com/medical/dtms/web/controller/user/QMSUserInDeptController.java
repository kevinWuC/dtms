package com.medical.dtms.web.controller.user;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.user.QMSUserInDeptDTO;
import com.medical.dtms.feignservice.user.QMSUserInDeptService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： QMSUserInDeptController.java v 1.0, 2019年08月20日 15:24  Exp$
 * @Description 用户 - 部门 关联 控制类
 **/
@RestController
public class QMSUserInDeptController {

    @Autowired
    private QMSUserInDeptService deptService;

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 新增 用户 - 部门关联
     **/
    @RequestMapping(value = "/userInDept/addQMSUserInDept", method = RequestMethod.POST)
    public Result<Boolean> addQMSUserInDept(@RequestBody QMSUserInDeptDTO dto) {
        if (null == dto || null == dto.getUserId() || CollectionUtils.isEmpty(dto.getDeptIdList())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());
        deptService.addQMSUserInDept(dto);
        return Result.buildSuccess(true);
    }
}
