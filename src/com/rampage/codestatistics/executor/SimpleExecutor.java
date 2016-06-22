/**
 * @fileName SimpleExecutor.java
 * @createDate 2016-6-4
 * @createTime 上午11:00:02
 */
package com.rampage.codestatistics.executor;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.rampage.codestatistics.context.EngineRuleContext;
import com.rampage.codestatistics.context.SystemConfigContext;
import com.rampage.codestatistics.util.CollectionUtils;

/**
 * @description 简单的执行器
 * 
 * @type SimpleExecutor
 * @author KiDe
 * @version V1.0.0
 */
public class SimpleExecutor
{
    private static final Logger LOGGER = Logger.getLogger(SimpleExecutor.class);
    
    private final File file;

    public SimpleExecutor(File file)
    {
        this.file = file;
    }

    public void execute()
    {
        long start = System.nanoTime();
        System.out.println("--------------------Counting source code type:" + EngineRuleContext.getInstance().getSourceCodeTypeName() + " start--------------------\n");

        ExecutorService executorService = Executors.newFixedThreadPool(SystemConfigContext.getInstance().getnThreads());
        // System.out.println("Threads number is:" + context.getSysConfig().getnThreads());
        TaskDistributor distributor = new TaskDistributor(new FileWrapper(file));
        distributor.distribute();
        
        int totalCount = 0;
        int emptyCount = 0;
        int effectiveCount = 0;
        int commentCount = 0;
        try
        {
            // 设置最高等待处理时间
            List<Future<List<CountResult>>> futures = executorService.invokeAll(distributor.getTasks(), SystemConfigContext.getInstance().getTaskWaitSeconds(), TimeUnit.SECONDS);
            if (CollectionUtils.isEmpty(futures))
            {
                System.out.println("No source file found!");
                return;
            }

            // 得到打印结果并且输出到控制太
            List<CountResult> countResults = null;
            for (Future<List<CountResult>> future : futures)
            {
                countResults = future.get();
                if (CollectionUtils.isEmpty(countResults))
                {
                    continue;
                }
                
                for (CountResult countResult : countResults)
                {
                    totalCount += countResult.getTotalCount();
                    emptyCount += countResult.getEmptyCount();
                    effectiveCount += countResult.getEffectiveCount();
                    commentCount += countResult.getCommentCount();
                    printCountResult(countResult);
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Task execute errors!The detail error messages please see file logs/tool.log!");
            LOGGER.error("Task execute errors!", e);
        }
        finally
        {
            executorService.shutdown();
            System.out.println("\nTotal cost time:" + ((System.nanoTime() - start) / 1000000) + "ms. All statistic information is:["  +  "total:" + totalCount + ", empty:" + emptyCount + ", effective:" + effectiveCount + ", comment:" + commentCount + "]");
            System.out.println("--------------------Counting source code type:" + EngineRuleContext.getInstance().getSourceCodeTypeName() + " end--------------------\n");
        }
    }

    /**
     * @function 输出统计结果
     * 
     * @param countResult
     *            统计结果对象
     */
    private void printCountResult(CountResult countResult)
    {
        StringBuilder sb = new StringBuilder(256);
        sb.append("file:").append(countResult.getFileName());
        sb.append(" total:").append(countResult.getTotalCount());
        sb.append(" empty:").append(countResult.getEmptyCount());
        sb.append(" effective:").append(countResult.getEffectiveCount());
        sb.append(" comment:").append(countResult.getCommentCount());

        System.out.println(sb.toString());
    }
}
