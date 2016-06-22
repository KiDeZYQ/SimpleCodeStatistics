/**
 * @fileName   CollectionUtils.java
 * @createDate 2016-6-3
 * @createTime 下午6:45:51
 */
package com.rampage.codestatistics.util;

import java.util.Collection;

/**
 * @description 集合工具类
 * 
 * @type		CollectionUtils
 * @author      KiDe
 * @version		V1.0.0
 */
public class CollectionUtils
{
    
    private CollectionUtils()
    {
        
    }
    
    /**
     * 
     * @function 判断集合是否为空
     *
     * @param collections 传入的集合参数
     * @return true.为空;false,不为空.
     */
    public static<T> boolean isEmpty(Collection<? extends T> collections)
    {
        return collections == null || collections.isEmpty();
    }
    
    
}
