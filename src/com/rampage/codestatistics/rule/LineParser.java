/**
 * @fileName   LineParser.java
 * @createDate 2016-6-4
 * @createTime 上午7:36:08
 */
package com.rampage.codestatistics.rule;

import java.util.List;

import com.rampage.codestatistics.context.EngineRuleContext;
import com.rampage.codestatistics.model.common.MultiLineComment;
import com.rampage.codestatistics.rule.intf.Parsable;
import com.rampage.codestatistics.util.CollectionUtils;
import com.rampage.codestatistics.util.StringUtils;

/**
 * @description 单行解析器
 * 
 * @type		LineParser
 * @author      KiDe
 * @version		V1.0.0
 */
public class LineParser implements Parsable
{
    /**
     * 当前是否进入多行注释的标识
     */
   private boolean innerMultiLineComment;

   /**
    * 单行注释标识
    */
   private final List<String> singleLineComments;
   
   /**
    * 多行注释列表
    */
   private final List<MultiLineComment> multiLineComments;
   
   /**
    * 多行注释的时候，当前匹配的多行注释对象
    */
   private MultiLineComment curMultiLineComment;
   
   public LineParser()
   {
       this.singleLineComments = EngineRuleContext.getInstance().getSingleLineComments();
       this.multiLineComments = EngineRuleContext.getInstance().getMultiLineComments();
   }
   
   /**
    * 
    * @function 判断当前行是否包含单行注释
    *
    * @param lineStr              当前检测的字符串
    * @param startWith   是否检测以注释开始
    * @return true,包含单行注释;false,不包含单行注释.
    */
   private boolean containsSingleLineComment(String lineStr, boolean startWith)
   {
       if (CollectionUtils.isEmpty(this.singleLineComments))
       {
           return false;
       }
       
       for (String singleLineComment : singleLineComments)
       {
           if (StringUtils.isEmpty(singleLineComment))
           {
               continue;
           }
           
           if (startWith)
           {
               if (lineStr.startsWith(singleLineComment.trim()))
               {
                   return true;
               }
           }
           else
           {
               if (lineStr.contains(singleLineComment.trim()))
               {
                   return true;
               }
           }
           
       }
       
       return false;
   }
   
   
    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Parsable#isComment(java.lang.String)
     */
    @Override
    public boolean isComment(String lineStr)
    {
        if (containsSingleLineComment(lineStr, true))
        {
            return true;
        }
        
        boolean flag = innerMultiLineComment;
        
        // 不再多行注释内，需要判断当前行是否是多行注释开始
        if (!this.innerMultiLineComment)
        {
            updateCurMultiLineComment(lineStr, true);
            if (curMultiLineComment != null)
            {
                // 将遇到多行注释的开始标识置为true
                this.innerMultiLineComment = true;
                flag = true;
            }
        }
        
        // 处于多行注释内需要判断是否已经结束
        if (this.innerMultiLineComment)
        {
            if (containsMultiCommentEnd(lineStr, true))
            {
                this.innerMultiLineComment = false;
            }
        }
        
        return flag;
    }
    
    /**
     * @function 
     *
     * @param lineStr              当前检测的字符串
     * @param endWith     是否检测以注释结束
     * @return true,包含多行注释结束符;false,不包含多行注释结束符.
     */
    private boolean containsMultiCommentEnd(String lineStr, boolean endWith)
    {
        if (this.curMultiLineComment == null || StringUtils.isEmpty(this.curMultiLineComment.getEnd()))
        {
            return false;
        }
        
        if (endWith)
        {
            if (lineStr.endsWith(curMultiLineComment.getEnd().trim()))
            {
                return true;
            }
        }
        else
        {
            if (lineStr.contains(curMultiLineComment.getEnd().trim()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @function 更新当前遇到的多行注释对象
     *
     * @param lineStr 传入的该行字符串
     * @param startWith 校验当前行是否以注释开始（如果是以注释开始，则证明当前行是纯粹的注释行）
     */
    private void updateCurMultiLineComment(String lineStr, boolean startWith)
    {
        if (this.innerMultiLineComment)
        {
            return;
        }
        
        this.curMultiLineComment = null;
        if (CollectionUtils.isEmpty(this.multiLineComments))
        {
            return;
        }
        
        String start = null;
        
        for (MultiLineComment multiLineComment : multiLineComments)
        {
            start = multiLineComment.getStart();
            if (StringUtils.isEmpty(start))
            {
                continue;
            }
            
            if (startWith)
            {
                if (lineStr.startsWith(start.trim()))
                {
                    this.curMultiLineComment = multiLineComment;
                }
            }
            else
            {
                if (lineStr.contains(start.trim()))
                {
                    this.curMultiLineComment = multiLineComment;
                }
            }
        }
        
    }

    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Parsable#isEmpty(java.lang.String)
     */
    @Override
    public boolean isEmpty(String lineStr)
    {
        return StringUtils.EMPTY.equals(lineStr);
    }
    
    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Parsable#isEffective(java.lang.String)
     */
    @Override
    public boolean isEffective(String lineStr)
    {
        // 因为默认的实现先判断空行和注释行，除了那两种类型外其余的都算做有效代码行;所以这里返回true.
        return true;
    }
    
    /* (non-Javadoc)
     * @see com.rampage.codestatistics.rule.Parsable#containsComment(java.lang.String)
     */
    @Override
    public boolean containsComment(String lineStr)
    {
        // 包含单行注释的直接返回true
        if (containsSingleLineComment(lineStr, false))
        {
            return true;
        }
        
        boolean flag = false;
        
        // 需要判断当前行是否包含多行注释，并且需要检测多行注释是否结束
        updateCurMultiLineComment(lineStr, false);
        if (this.curMultiLineComment != null)
        {
            this.innerMultiLineComment = true;
            if (containsMultiCommentEnd(lineStr, false))
            {
                this.innerMultiLineComment = false;
            }
            flag = true;
        }
        
        return flag;
    }
}
