package com.hung.util;

/**
 * Sql防止注入检验工具类
 *
 * @author Hung
 */
public class SqlFilter {

    /**
     * 检查sql
     *
     * @param str sql字符串
     * @return true代表有违规字符，false代表没有违规字符
     */
    public static boolean sqlCheck(String str) {
        String limit = "'|and|exec|insert|select|delete|update| count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
        String[] split = limit.split("\\|");
        for (int i = 0; i < split.length; i++) {
            if (str.contains(split[i])) {
                return true;
            }
        }
        return false;
    }
}
