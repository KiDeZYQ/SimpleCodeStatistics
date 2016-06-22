/**
 * @fileName   Parsable.java
 * @createDate 2016-6-4
 * @createTime 上午7:34:15
 */
package com.rampage.codestatistics.rule.intf;

/**
 * @description 解析接口
 * 
 * @type		Parsable
 * @author      KiDe
 * @version		V1.0.0
 */
public interface Parsable
{
    /**
     * 
     * @function 校验传入的行是否是注释行
     * @param str 待处理的字符串
     * @return true,是注释行;false,不是注释行.
     */
    boolean isComment(String str);
    
    /**
     * 
     * @function 判断传入的行是否是空行
     * @param str 待处理的字符串
     * @return true,是空行;false,不是空行.
     */
    boolean isEmpty(String str);
    
    /**
     * 
     * @function 判断传入的行是否还是有效代码行
     * @param str 待处理的字符串
     * @return true,是有效代码行;false,不是有效代码行.
     */
    boolean isEffective(String str);
    
    /**
     * 
     * @function 当为有效代码行的时候，里面是否包含注释
     * @param str 待处理的字符串
     * @return true,里面包含注释;false,里面不包含注释.
     */
    boolean containsComment(String str);
}
