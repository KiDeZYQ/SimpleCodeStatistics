/**
 * @fileName   Engine.java
 * @createDate 2016-6-4
 * @createTime 下午2:44:16
 */
package com.rampage.codestatistics.model.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description 
 * 
 * @type		Engine
 * @author      KiDe
 * @version		V1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="engine")
public class Engine implements Serializable
{
    private static final long serialVersionUID = -6643468835504157102L;

    @XmlElement(name="engineClass")
    private String engineClass;
    
    @XmlElement(name="countSourceType")
    private String sourceType;

    public String getEngineClass()
    {
        return engineClass;
    }

    public void setEngineClass(String engineClass)
    {
        this.engineClass = engineClass;
    }

    public String getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(String sourceType)
    {
        this.sourceType = sourceType;
    }
}
