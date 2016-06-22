/**
 * @fileName   SourceType.java
 * @createDate 2016-6-4
 * @createTime 下午3:08:17
 */
package com.rampage.codestatistics.model.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.rampage.codestatistics.model.common.MultiLineComment;

/**
 * @description 
 * 
 * @type		SourceType
 * @author      KiDe
 * @version		V1.0.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="sourceType")
public class SourceType implements Serializable
{
    private static final long serialVersionUID = -7755225964362440928L;

    @XmlElement(name="typeId")
    private String typeId;
    
    @XmlElement(name="typeName")
    private String typeName;
    
    @XmlElement(name="matchIgnoreCase")
    private String matchIgnoreCase;
    
    @XmlElement(name="fileNameSuffix")
    private String fileNameSuffix;
    
    @XmlElement(name="singleLineComment")
    private String singleLineComment;
    
    @XmlElementWrapper(name="multiLineComments")
    @XmlElement(name="multiLineComment")
    private List<MultiLineComment> multiLineComments;

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getMatchIgnoreCase()
    {
        return matchIgnoreCase;
    }

    public void setMatchIgnoreCase(String matchIgnoreCase)
    {
        this.matchIgnoreCase = matchIgnoreCase;
    }

    public String getFileNameSuffix()
    {
        return fileNameSuffix;
    }

    public void setFileNameSuffix(String fileNameSuffix)
    {
        this.fileNameSuffix = fileNameSuffix;
    }

    public String getSingleLineComment()
    {
        return singleLineComment;
    }

    public void setSingleLineComment(String singleLineComment)
    {
        this.singleLineComment = singleLineComment;
    }

    public List<MultiLineComment> getMultiLineComments()
    {
        return multiLineComments;
    }

    public void setMultiLineComments(List<MultiLineComment> multiLineComments)
    {
        this.multiLineComments = multiLineComments;
    }
}
