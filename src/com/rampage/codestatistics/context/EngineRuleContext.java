/**
 * @fileName   EngineRuleContext.java
 * @createDate 2016-6-4
 * @createTime 下午3:26:57
 */
package com.rampage.codestatistics.context;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import org.apache.log4j.Logger;

import com.rampage.codestatistics.model.common.MultiLineComment;
import com.rampage.codestatistics.model.constant.PathConstants;
import com.rampage.codestatistics.model.xml.CountRule;
import com.rampage.codestatistics.model.xml.Engine;
import com.rampage.codestatistics.model.xml.SourceType;
import com.rampage.codestatistics.rule.DefaultRuleEngine;
import com.rampage.codestatistics.rule.intf.RuleEngine;
import com.rampage.codestatistics.util.CollectionUtils;
import com.rampage.codestatistics.util.JAXBUtils;
import com.rampage.codestatistics.util.StringUtils;

/**
 * @description 引擎规则上下文(初始化的值按照c/c++源码统计来实现初始化)
 * 
 * @type		EngineRuleContext
 * @author      KiDe
 * @version		V1.0.0
 */
public class EngineRuleContext
{
    /**
     * 规则引擎
     */
    private RuleEngine engine = new DefaultRuleEngine();
    
    /**
     * 要统计的源码对象信息
     */
    private SourceCodeType source = new SourceCodeType();
    
    private static final Lock LOCK = new ReentrantLock();
    
    private static volatile EngineRuleContext instance;
    
    private static final Logger LOGGER = Logger.getLogger(EngineRuleContext.class);

    private EngineRuleContext()
    {
        
    }
    
    public static EngineRuleContext getInstance()
    {
        if (instance == null)
        {
            initEngineRule();
        }
        
        return instance;
    }
    
    /**
     * @function 初始化引擎规则
     *
     */
    private static void initEngineRule()
    {
        LOCK.lock();
        try
        {
            if (instance != null)
            {
                return;
            }
            
            instance = new EngineRuleContext();
            CountRule rule = JAXBUtils.parseFile2Object(new File(PathConstants.RULE_FILE_PATH), CountRule.class);
            
            Engine engine = rule.getEngine();
            List<SourceType> sourceCodeTypes = rule.getSourceTypes();
            SourceType curSourceType = null;
            if (engine != null)
            {
                // 配置了新的其他规则引擎
                if (!StringUtils.isBlank(engine.getEngineClass()) && !"com.rampage.codestatistics.rule.DefaultRuleEngine".equals(engine.getEngineClass().trim()))
                {
                    instance.engine = (RuleEngine) Class.forName(engine.getEngineClass().trim()).newInstance();
                }
                
                // 配置了待统计的源码类型，则需要更新默认设置
                String sourceTypeId = engine.getSourceType();
                if (!StringUtils.isBlank(sourceTypeId) && !CollectionUtils.isEmpty(sourceCodeTypes))
                {
                    for (SourceType type : sourceCodeTypes)
                    {
                        if (sourceTypeId.trim().equals(type.getTypeId().trim()))
                        {
                            curSourceType = type;
                            break;
                        }
                    }
                }
            }
            else
            {
                LOGGER.warn("No rule engine configured for the tool, Use default rule engine instead!");
            }
            
            // 如果没有配置要统计的源码类型，直接返回（使用默认的c/c++配置）
            if (curSourceType == null)
            {
                LOGGER.warn("No source code type configured for the rule engine, Use default source code type instead!");
                return;
            }
            
            instance.source.id = curSourceType.getTypeId();
            instance.source.name = curSourceType.getTypeName();
            instance.source.singleLineComments = null;
            String singleLineComments = curSourceType.getSingleLineComment();
            if (!StringUtils.isBlank(singleLineComments))
            {
                instance.source.singleLineComments = Arrays.asList(singleLineComments.split(","));
            }
            
            instance.source.multiLineComments = curSourceType.getMultiLineComments();
            instance.source.fileNameSuffixes = null;
            String fileNameSuffix = curSourceType.getFileNameSuffix();
            if (!StringUtils.isBlank(fileNameSuffix))
            {
                instance.source.fileNameSuffixes = Arrays.asList(fileNameSuffix.split(","));
            }
            
            String ignoreCase = curSourceType.getMatchIgnoreCase();
            if (!StringUtils.isEmpty(ignoreCase) && "false".equals(ignoreCase))
            {
                instance.source.ignoreCase = false;
            }
        }
        catch (Exception e)
        {
            System.out.println("Init engine rule failed, Use default set instead!Detail fail information please see file logs/tool.log!");
            LOGGER.warn("Init engine rule failed, Use default set instead!", e);
        }
        finally
        {
            LOCK.unlock();
        }
    }

    public RuleEngine getEngine()
    {
        return engine;
    }
    
    public String getSourceCodeTypeId()
    {
        return source.id;
    }

    public String getSourceCodeTypeName()
    {
        return source.name;
    }

    public boolean isIgnoreCase()
    {
        return source.ignoreCase;
    }

    public List<String> getFileNameSuffixes()
    {
        return source.fileNameSuffixes;
    }

    public List<String> getSingleLineComments()
    {
        if (CollectionUtils.isEmpty(source.singleLineComments))
        {
            return null;
        }
        return Collections.synchronizedList(source.singleLineComments);
    }

    public List<MultiLineComment> getMultiLineComments()
    {
        if (CollectionUtils.isEmpty(source.multiLineComments))
        {
            return null;
        }
        
        List<MultiLineComment> unmodifyCopy = new ArrayList<MultiLineComment>(source.multiLineComments);
        return Collections.synchronizedList(unmodifyCopy);
    }
    
    /**
     * @description 源码类型对象(初始化的值按照c/c++的配置实现)
     * 
     * @type        SourceCodeType
     * @author      KiDe
     * @version     V1.0.0
     */
    private class SourceCodeType
    {
        /**
         * 源码对应的id
         */
        private String id = "1";
        
        /**
         * 源码名称
         */
        private String name = "c/c++";
        
        /**
         * 是否在进行源码文件后缀匹配的时候忽略大小写
         */
        private boolean ignoreCase = true;
        
        /**
         * 文件后缀名列表
         */
        private List<String> fileNameSuffixes = Arrays.asList(".c", ".h", ".cpp", ".cc", ".cxx");
        
        /**
         * 单行注释标识
         */
        private List<String> singleLineComments = Arrays.asList("//");
        
        /**
         * 多行注释列表
         */
        @SuppressWarnings("serial")
        private List<MultiLineComment> multiLineComments = new ArrayList<MultiLineComment>() {
            {
                // 多行注释也默认设置为c/c++的多行注释
                MultiLineComment multiLineComment = new MultiLineComment();
                multiLineComment.setStart("/*");
                multiLineComment.setEnd("*/");
                this.add(multiLineComment);
            }
        };
    }

}
