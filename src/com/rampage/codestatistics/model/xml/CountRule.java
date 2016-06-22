/**
 * @fileName   EngineRuleContext.java
 * @createDate 2016-6-4
 * @createTime 下午2:42:06
 */
package com.rampage.codestatistics.model.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description 
 * 
 * @type		EngineRuleContext
 * @author      KiDe
 * @version		V1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="countRule")
public class CountRule implements Serializable
{
    private static final long serialVersionUID = 1322829389258761844L;
    
    @XmlElement(name="engine")
    private Engine engine;
    
    @XmlElementWrapper(name="sourceTypes")
    @XmlElement(name="sourceType")
    private List<SourceType> sourceTypes;

    public Engine getEngine()
    {
        return engine;
    }

    public void setEngine(Engine engine)
    {
        this.engine = engine;
    }

    public List<SourceType> getSourceTypes()
    {
        return sourceTypes;
    }

    public void setSourceTypes(List<SourceType> sourceTypes)
    {
        this.sourceTypes = sourceTypes;
    }
}
