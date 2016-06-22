/**
 * @fileName   CounterTask.java
 * @createDate 2016-6-3
 * @createTime 下午6:34:43
 */
package com.rampage.codestatistics.executor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.rampage.codestatistics.context.EngineRuleContext;
import com.rampage.codestatistics.rule.intf.Countable;
import com.rampage.codestatistics.rule.intf.RuleEngine;
import com.rampage.codestatistics.util.CollectionUtils;

/**
 * @description 统计文件的任务类(实现Callable接口，返回CountResult的列表)
 * 
 * @type		CounterTask
 * @author      KiDe
 * @version		V1.0.0
 */
public class CounterTask implements Callable<List<CountResult>>
{

    /**
     * 要统计的文件列表
     */
    private final List<FileWrapper> files;
    
    public CounterTask(List<FileWrapper> files)
    {
        this.files = files;
    }
    
    /* (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public List<CountResult> call() throws Exception
    {
        if (CollectionUtils.isEmpty(files))
        {
            return Collections.emptyList();
        }
        List<CountResult> results = new LinkedList<CountResult>();
        RuleEngine ruleEngine = EngineRuleContext.getInstance().getEngine();
        Countable counter = null;
        for (FileWrapper file : files)
        {
            // 循环处理每一个文件，并将结果存储到结果列表中
            CountResult newResult = new CountResult();
            counter = ruleEngine.processFile(file.getFile());
            newResult.setCommentCount(counter.getCommentCount());
            newResult.setEffectiveCount(counter.getEffectiveCount());
            newResult.setTotalCount(counter.getTotalCount());
            newResult.setEmptyCount(counter.getEmptyCount());
            newResult.setFileName(file.getRelativePath());
            results.add(newResult);
        }
        
        return results;
    }
}
