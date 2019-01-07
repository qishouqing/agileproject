package com.qishouqing.frame.utils;

import java.util.regex.Pattern;

/***
 * 包名： cn.com.dareway.nurse.utils
 * 类名： CheckUtil
 * 时间： 2018/8/20 8:34
 * 作者： ljm
 * 功能简述：校验，姓名，身份证，手机号
 * 修改历史：
 */
public class CheckUtil {
    /**
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";

    /**
     * 正则表达式:验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    /**
     * 校验姓名,只能输入中文和点
     *
     * @param name
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChineseName(String name) {
        if (name.contains("·") || name.contains("•")) {
            if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")) {
                return true;
            } else {
                return false;
            }
        } else {
            if (name.matches("^[\\u4e00-\\u9fa5]+$")) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 校验姓名
     *
     * @param name
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChineseOrEngName(String name) {
        if (name.contains("·") || name.contains("•")) {
            if (name.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")) {
                return true;
            } else if (name.matches("^[A-Za-z]+[·•]+[A-Za-z]+$")) {
                return true;
            } else {
                return false;
            }
        } else {
            if (name.matches("^[\\u4e00-\\u9fa5]+$") || name.matches("^[A-Za-z]+$")) {
                return true;
            } else {
                return false;
            }
        }
    }
}
