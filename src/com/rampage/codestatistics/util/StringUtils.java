/**
 * @fileName   StringUtils.java
 * @createDate 2016-6-3
 * @createTime 下午7:00:44
 */
package com.rampage.codestatistics.util;

import java.math.BigDecimal;

/**
 * @description 字符串处理工具类
 * 
 * @type		StringUtils
 * @author      KiDe
 * @version		V1.0.0
 */
public class StringUtils
{
    
    private StringUtils()
    {
        
    }
    
    /**
     * 空字符串
     */
    public static final String EMPTY = "";
    
    /**
     * 
     * @function 判断字符串是否是empty（为null或则是空字符串）
     *
     * @param str 传入的带判断的字符串
     * @return true,传入的字符串是empty;false,传入的字符串是非empty.
     */
    public static boolean isEmpty(String str)
    {
        return str == null || EMPTY.equals(str);
    }
    
    /**
     * 
     * @function 判断是否是blank，执行trim操作之后判断
     *
     * @param str 传入的字符串
     * @return true,是blank;false,不是blank.
     */
    public static boolean isBlank(String str)
    {
        return str == null || EMPTY.equals(str.trim());
    }

    /**
     * @function 执行trim操作（考虑null的情况）
     *
     * @param  str 传入的字符串行
     * @return trim操作之后的结果
     */
    public static String trim(String str)
    {
        return str == null ? null : str.trim();
    }
    
    /**
     * 
     * @function 将字符串转换成BigDecimal对象
     *
     * @param str   待转化的字符串
     * @return      转换后的BigDecimal对象
     */
    public static BigDecimal string2Decimal(String str)
    {
        if (isBlank(str))
        {
            return null;
        }
        
        return new BigDecimal(str.trim());
    }
}
