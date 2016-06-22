/**
 * @fileName   TaskDistributor.java
 * @createDate 2016-6-4
 * @createTime 上午10:36:12
 */
package com.rampage.codestatistics.executor;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;


import com.rampage.codestatistics.context.EngineRuleContext;
import com.rampage.codestatistics.context.SystemConfigContext;
import com.rampage.codestatistics.util.CollectionUtils;
import com.rampage.codestatistics.util.StringUtils;

/**
 * @description 任务分发器类
 * 
 * @type		TaskDistributor
 * @author      KiDe
 * @version		V1.0.0
 */
public class TaskDistributor
{
    /**
     * 传入的路径
     */
    private final FileWrapper file;
    
    /**
     * 用来存储当前总共的文件大小，一旦超过固定大小就分发给任务来处理
     */
    private long fileLength = 0;
    
    /**
     * 单个任务最大处理的文件数量
     */
    private final int maxFileSize;
    
    /**
     * 单个任务最大处理的文件大小（按文件来进行累计，如果累加某个文件A超过该大小，该文件A还是要放入任务中处理）
     */
    private final long maxFileLength;
    
    /**
     * 文件后缀名列表
     */
    private final List<String> fileNameSuffixes;
    
    /**
     * 任务列表
     */
    private List<CounterTask> tasks = new LinkedList<CounterTask>();
    
    private static final Logger LOGGER = Logger.getLogger(TaskDistributor.class);
    
    public TaskDistributor(FileWrapper file)
    {
        this.file = file;
        this.maxFileSize = SystemConfigContext.getInstance().getMaxFileNums();
        this.maxFileLength = SystemConfigContext.getInstance().getMaxFileLength();
        this.fileNameSuffixes = EngineRuleContext.getInstance().getFileNameSuffixes();
    }
    
    /**
     * 分发处理
     * @function 
     *
     */
    public void distribute()
    {
        // 没有配置文件后缀名，直接记录警告返回
        if (CollectionUtils.isEmpty(fileNameSuffixes))
        {
            LOGGER.warn("No file name suffix configured for the source code, there would be not file found!");
            return;
        }
        
        List<FileWrapper> taskFiles = new LinkedList<FileWrapper>();
        distributeFile2Task(taskFiles, file);
        if (!taskFiles.isEmpty())
        {
            this.tasks.add(new CounterTask(new LinkedList<FileWrapper>(taskFiles)));
            taskFiles.clear();
            this.fileLength = 0;
        }
        
        // System.out.println("Task number is:" + this.tasks.size());
    }

    /**
     * @function 分发文件列表给对应的
     *
     * @param taskFiles
     * @param file2
     */
    private void distributeFile2Task(List<FileWrapper> taskFiles, FileWrapper curFileAdapter)
    {
        File curFile = curFileAdapter.getFile();
        if (curFile.isFile())
        {
            if (isSourceFile(curFile.getName()))
            {
                taskFiles.add(curFileAdapter);
                fileLength += curFile.length();
                if (taskFiles.size() >= maxFileSize || fileLength >= maxFileLength)
                {
                    // 新建任务并且清空记录状态
                    tasks.add(new CounterTask(new LinkedList<FileWrapper>(taskFiles)));
                    taskFiles.clear();
                    fileLength = 0;
                }
            }
            
            return;
        }
        
        // 递归处理子文件
        File[] files = curFile.listFiles();
        if (files == null || files.length == 0)
        {
            return;
        }
        String curRelativePath = curFileAdapter.getRelativePath();
        for (File file : files)
        {
            FileWrapper adapter = new FileWrapper(file);
            if (!StringUtils.isBlank(curRelativePath))
            {
                adapter.setRelativePath(curRelativePath + File.separator + file.getName());
            }
            else
            {
                adapter.setRelativePath(file.getName());
            }
            distributeFile2Task(taskFiles, adapter);
        }
    }

    /**
     * @function 判断是否是需要检测的源文件
     *
     * @param name  文件名
     * @return true,是需要检测的源文件;false,不是需要检测的源文件.
     */
    private boolean isSourceFile(String name)
    {
        if (CollectionUtils.isEmpty(fileNameSuffixes))
        {
            return false;
        }
        
        for (String suffix : fileNameSuffixes)
        {
            if (EngineRuleContext.getInstance().isIgnoreCase())
            {
                if (name.toUpperCase().endsWith(suffix.toUpperCase()))
                {
                    return true;
                }
                
            }
            else
            {
                if (name.endsWith(suffix))
                {
                    return true;
                }
            }
        }
        
        return false;
    }

    public List<CounterTask> getTasks()
    {
        return tasks;
    }
}
