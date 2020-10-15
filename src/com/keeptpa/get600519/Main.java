package com.keeptpa.get600519;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        //设定股票代码为沪市茅台
        String area ="0";
        String code ="600519";
        //获取日期，基于系统时间
        Date date = new Date();
        SimpleDateFormat formatterY = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);

        System.out.println(formatterY.format(calendar.getTime()));
        System.out.println(formatterY.format(date));

        //处理日期参数

        String date_s = formatterY.format(calendar.getTime());
        String date_e = formatterY.format(date);
        //整合获取地址
        String url="http://quotes.money.163.com/service/chddata.html?code="+area+code+"&start="+date_s+"&end="+date_e+
                "&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
        System.out.println("默认输出到桌面");
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();
        //桌面真实路径
        downLoad(url,code+"_"+formatterY.format(date),com.getPath());
        System.out.println("Done!");

    }
    //学习一个键盘输入
    public static String input(){
    Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    //解析url姿势水平
    public static void  downLoad(String urlStr,String fileName,String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //USERAGENT
        conn.setRequestProperty("User-Agent", "Chrome/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        InputStream inputStream = conn.getInputStream();
        byte[] getData = read(inputStream);
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        System.out.println(url);
        }

    //输入流截取
    public static  byte[] read(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
        }
    }
