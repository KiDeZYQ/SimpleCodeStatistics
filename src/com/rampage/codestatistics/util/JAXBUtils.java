/**
 * @fileName   JAXBUtils.java
 * @createDate 2016-6-4
 * @createTime 下午2:00:26
 */
package com.rampage.codestatistics.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @description xml文件处理的工具类
 * 
 * @type		JAXBUtils
 * @author      KiDe
 * @version		V1.0.0
 */
public class JAXBUtils
{
    /**
     * 
     * @function  将xml文件映射成对象
     *
     * @param file   待处理的xml文件
     * @param clazz  传入的类类型
     * @return       根据xml文件生成的对应类型的对象实例
     * @throws JAXBException 转换出错抛出异常
     */
    public static<T> T parseFile2Object(File file, Class<T> clazz) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarsher = jaxbContext.createUnmarshaller();
        @SuppressWarnings("unchecked")
        T result = (T) unmarsher.unmarshal(file);
        return result;
    }
}
