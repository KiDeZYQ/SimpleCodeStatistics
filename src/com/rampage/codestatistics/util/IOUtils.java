/**
 * @fileName   IOUtils.java
 * @createDate 2016-6-4
 * @createTime 上午8:14:32
 */
package com.rampage.codestatistics.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @description 
 * 
 * @type		IOUtils
 * @author      KiDe
 * @version		V1.0.0
 */
public class IOUtils
{
    
    private IOUtils()
    {
        
    }
    
    /**
     * 
     * @function 安静的关闭流
     *
     * @param io 传入的带待关闭的流
     */
    public static void closeQuietly(Closeable io)
    {
        if (io == null)
        {
            return;
        }
        
        try
        {
            io.close();
        }
        catch (IOException e)
        {
        }
    }
}
