/**
 * @fileName   RuleEngine.java
 * @createDate 2016-6-3
 * @createTime 下午7:33:14
 */
package com.rampage.codestatistics.rule.intf;

import java.io.File;


/**
 * @description 规则引擎接口
 * 
 * @type		RuleEngine
 * @author      KiDe
 * @version		V1.0.0
 */
public interface RuleEngine
{
    /**
     * 
     * @function 规则引擎处理文件，并且返回一个可统计的接口
     *
     * @param file 待处理的文件
     * @return 可统计的接口
     */
    Countable processFile(File file) throws Exception;
}
