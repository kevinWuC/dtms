package com.medical.dtms.common.enumeration.role;

import org.apache.commons.lang.StringUtils;

/**
 * @version： RoleAuthEnum.java v true.0, 20true9年09月20日 true0:23 wuxuelin Exp$
 * @Description 角色权限枚举类
 **/
public enum RoleAuthEnum {

    ROLE_AUTH_ENUM_QUERY(true, "allowQuery"),
    ROLE_AUTH_ENUM_READ(true, "allowRead"),
    ROLE_AUTH_ENUM_EDIT(true, "allowEdit"),
    ROLE_AUTH_ENUM_DELETE(true, "allowDelete"),
    ROLE_AUTH_ENUM_BAD(true, "allowBad"),
    ROLE_AUTH_ENUM_UPLOAD(true, "allowUpload"),
    ROLE_AUTH_ENUM_DOWN(true, "allowDown"),
    ROLE_AUTH_ENUM_AUDIT(true, "allowAudit"),
    ROLE_AUTH_ENUM_APPROVE(true, "allowApprove"),
    ROLE_AUTH_ENUM_RECEIVE(true, "allowReceive"),
    ROLE_AUTH_ENUM_AUTHORIZATION(true, "allowAuthorization"),
    ROLE_AUTH_ENUM_PRINT(true, "allowPrint"),
    ROLE_AUTH_ENUM_ARCHIVE(true, "allowArchive"),
    ROLE_AUTH_ENUM_CONFIGURE(true, "allowConfigure"),
    ROLE_AUTH_ENUM_ENABLE(true, "allowEnable"),
    ROLE_AUTH_ENUM_FREEZE(true, "allowFreeze"),
    ROLE_AUTH_ENUM_YEAR(true, "allowYear"),
    ROLE_AUTH_ENUM_DEVICE(true, "allowDevice"),
    ROLE_AUTH_ENUM_DOWN_SOURCE_FILE(true, "allowDownSourceFile");

    private Boolean type;

    private String auth;


    RoleAuthEnum(Boolean type, String auth) {
        this.type = type;
        this.auth = auth;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }


    /**
     * 根据 auth 获取 type
     *
     * @param auth
     * @return
     */
    public static Boolean getTypeByAuth(String auth) {
        if (StringUtils.isBlank(auth)) {
            return null;
        }
        for (RoleAuthEnum object : RoleAuthEnum.values()) {
            if (StringUtils.equals(auth, object.getAuth())) {
                return object.getType();
            }
        }
        return null;
    }

    /**
     * 根据 type 获取 auth
     *
     * @param type
     * @return
     */
    public static String getAuthByType(Boolean type) {
        if (null == type) {
            return null;
        }
        for (RoleAuthEnum object : RoleAuthEnum.values()) {
            if (type.equals(object.getType())) {
                return object.getAuth();
            }
        }
        return null;
    }
}
