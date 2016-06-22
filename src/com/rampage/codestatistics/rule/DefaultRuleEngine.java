/**
 * @fileName   DefaultRuleEngine.java
 * @createDate 2016-6-4
 * @createTime 上午8:51:58
 */
package com.rampage.codestatistics.rule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;

import com.rampage.codestatistics.rule.intf.Countable;
import com.rampage.codestatistics.rule.intf.RuleEngine;
import com.rampage.codestatistics.util.IOUtils;
import com.rampage.codestatistics.util.StringUtils;

/**
 * @description 默认的规则引擎
 * 
 * @type		DefaultRuleEngine
 * @author      KiDe
 * @version		V1.0.0
 */
public class DefaultRuleEngine implements RuleEngine
{
    /**
     * 日志记录器
     */
    private static final Logger LOGGER = Logger.getLogger(DefaultRuleEngine.class);
    
    /**
     * 
     * @function 预处理要统计的代码行
     *
     * @param lineStr 传入的代码行字符串
     * @return 预处理之后的代码行字符串
     */
    private String preProcessLine(String lineStr)
    {
        if (StringUtils.isBlank(lineStr))
        {
            return StringUtils.trim(lineStr);
        }
        
        lineStr = lineStr.trim();
        int len = lineStr.length();
        StringBuilder sb = new StringBuilder(len);
        
        int doubleQuotationCount = 0;       // 双引号的计数
        char ch = 0;
        
        // 循环遍历字符串，去掉双引号之间的字符串
        for (int i=0; i<len; i++)
        {
             ch = lineStr.charAt(i);
             
             // 当前符号是双引号且前一个符号不是转义字符
             if (ch == '"' && ((i==0) || (lineStr.charAt(i-1) != '\\')))   
             {
                 doubleQuotationCount++;
             }
             
             // 非双引号之间的字符放入缓冲字符串中
             if (doubleQuotationCount % 2 == 0 && ch != '"')
             {
                 sb.append(ch);
             }
        }
        
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.RuleEngine#processFile(java.io.File)
     */
    @Override
    public Countable processFile(File file)
    {
        BufferedReader br = null;
        
        Countable counter = new LineCounter();
        try
        {
            // 逐行进行处理
            br = new BufferedReader(new FileReader(file));
            String lineStr = preProcessLine(br.readLine());
            while (lineStr != null)
            {
                counter.count(lineStr);
                lineStr = preProcessLine(br.readLine());
            }
        }
        catch (Exception e)
        {
            System.out.println("Process File:" + file.getAbsolutePath() + " error!Detail error messages please see file logs/tool.log!");
            LOGGER.error("Process File:" + file.getAbsolutePath() + " error!", e);
        }
        finally
        {
            IOUtils.closeQuietly(br);
        }
        
        return counter;
    }
}
