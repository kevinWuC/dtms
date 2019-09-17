package com.medical.dtms.service.serviceimpl.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.syslog.BaseSimpleUserModel;
import com.medical.dtms.common.model.syslog.SimpleLogInLogModel;
import com.medical.dtms.common.model.syslog.SysLoginLogModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.dept.QMSDeptDTO;
import com.medical.dtms.dto.log.QMSSysLogsDTO;
import com.medical.dtms.dto.menu.query.QMSMenuQuery;
import com.medical.dtms.dto.role.QMSRoleDTO;
import com.medical.dtms.dto.role.query.QMSRoleQuery;
import com.medical.dtms.dto.user.QMSUserDTO;
import com.medical.dtms.dto.user.QMSUserInDeptDTO;
import com.medical.dtms.dto.user.QMSUserInJobsDTO;
import com.medical.dtms.dto.user.QMSUserInRoleDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.dto.user.query.QMSUserInDeptQuery;
import com.medical.dtms.feignservice.menu.QMSMenuService;
import com.medical.dtms.feignservice.user.QMSUserService;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.common.model.user.QMSUserInRoleModel;
import com.medical.dtms.common.model.user.QMSUserModel;
import com.medical.dtms.common.model.user.UserLoginModel;
import com.medical.dtms.service.manager.dept.QMSDeptManager;
import com.medical.dtms.service.manager.role.QMSRoleInMenuManager;
import com.medical.dtms.service.manager.role.QMSRoleManager;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.syslogs.SysLogsManager;
import com.medical.dtms.service.manager.user.QMSUserInDeptManager;
import com.medical.dtms.service.manager.user.QMSUserInJobsManager;
import com.medical.dtms.service.manager.user.QMSUserInRoleManager;
import com.medical.dtms.service.manager.user.QMSUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version： QMSUserServiceImpl.java v 1.0, 2019年08月20日 17:40 wuxuelin Exp$
 * @Description 用户管理
 **/
@RestController
@Slf4j
public class QMSUserServiceImpl implements QMSUserService {

