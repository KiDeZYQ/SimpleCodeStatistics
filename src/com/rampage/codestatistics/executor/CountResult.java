/**
 * @fileName   CountResult.java
 * @createDate 2016-6-3
 * @createTime 下午6:38:35
 */
package com.rampage.codestatistics.executor;

/**
 * @description 定义单个文件的统计结果类
 * 
 * @type		CountResult
 * @author      KiDe
 * @version		V1.0.0
 */
public class CountResult
{
    /**
     * 统计的文件名
     */
    private String fileName;
    
    /**
     * 注释行数
     */
    private int commentCount;
    
    /**
     * 有效代码行数
     */
    private int effectiveCount;
    
    /**
     * 空行数
     */
    private int emptyCount;
    
    /**
     * 总行数
     */
    private int totalCount;

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public int getCommentCount()
    {
        return commentCount;
    }

    public void setCommentCount(int commentCount)
    {
        this.commentCount = commentCount;
    }

    public int getEffectiveCount()
    {
        return effectiveCount;
    }

    public void setEffectiveCount(int effectiveCount)
    {
        this.effectiveCount = effectiveCount;
    }

    public int getEmptyCount()
    {
        return emptyCount;
    }

    public void setEmptyCount(int emptyCount)
    {
        this.emptyCount = emptyCount;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
}
