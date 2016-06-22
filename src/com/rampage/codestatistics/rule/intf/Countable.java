/**
 * @fileName   Countable.java
 * @createDate 2016-6-4
 * @createTime 上午9:22:11
 */
package com.rampage.codestatistics.rule.intf;

/**
 * @description 统计接口
 * 
 * @type		Countable
 * @author      KiDe
 * @version		V1.0.0
 */
public interface Countable
{
    /**
     * 
     * @function 得到总的物理行数
     *
     * @return 总的物理行数
     */
    int getTotalCount();
    
    /**
     * 
     * @function 得到注释行数
     *
     * @return  注释行数
     */
    int getCommentCount();
    
    /**
     * 
     * @function 得到空行数
     *
     * @return 空行数
     */
    int getEmptyCount();
    
    /**
     * 
     * @function 得到有效代码行数
     *
     * @return 有效代码行数
     */
    int getEffectiveCount();
    
    /**
     * 
     * @function 统计传入的字符串中各种类型的行数
     *
     * @param lineStr 传入的待统计的代码
     */
    void count(String lineStr);
}
