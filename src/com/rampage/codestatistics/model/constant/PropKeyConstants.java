/**
 * @fileName   PropKeyConstants.java
 * @createDate 2016-6-4
 * @createTime 下午4:16:59
 */
package com.rampage.codestatistics.model.constant;

/**
 * @description 属性key值常量
 * 
 * @type		PropKeyConstants
 * @author      KiDe
 * @version		V1.0.0
 */
public class PropKeyConstants
{
    /**
     * 线程池初始化线程个数
     */
    public static final String NTHREADS = "fixed_thread_count";
    
    /**
     * 每个任务处理的最大文件数量
     */
    public static final String MAX_FILE_NUMS = "max_file_size";
    
    /**
     * 每个任务处理的最大文件长度大小
     */
    public static final String MAX_FILE_LENGTH = "max_file_length";
    
    /**
     * 每个任务的最长等待处理时间
     */
    public static final String WAIT_SECONDS = "wait_seconds";
}
