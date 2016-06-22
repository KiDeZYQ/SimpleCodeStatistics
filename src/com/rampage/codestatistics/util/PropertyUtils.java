/**
 *
 * @fileName PropertyUtils.java
 * @creator  KiDe
 */
package com.rampage.codestatistics.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 属性工具类 (当前只用到了读取操作，所以暂时只支持读取)
 * 
 * @author KiDe
 * @version V1.0
 * @date 2013-12-21
 */
public class PropertyUtils
{
    private PropertyUtils()
    {

    }


    /**
     * 
     * @param propFile 属性文件
     * @return 读取的配置文件中的属性key、value组成的map值
     * @throws IOException 文件读取异常将抛出IOException
     */
    public static Map<String, String> getProperties(File propFile)
            throws IOException
    {
        if (!propFile.exists() || !propFile.isFile())
        {
            throw new IOException("Property file:" + propFile.getAbsolutePath() + " not found or is not a valid file!");
        }

        // 读取配置文件
        FileInputStream fis = null;
        Properties property = null;
        try
        {
            fis = new FileInputStream(propFile);
            property = new Properties();
            property.load(fis);
        }
        catch (IOException e)
        {
            throw new IOException("Load property file failed!", e);
        }
        finally
        {
            IOUtils.closeQuietly(fis);
        }

        // 依次遍历所有属性，将属性键值对放入map中
        Set<Entry<Object, Object>> entrySet = property.entrySet();
        if (CollectionUtils.isEmpty(entrySet))
        {
            return null;
        }
        
        Map<String, String> propsMap = new HashMap<String, String>(entrySet.size());
        for (Entry<Object, Object> entry : entrySet)
        {
            propsMap.put(entry.getKey().toString(), entry.getValue().toString());
        }

        return propsMap;
    }
}
