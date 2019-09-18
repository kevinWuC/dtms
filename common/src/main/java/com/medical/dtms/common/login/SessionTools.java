package com.medical.dtms.common.login;

import com.medical.dtms.common.model.user.UserLoginModel;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @version： SessionTools.java v 1.0, 2019年08月23日 12:05 wuxuelin Exp$
 * @Description session 工具类
 **/
public class SessionTools {

    /**
     * 保存用户信息到 session
     */
    public static void saveUserInfo(UserLoginModel model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (model.getKeepLogin() == false) {
            // 清除session
            session.removeAttribute(SessionConstants.SESSION_KEY);
        }
        // 放入用户信息
        OperatorInfo info = new OperatorInfo();
        BeanUtils.copyProperties(model, info);
        info.setUserId(String.valueOf(info.getBizId()));

        // 设置 session 信息及过期时间
        session.setAttribute(SessionConstants.SESSION_KEY, info);
        session.setAttribute(SessionConstants.SESSION_TIME_OUT, System.currentTimeMillis());
    }

    /**
     * 从session 从获取用户信息
     */
    public static OperatorInfo getOperator() {
        OperatorInfo operatorInfo = (OperatorInfo) ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest().getSession().getAttribute(SessionConstants.SESSION_KEY);
        if (null == operatorInfo) {
            return null;
        }
        return operatorInfo;
    }

    /**
     * 清除 session 中所有信息
     */
    public static void cleanUserInfo(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}
