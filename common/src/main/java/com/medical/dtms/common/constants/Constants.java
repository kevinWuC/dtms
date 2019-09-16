package com.medical.dtms.common.constants;

/**
 * @version： NConstants.java v 1.0, 2019年08月22日 16:24 wuxuelin Exp$
 * @Description 常量类
 **/
public class Constants {

    /**
     * 父级 id
     */
    public static final Long PARENT_ID = 0L;
    /**
     * 列默认值是否为空  - 非空
     */
    public static final Integer columnNoNulls = 0;
    /**
     * 列默认值是否为空  - 空
     */
    public static final Integer columnNullable = 1;
    /**
     * 列默认值是否为空  - 未知
     */
    public static final Integer columnNullableUnknown = 2;
    /**
     * 执行类型 - 1 查询列表
     */
    public static final Integer operateType_select = 1;
    /**
     * 执行类型 - 2 更新与删除
     */
    public static final Integer operateType_update_or_delete = 2;
    /**
     * 无权限
     */
    public static final String NO_PERMISSION = "/noPermission";
    /**
     * 登录页
     */
    public static final String LOGIN = "/login.html";
    /**
     * 新增当前提交人的日志表记录 - 意见 栏
     */
    public static final String USER_SUBMIT = "用户提交";
    /**
     * 新增转交人的日志表记录 - 意见 栏
     */
    public static final String USER_TRANSFER = "用户转交";
    /**
     * 日志表 意见
     */
    public static final String REMARK = "其他人已退回,系统自动关闭";
    /**
     * 文件格式
     */
    public static final String[] FILE_FORMATS = new String[]{
            ".docx", ".xlsx", ".pptx", ".doc", ".xls", ".ppt", ".jpg", ".png"
    };
    /**
     * 委托
     */
    public static final String MANDATORY = "委托给receiveUserName处理";
    /**
     * 文件申请状态 0 锁定 1 不锁定
     */
    public static final Integer LOCK = 0;
    public static final Integer NO_LOCK = 1;
    /**
     * 1 用户 2 部门
     */
    public static final Integer USER = 1;
    /**
     * 1 用户 2 部门
     */
    public static final Integer DEPT = 2;
    /**
     * 是
     */
    public static final String YES = "是";
    /**
     * 否
     */
    public static final String NO = "否";
    /**
     * 年审结论
     */
    public static final String PASS = "年审通过";
    public static final String FAIL = "年审未通过,需要修改文件";
}
