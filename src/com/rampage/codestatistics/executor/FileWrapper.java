/**
 * @fileName   FileWrapper.java
 * @createDate 2016-6-4
 * @createTime 下午7:34:54
 */
package com.rampage.codestatistics.executor;

import java.io.File;

/**
 * @description 文件包装类（因为要要输出文件名带相对于扫描目录的路径）
 * 
 * @type		FileWrapper
 * @author      KiDe
 * @version		V1.0.0
 */
public class FileWrapper
{
    /**
     * 包装的文件
     */
    private final File file;
    
    /**
     * 文件对于扫描跟路径的相对路径
     */
    private String relativePath;

    public FileWrapper(File file)
    {
        this.file = file;
    }
    
    public File getFile()
    {
        return file;
    }

    public String getRelativePath()
    {
        return relativePath;
    }

    public void setRelativePath(String relativePath)
    {
        this.relativePath = relativePath;
    }
}
