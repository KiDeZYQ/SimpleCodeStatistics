/**
 * @fileName   Main.java
 * @createDate 2016-6-3
 * @createTime 上午9:08:43
 */
package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.rampage.codestatistics.executor.SimpleExecutor;
import com.rampage.codestatistics.util.StringUtils;

/**
 * @description 项目入口
 * 
 * @type		Main
 * @author      KiDe
 * @version		V1.0.0
 */
public class Main
{
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    
    public static void main(String[] args)
    {
        System.out.println("Please enter the source code count start path:");
        
        String path = null;
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(System.in);
            path = scanner.nextLine();
        }
        finally
        {
            if (scanner!=null)
            {
                scanner.close();
            }
        }
        
        // 校验输入参数
        if (StringUtils.isBlank(path))
        {
            System.out.println("The code count program needs a not empty start path as a parameter!");
            return;
        }
        
        // 加载log4j配置,用来记录日志
        File log4jFile = new File("log4j.properties");
        if (!log4jFile.exists())
        {
            System.out.println("Log configuration file:log4j.properties not found!The program will not generate the file logs/tool.log!");
        }
        else
        {
            PropertyConfigurator.configure("log4j.properties");
        }
        
        File file = null;
        try
        {
            file = new File(path).getCanonicalFile();
        }
        catch (IOException e)
        {
            System.out.println("Illegal parameter:" + path + " as file path!Detail error message please see file logs/tool.log!");
            LOGGER.error("Illegal parameter:" + path + " as file path!", e);
            return;
        }
        
        // 直接判断文件是否存在，提高效率
        if (!file.exists())
        {
            System.out.println("The file:" + file.getAbsolutePath() + " for source code count start path was not existed!");
            return;
        }
        
        // 执行器执行文件处理
        SimpleExecutor executor = new SimpleExecutor(file);
        executor.execute();
        
        
    }
}
