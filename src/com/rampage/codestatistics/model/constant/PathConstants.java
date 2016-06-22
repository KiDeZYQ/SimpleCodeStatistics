/**
 * @fileName   PathConstants.java
 * @createDate 2016-6-4
 * @createTime 下午3:45:57
 */
package com.rampage.codestatistics.model.constant;

import java.io.File;

/**
 * @description 路径常量定义
 * 
 * @type		PathConstants
 * @author      KiDe
 * @version		V1.0.0
 */
public class PathConstants
{
    /**
     * 属性配置文件路径
     */
    public static final String PROP_FILE_PATH = "data" + File.separator + "SystemConfig.properties";
    
    
    /**
     * 规则配置文件路径
     */
    public static final String RULE_FILE_PATH = "data" + File.separator + "CountRule.xml";
}
