package com.tugos.dst.admin.utils;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * <p> 无 </p>
 */
public class FileRead {

    //文件读取指针游标
    public static long pointer = 0;

    public static void main(String[] agrs){
        while (true){
            System.out.println("pointer:"+pointer);
            String paht ="C:\\Users\\lenovo\\Desktop\\河北有线计划单excel\\河北养乐多计划单自定义标签定向.sql";
            randomRed(paht);
            try {
                System.out.println("停顿开始："+System.currentTimeMillis());
                //停顿10秒，方便操作日志文件，看效果。
                Thread.sleep(10000);
                System.out.println("停顿结束："+System.currentTimeMillis());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * 读取文件的方法
     * @param path 文件路径
     * **/
    public static void randomRed(String path){
        try{
            File file = new File(path);
            if(file == null){
                System.out.println("文件不存在");
                return;
            }
            RandomAccessFile raf=new RandomAccessFile(file, "r");
            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            System.out.println("RandomAccessFile文件指针的初始位置:"+raf.getFilePointer());
            raf.seek(pointer);//移动文件指针位置
            String line =null;
            //循环读取
            while((line = raf.readLine())!=null){
                if(line.equals("")){
                    continue;
                }
                //打印读取的内容,并将字节转为字符串输入，做转码处理，要不然中文会乱码
                line = new String(line.getBytes("ISO-8859-1"),"gb2312");
                System.out.println("line :"+line);
            }
            //文件读取完毕后，将文件指针游标设置为当前指针位置 。 运用这个方法可以做很多文章，比如查到自己想要的行的话，可以记下来，下次直接从这行读取
            pointer = raf.getFilePointer();
        }catch(Exception e){
            System.out.println("异常："+ e.getMessage());
            e.printStackTrace();
        }
    }

}
