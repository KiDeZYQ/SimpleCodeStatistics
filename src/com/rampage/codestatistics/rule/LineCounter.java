/**
 * @fileName   LineCounter.java
 * @createDate 2016-6-4
 * @createTime 上午9:23:39
 */
package com.rampage.codestatistics.rule;

import com.rampage.codestatistics.rule.intf.Countable;
import com.rampage.codestatistics.rule.intf.Parsable;


/**
 * @description 按行来统计的统计接口实现类
 * 
 * @type		LineCounter
 * @author      KiDe
 * @version		V1.0.0
 */
public class LineCounter implements Countable
{
    /**
     * 总的行数
     */
    private int totalCount;
    
    /**
     * 空行数
     */
    private int emptyCount;
    
    /**
     * 注释行数
     */
    private int commentCount;
    
    /**
     * 有效代码行数
     */
    private int effectiveCount;
    
    /**
     * 解析器
     */
    private final Parsable parser = new LineParser();
    
    public void count(String lineStr)
    {
        this.totalCount++;
        
        // 判断顺序： 空行 ---》 注释行 ---》有效代码行 ---》 有效代码行和注释混合行
        if (parser.isEmpty(lineStr))
        {
            this.emptyCount++;
        }
        else if (parser.isComment(lineStr))
        {
            this.commentCount++;
        }
        else if (parser.isEffective(lineStr))
        {
            this.effectiveCount++;
            if (parser.containsComment(lineStr))
            {
                this.commentCount++;
            }
        }
    }
    
    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Countable#getTotalCount()
     */
    @Override
    public int getTotalCount()
    {
        return this.totalCount;
    }

    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Countable#getCommentCount()
     */
    @Override
    public int getCommentCount()
    {
        return this.commentCount;
    }

    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Countable#getEmptyCount()
     */
    @Override
    public int getEmptyCount()
    {
        return this.emptyCount;
    }

    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Countable#getEffectiveCount()
     */
    @Override
    public int getEffectiveCount()
    {
        return this.effectiveCount;
    }

}
