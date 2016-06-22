/**
 * @fileName   MultiLineComment.java
 * @createDate 2016-6-4
 * @createTime 下午9:16:38
 */
package com.rampage.codestatistics.model.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description 多行注释的类
 * 
 * @type		MultiLineComment
 * @author      KiDe
 * @version		V1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="multiLineComment")
public class MultiLineComment implements Serializable
{
    private static final long serialVersionUID = 2927010963191687804L;

    @XmlElement(name="start")
    private String start;
    
    @XmlElement(name="end")
    private String end;

    public String getStart()
    {
        return start;
    }

    public void setStart(String start)
    {
        this.start = start;
    }

    public String getEnd()
    {
        return end;
    }

    public void setEnd(String end)
    {
        this.end = end;
    }
}
