/**
 * @fileName   SystemConfigContext.java
 * @createDate 2016-6-4
 * @createTime 下午3:21:57
 */
package com.rampage.codestatistics.context;

import java.io.File;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.rampage.codestatistics.model.constant.PathConstants;
import com.rampage.codestatistics.model.constant.PropKeyConstants;
import com.rampage.codestatistics.util.PropertyUtils;
import com.rampage.codestatistics.util.StringUtils;

/**
 * @description 系统配置上下文
 * 
 * @type		SystemConfigContext
 * @author      KiDe
 * @version		V1.0.0
 */
public class SystemConfigContext
{
    /**
     * 线程池中默认初始化的线程个数
     */
    private int nThreads = 2 * Runtime.getRuntime().availableProcessors() + 1;
    
    /**
     * 每个任务处理的最大文件数量
     */
    private int maxFileNums = Integer.MAX_VALUE;
    
    /**
     * 最大文件长度
     */
    private long maxFileLength = Long.MAX_VALUE;
    
    /**
     * 每个任务最大等待执行时间
     */
    private int taskWaitSeconds = 120;
    
    /**
     * 单个实例
     */
    private static volatile SystemConfigContext instance;
    
    /**
     * 锁对象
     */
    private static final Lock LOCK = new ReentrantLock();
    
    private static final Logger LOGGER = Logger.getLogger(SystemConfigContext.class);
    
    private SystemConfigContext()
    {
        
    }
    
    public static SystemConfigContext getInstance()
    {
        if (instance == null)
        {
            initSystemConfig();
        }
        
        return instance;
    }
    

    /**
     * @function 
     *
     */
    private static void initSystemConfig()
    {
        
        LOCK.lock();
        try
        {
            if (instance != null)
            {
                return;
            }
            
            instance = new SystemConfigContext();
            
            // 读取属性配置文件
            Map<String, String> properties = PropertyUtils.getProperties(new File(PathConstants.PROP_FILE_PATH));
            
            // 根据配置文件中的配置，组装系统配置对象中的信息
            if (properties == null)
            {
                LOGGER.warn("No properties configured in the system configuration file, Use default set instead!");
                return;
            }
            
            // 线程个数
            String value = properties.get(PropKeyConstants.NTHREADS);
            if (!StringUtils.isBlank(value))
            {
                int threadNumber = Integer.parseInt(value);
                if (threadNumber > 0)
                {
                    instance.nThreads = threadNumber;
                }
            }
            
            // 单任务最大文件个数
            value = properties.get(PropKeyConstants.MAX_FILE_NUMS);
            if (!StringUtils.isBlank(value))
            {
                int maxFileNums = Integer.parseInt(value);
                if (maxFileNums > 0)
                {
                    instance.maxFileNums = maxFileNums;
                }
            }
            
            // 单任务最大文件长度
            value = properties.get(PropKeyConstants.MAX_FILE_LENGTH);
            if (!StringUtils.isBlank(value))
            {
                long maxFileLength = Long.parseLong(value);
                if (maxFileLength > 0)
                {
                    instance.maxFileLength = maxFileLength;
                }
            }
            
            // 单任务最大任务执行等待时间
            value = properties.get(PropKeyConstants.WAIT_SECONDS);
            if (!StringUtils.isBlank(value))
            {
                int waitSeconds = Integer.parseInt(value);
                if (waitSeconds > 0)
                {
                    instance.taskWaitSeconds = waitSeconds;
                }
            }
           
        }
        catch (Exception e)
        {
            System.out.println("Init system configuration failed, Use default set instead!Detail fail information please see file logs/tool.log.");
            LOGGER.warn("Init system configuration failed, Use default set instead!", e);
        }
        finally
        {
            LOCK.unlock();
        }
    }

    public int getnThreads()
    {
        return nThreads;
    }


    public int getMaxFileNums()
    {
        return maxFileNums;
    }

    public long getMaxFileLength()
    {
        return maxFileLength;
    }

    public int getTaskWaitSeconds()
    {
        return taskWaitSeconds;
    }
}
