package cn.flow.component.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class FileUtils {
 
    public static void main(String[] args) {
        String newLog = " Date:" + new Date() + "  |";
        appendLog(newLog);
    }
    
    public static String getCurrentDate() {
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sm.format(new Date());
    }
 
    public static String strRight(String value) {
        return value.substring(value.length() - 2, value.length());
    }
 
    public static void appendLog(String newLog) {
        Scanner sc = null;
        PrintWriter pw = null;
        Calendar c = new GregorianCalendar();
        File log = new File("log\\loginfo" + c.get(Calendar.YEAR) + strRight("00" + (c.get(Calendar.MONTH) + 1)) + strRight("00" + c.get(Calendar.DAY_OF_MONTH)) + ".log");
        try {
            if (!log.exists())//如果文件不存在,则新建.
            {
                File parentDir = new File(log.getParent());
                if (!parentDir.exists())//如果所在目录不存在,则新建.
                {
                    parentDir.mkdirs();
                }
                log.createNewFile();
            }
            sc = new Scanner(log);
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine())//先读出旧文件内容,并暂存sb中;
            {
                sb.append(sc.nextLine());
                sb.append("\r\n");//换行符作为间隔,扫描器读不出来,因此要自己添加.
            }
            sc.close();
 
            pw = new PrintWriter(new FileWriter(log), true);
            /*
             * A.
             */
            pw.println(sb.toString());//,写入旧文件内容.
   /*
             * B.
             */
            pw.println(newLog + "  [" + getCurrentDate() + "]");//写入新日志.
   /*
             * 如果先写入A,最近日志在文件最后. 如是先写入B,最近日志在文件最前.
             */
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void appendLog(String logFileName,String newLog, String filePath) {
//        Scanner sc = null;
        PrintWriter pw = null;
        File log = new File(filePath + logFileName+".bpmn20.xml");
        try {
            if (!log.exists())//如果文件不存在,则新建.
            {
                File parentDir = new File(log.getParent());
                if (!parentDir.exists())//如果所在目录不存在,则新建.
                {
                    parentDir.mkdirs();
                }
                log.createNewFile();
            }

            pw = new PrintWriter(new FileWriter(log), true);

            // TODO 旧的文件内容丢弃
//            sc = new Scanner(log);
//            StringBuilder sb = new StringBuilder();
//            while (sc.hasNextLine())//先读出旧文件内容,并暂存sb中;
//            {
//                sb.append(sc.nextLine());
//                sb.append("\r\n");//换行符作为间隔,扫描器读不出来,因此要自己添加.
//            }
//            sc.close();
//            // 写入A
//            pw.println(sb.toString());//,写入旧文件内容.
            // 写入B
            pw.println(newLog);//写入新日志.
            /*
             * 如果先写入A,最近日志在文件最后. 如是先写入B,最近日志在文件最前.
             */
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