    @Autowired
    private QMSUserManager userManager;
    @Autowired
    private QMSUserInRoleManager userInRoleManager;
    @Autowired
    private QMSUserInJobsManager userInJobsManager;
    @Autowired
    private QMSUserInDeptManager userInDeptManager;
    @Autowired
    private QMSRoleInMenuManager roleInMenuManager;
    @Autowired
    private SysLogsManager sysLogsManager;
    @Autowired
    private QMSMenuService menuService;
    @Autowired
    private QMSDeptManager deptManager;
    @Autowired
    private QMSRoleManager roleManager;
    @Autowired
    private SysLoginLogManager logManager;
    @Autowired
    private IdGenerator idGenerator;


    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 用户管理 - 新增用户
     **/
    @Override
    public Boolean addUser(@RequestBody QMSUserDTO dto) {
        // 根据账号做唯一性校验
        BaseUserQuery query = new BaseUserQuery();
        query.setAccount(dto.getAccount());

        QMSUserDTO user = userManager.getUserByCondition(query);
        if (null != user) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "用户帐号重复，请重新填写");
        }
        // 加密密码
        if (StringUtils.isNotBlank(dto.getPassWord())) {
            String newPassWord = encryptPassword(dto);
            dto.setPassWord(newPassWord);
        }

        try {
            dto.setBizId(idGenerator.nextId());
            userManager.insert(dto);
        } catch (Exception e) {
            log.error("新增用户失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 用户管理 - 编辑用户
     **/
    @Override
    public Boolean updateUser(@RequestBody QMSUserDTO dto) {
        BaseUserQuery query = new BaseUserQuery();
        query.setUserId(dto.getBizId());
        QMSUserDTO user = userManager.getUserByCondition(query);
        if (null == user) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该用户已被删除，无法操作");
        }

        // 重复性校验
        if (null != dto && StringUtils.isNotBlank(dto.getAccount())) {
            query.setUserId(null);
            query.setAccount(dto.getAccount());
            QMSUserDTO user1 = userManager.getUserByCondition(query);
            if (null != user1 && !user1.getBizId().equals(dto.getBizId())) {
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "用户帐号重复，请重新填写");
            }
        }

        // 重置密码
        if (null != dto && StringUtils.isNotBlank(dto.getPassWord())) {
            // 密码加密
            String newPassWord = encryptPassword(dto);
            dto.setPassWord(newPassWord);
        }

        try {
            userManager.updateUser(dto);
        } catch (Exception e) {
            log.error("编辑用户失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 用户管理 - 删除用户(逻辑删除)
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean deleteUser(@RequestBody QMSUserDTO dto) {
        BaseUserQuery query = new BaseUserQuery();
        query.setUserId(dto.getBizId());
        QMSUserDTO user = userManager.getUserByCondition(query);
        if (null == user) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该用户已被删除，无法操作");
        }

        //  删除与角色的关联
        BaseUserQuery qmsUserInRoleQuery = new BaseUserQuery();
        qmsUserInRoleQuery.setUserId(dto.getBizId());
        List<QMSUserInRoleDTO> list = userInRoleManager.listQMSUserInRole(qmsUserInRoleQuery);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                userInRoleManager.deleteQMSUserInRoleByUserId(dto.getBizId());
            } catch (Exception e) {
                log.error("删除用户时，删除角色关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        //  删除与岗位的关联
        BaseUserQuery jobsQuery = new BaseUserQuery();
        jobsQuery.setUserId(dto.getBizId());
        List<QMSUserInJobsDTO> jobsDTOS = userInJobsManager.listQMSUserInJob(jobsQuery);
        if (CollectionUtils.isNotEmpty(jobsDTOS)) {
            try {
                userInJobsManager.deleteQMSUserInJobByUserId(dto.getBizId());
            } catch (Exception e) {
                log.error("删除用户时，删除岗位关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        //  删除与部门的关联
        QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
        deptQuery.setUserId(dto.getBizId());
        List<QMSUserInDeptDTO> inDeptDTOS = userInDeptManager.listQMSUserInDept(deptQuery);
        if (CollectionUtils.isNotEmpty(inDeptDTOS)) {
            try {
                userInDeptManager.deleteByPrimaryKey(dto.getBizId());
            } catch (Exception e) {
                log.error("删除用户时，删除部门关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        try {
            dto.setIsDeleted(true);
            userManager.updateUser(dto);
        } catch (Exception e) {
            log.error("删除用户失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.user.QMSUserModel>
     * @description 用户管理 - 分页展示用户列表
     **/
    @Override
    public PageInfo<QMSUserModel> pageListUser(@RequestBody BaseUserQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<QMSUserModel> list = userManager.pageListUser(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }

        // 处理登录次数、最后登录时间、角色、职位、部门
        List<Long> userIds = list.stream().map(QMSUserModel::getBizId).distinct().collect(Collectors.toList());
        List<SimpleLogInLogModel> logModels = logManager.listUserLastVisitAndVisitTime(userIds);
        logModels.removeAll(Collections.singleton(null));

        List<BaseSimpleUserModel> roleIds = userInRoleManager.listRoleIdsByUserIds(userIds);
        List<BaseSimpleUserModel> deptIds = userInDeptManager.listDeptIdsByUserIds(userIds);
        List<BaseSimpleUserModel> jobIds = userInJobsManager.listJobIdsByUserIds(userIds);


        if (CollectionUtils.isNotEmpty(logModels)) {
            List<Long> users = logModels.stream().map(SimpleLogInLogModel::getUserId).distinct().collect(Collectors.toList());
            users.removeAll(Collections.singleton(null));
            List<SimpleLogInLogModel> collect = new ArrayList<>();
            for (QMSUserModel model : list) {
                if (CollectionUtils.isNotEmpty(users)) {
                    collect = logModels.stream().filter(simpleLogInLogModel -> simpleLogInLogModel.getUserId().equals(model.getBizId())).distinct().collect(Collectors.toList());
                }
                model.setLogOnCount(CollectionUtils.isEmpty(collect) == true ? 0 : collect.get(0).getLogOnCount() == null ? 0 : collect.get(0).getLogOnCount());
                model.setLastVisit(CollectionUtils.isEmpty(collect) == true ? null : collect.get(0).getLastVisit() == null ? null : collect.get(0).getLastVisit());
                model.setPreviousVisit(CollectionUtils.isEmpty(collect) == true ? null : collect.get(0).getLastVisit() == null ? null : collect.get(0).getLastVisit());

                if (CollectionUtils.isEmpty(roleIds)) {
                    model.setRoleIdListStr(null);
                } else {
                    model.setRoleIdListStr(CollectionUtils.isEmpty(roleIds.stream().filter(userModel -> model.getBizId().equals(userModel.getUserId())).collect(Collectors.toList())) == true ?
                            null : StringUtils.join(roleIds.stream().filter(userModel -> model.getBizId().equals(userModel.getUserId())).collect(Collectors.toList()).stream().map(BaseSimpleUserModel::getRoleId).distinct().collect(Collectors.toList()), ","));
                }

                if (CollectionUtils.isEmpty(deptIds)) {
                    model.setDeptIdListStr(null);
                } else {
                    model.setDeptIdListStr(CollectionUtils.isEmpty(deptIds.stream().filter(userModel -> model.getBizId().equals(userModel.getUserId())).collect(Collectors.toList())) == true ?
                            null : StringUtils.join(deptIds.stream().filter(userModel -> model.getBizId().equals(userModel.getUserId())).collect(Collectors.toList()).stream().map(BaseSimpleUserModel::getDeptId).distinct().collect(Collectors.toList()), ","));
                }

                if (CollectionUtils.isEmpty(jobIds)) {
                    model.setJobIdListStr(null);
                } else {
                    model.setJobIdListStr(CollectionUtils.isEmpty(jobIds.stream().filter(userModel -> model.getBizId().equals(userModel.getUserId())).collect(Collectors.toList())) == true ?
                            null : StringUtils.join(jobIds.stream().filter(userModel -> model.getBizId().equals(userModel.getUserId())).collect(Collectors.toList()).stream().map(BaseSimpleUserModel::getJobId).distinct().collect(Collectors.toList()), ","));
                }
            }
        }

        return new PageInfo<>(list);
    }

    /**
     * @param [roleDTO]
     * @return java.lang.Boolean
     * @description 用户管理 - 新增用户与角色关联
     **/
    @Override
    public Boolean addUserInRole(@RequestBody QMSUserInRoleDTO roleDTO) {
        // 查询用户关联的角色
        BaseUserQuery roleQuery = new BaseUserQuery();
        roleQuery.setUserId(roleDTO.getBizId());
        List<QMSUserInRoleDTO> list = userInRoleManager.listQMSUserInRole(roleQuery);
        if (CollectionUtils.isNotEmpty(list)) {
            try {
                userInRoleManager.deleteQMSUserInRoleByUserId(roleDTO.getBizId());
            } catch (Exception e) {
                log.error("删除用户时，删除角色关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        // 新增关联
        List<QMSUserInRoleDTO> dtoList = new ArrayList<>();
        QMSUserInRoleDTO userInRoleDTO;
        for (Long roleId : roleDTO.getRoleIdList()) {
            userInRoleDTO = new QMSUserInRoleDTO();
            BeanUtils.copyProperties(roleDTO, userInRoleDTO);
            userInRoleDTO.setBizId(idGenerator.nextId());
            userInRoleDTO.setRoleId(roleId);
            dtoList.add(userInRoleDTO);
        }

        try {
            userInRoleManager.insert(dtoList);
        } catch (Exception e) {
            log.error("添加用户与角色关联关系失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [jobsDTO]
     * @return java.lang.Boolean
     * @description 用户管理 - 新增用户与职位关联
     **/
    @Override
    public Boolean addUserInJob(@RequestBody QMSUserInJobsDTO jobsDTO) {
        //  查询与岗位的关联
        BaseUserQuery jobsQuery = new BaseUserQuery();
        jobsQuery.setUserId(jobsDTO.getBizId());
        List<QMSUserInJobsDTO> jobsDTOS = userInJobsManager.listQMSUserInJob(jobsQuery);
        if (CollectionUtils.isNotEmpty(jobsDTOS)) {
            try {
                userInJobsManager.deleteQMSUserInJobByUserId(jobsDTO.getBizId());
            } catch (Exception e) {
                log.error("删除用户时，删除岗位关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        List<QMSUserInJobsDTO> dtos = new ArrayList<>();
        QMSUserInJobsDTO inJobsDTO;
        for (Long jobId : jobsDTO.getJobIdList()) {
            inJobsDTO = new QMSUserInJobsDTO();
            BeanUtils.copyProperties(jobsDTO, inJobsDTO);
            inJobsDTO.setBizId(idGenerator.nextId());
            inJobsDTO.setJobId(jobId);
            dtos.add(inJobsDTO);
        }

        try {
            userInJobsManager.insert(dtos);
        } catch (Exception e) {
            log.error("添加用户与职位关联关系失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [deptDTO]
     * @return java.lang.Boolean
     * @description 用户管理 - 新增用户与部门关联
     **/
    @Override
    public Boolean addUserInDept(@RequestBody QMSUserInDeptDTO deptDTO) {
        //  查询与部门的关联
        QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
        deptQuery.setUserId(deptDTO.getBizId());
        List<QMSUserInDeptDTO> inDeptDTOS = userInDeptManager.listQMSUserInDept(deptQuery);
        if (CollectionUtils.isNotEmpty(inDeptDTOS)) {
            try {
                userInDeptManager.deleteByPrimaryKey(deptDTO.getBizId());
            } catch (Exception e) {
                log.error("删除用户时，删除部门关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }

        List<QMSUserInDeptDTO> dtos = new ArrayList<>();
        QMSUserInDeptDTO inDeptDTO;
        for (Long deptId : deptDTO.getDeptIdList()) {
            inDeptDTO = new QMSUserInDeptDTO();
            BeanUtils.copyProperties(deptDTO, inDeptDTO);
            inDeptDTO.setBizId(idGenerator.nextId());
            inDeptDTO.setDeptId(deptId);
            dtos.add(inDeptDTO);
        }

        try {
            userInDeptManager.insert(dtos);
        } catch (Exception e) {
            log.error("添加用户与部门关联关系失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.model.user.UserLoginModel
     * @description 用户登录
     **/
    @Override
    public UserLoginModel login(@RequestBody QMSUserDTO dto) {
        // 校验用户名是否存在
        BaseUserQuery query = new BaseUserQuery();
        query.setAccount(dto.getAccount());
        QMSUserDTO user = userManager.getUserByCondition(query);
        if (null == user) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "账号不存在，请重新输入！");
        }

        String newPassWord = encryptPassword(dto);
        dto.setPassWord(newPassWord);
        // 校验密码
        if (!StringUtils.equals(dto.getPassWord(), user.getPassWord())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "用户名密码不匹配，请重新输入！");
        }

        // 查询默认部门名称
        QMSDeptDTO deptDTO = deptManager.selectByPrimaryKey(user.getDeptId());

        UserLoginModel model = new UserLoginModel();
        BeanUtils.copyProperties(user, model);
        model.setDeptName(deptDTO == null ? null : deptDTO.getDeptName() == null ? null : deptDTO.getDeptName());

        // 查询用户所拥有的角色
        List<QMSUserInRoleModel> roleInfo = userInRoleManager.listRoleInfoByUserId(user.getBizId());

        // 查询用户默认角色
        QMSRoleQuery roleQuery = new QMSRoleQuery();
        roleQuery.setBizId(user.getRoleId());
        QMSRoleDTO role = roleManager.getRoleByCondition(roleQuery);
        QMSUserInRoleModel roleModel = BeanConvertUtils.convert(role, QMSUserInRoleModel.class);
        roleModel.setRoleId(role.getBizId());

        roleInfo.add(roleModel);

        model.setRoleList(roleInfo);

        return model;
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.model.menu.QMSMenuModel>
     * @description 查询用户所拥有的菜单
     **/
    @Override
    public List<QMSMenuModel> listMenusByUserId(@RequestBody BaseUserQuery query) {
        // 查询用户所拥有的角色
        List<QMSUserInRoleModel> roleInfo = userInRoleManager.listRoleInfoByUserId(query.getBizId());
        if (CollectionUtils.isEmpty(roleInfo)) {
            log.error("该用户未绑定角色");
            return new ArrayList<>();
        }

        // 查询菜单信息
        List<Long> roleIds = roleInfo.stream().map(QMSUserInRoleModel::getRoleId).distinct().collect(Collectors.toList());
        List<QMSMenuModel> newMenuList = new ArrayList<>();
        List<QMSMenuModel> menuModels = roleInMenuManager.listMenuByRole(roleIds);
        if (CollectionUtils.isNotEmpty(menuModels)) {
            for (QMSMenuModel model : menuModels) {
                QMSMenuQuery menuQuery = new QMSMenuQuery();
                menuQuery.setParentId(model.getBizId());
                List<QMSMenuModel> models = menuService.listQMSMenus(menuQuery);
                if (CollectionUtils.isEmpty(models)) {
                    continue;
                }
                newMenuList.addAll(models);
            }
        }
        return newMenuList;
    }


    /**
     * 密码加密
     */
    private String encryptPassword(QMSUserDTO dto) {
        String newPassWord;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder encoder = new BASE64Encoder();
            newPassWord = encoder.encode(md5.digest(dto.getPassWord().getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            log.info("加密算法不合法", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        } catch (UnsupportedEncodingException e) {
            log.info("不支持的字符集", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return newPassWord;
    }

}
