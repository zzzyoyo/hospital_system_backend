package fudan.se.lab2.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    private FileUtil(){

    }
    
    //静态方法：三个参数：文件的二进制，文件路径，文件名
    //通过该方法将在指定目录下添加指定文件
   public static void fileupload(byte[] file,String fileName,String filepath) throws IOException {
       Logger logger = LoggerFactory.getLogger(FileUtil.class);
        File targetfile = new File(filepath);
        if(!targetfile.exists()) {
            targetfile.mkdirs();
        }
        File localfile = new File(filepath+fileName);
        logger.debug(filepath+fileName);
        if(localfile.exists()) {
           logger.debug( "删除原文件");

        }
       FileOutputStream out = new FileOutputStream(localfile);
        //二进制流写入
       try {
           out.write(file);
           out.flush();

       }catch(IOException ex){
           logger.error("FileService Upload 方法写入文件异常:",ex);
       }finally {
           logger.debug("结束存储");
           out.close();
       }
    }


}
