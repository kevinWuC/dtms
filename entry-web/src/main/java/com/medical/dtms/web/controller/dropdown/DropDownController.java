package com.medical.dtms.web.controller.dropdown;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.dropdown.DropDownDetailsModel;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.feignservice.dropdown.DropDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @version： DropDownController.java v 1.0, 2019年09月04日 11:06 wuxuelin Exp$
 * @Description 下拉控制类
 **/
@RestController
public class DropDownController {

    @Autowired
    private DropDownService dropDownService;

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownDetailsModel>>
     * @description 字典明细二级下拉框
     **/
    @RequestMapping(value = "/dropdown/listTypeDetails")
    public Result<PageInfo<DropDownDetailsModel>> listTypeDetailsName(@RequestBody DropDownQuery query){
        if(null == query.getItemsId()){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(),"字典类别的业务主键为空");
        }
        checkParams(query);
        PageInfo<DropDownDetailsModel> models = dropDownService.listTypeDetailsName(query);
        return Result.buildSuccess(models);
    }


    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 文件类别下拉框
     **/
    @RequestMapping(value = "/dropdown/listFileTypes")
    public Result<PageInfo<DropDownModel>> listFileTypesInFileModel(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listFileTypes(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 申请类别下拉框
     **/
    @RequestMapping(value = "/dropdown/listApplyTypesInFileModel")
    public Result<PageInfo<DropDownModel>> listApplyTypesInFileModel(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listApplyTypesInFileModel(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 接收部门下拉框
     **/
    @RequestMapping(value = "/dropdown/listReceiveDept")
    public Result<PageInfo<DropDownModel>> listReceiveDeptInFileModel(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listReceiveDept(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List <com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 编写部门下拉框
     **/
    @RequestMapping(value = "/dropdown/listApplyDeptInFileModel")
    public Result<PageInfo<DropDownModel>> listApplyDeptInFileModel(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listApplyDeptInFileModel(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 人员列表
     **/
    @RequestMapping(value = "/dropdown/listUsers")
    public Result<PageInfo<DropDownModel>> listUsersInFileModel(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listUsers(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 发布年度下拉框
     **/
    @RequestMapping(value = "/dropdown/listYears")
    public Result<PageInfo<DropDownModel>> listYearsInFileModel(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> pageInfo = dropDownService.listYears(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 分配角色下拉框
     **/
    @RequestMapping(value = "/dropdown/listRolesInFileModel")
    public Result<List<DropDownModel>> listRolesInFileModel(@RequestBody DropDownQuery query) {
//        checkParams(query);
        List<DropDownModel> pageInfo = dropDownService.listRolesInFileModel(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 所有文件类别下拉框
     **/
    @RequestMapping(value = "/dropdown/listAllFileTypes")
    public Result<PageInfo<DropDownModel>> listAllFileTypes(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listAllFileTypes(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownModel>>
     * @description 所有申请原因下拉框
     **/
    @RequestMapping(value = "/dropdown/listAllApplyReasons")
    public Result<PageInfo<DropDownModel>> listAllApplyReasons(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listAllApplyReasons(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param []
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.common.model.dropdown.DropDownModel>>
         * @description 所有申请类别下拉框
     **/
    @RequestMapping(value = "/dropdown/listAllApplyTypes")
    public Result<PageInfo<DropDownModel>> listAllApplyTypes(@RequestBody DropDownQuery query) {
        checkParams(query);
        PageInfo<DropDownModel> models = dropDownService.listAllApplyTypes(query);
        return Result.buildSuccess(models);
    }



    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(DropDownQuery query) {
        if (null == query) {
            query = new DropDownQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(50);
        }
    }
}
