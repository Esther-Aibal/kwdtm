package com.movitech.mbox.modules.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import com.jcraft.jsch.ChannelSftp;
import com.movitech.mbox.modules.cms.Constants;

/**
 * Created by gorden on 2017/6/7.
 */
public class FTPUtil {


    private static FTPUtil instance;
   /* public final  static  String FTP_NAME="root";
    public final  static  String FTP_PASSWORD="YZ-it418";
    public final  static  String FTP_HOST="172.19.50.176";*/
    public final  static  String FTP_BASE=Constants.getProperty("sftp.base");
    public  void put(String src,String dest) {
    	SFTPChannel cl = new SFTPChannel();
        ChannelSftp sftp = new ChannelSftp();
        String ftpHost =Constants.getProperty("sftp.ip.address");
        int ftpPort =Integer.valueOf(Constants.getProperty("sftp.port"));
        String ftpUserName = Constants.getProperty("sftp.user");
        String ftpPassword =Constants.getProperty("sftp.pwd");
        int timeout =Integer.valueOf(Constants.getProperty("sftp.timeout"));
        try {
            sftp = cl.getChannel(ftpHost, ftpPort, ftpUserName, ftpPassword, timeout);
           
            
            sftp.put(src,dest,ChannelSftp.OVERWRITE);
            sftp.quit();
            cl.closeChannel();
        } catch (Exception e) {
        	e.printStackTrace();
            sftp.quit();
            try {
                cl.closeChannel();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
    public  void put(InputStream src,String dest) {
        SFTPChannel cl = new SFTPChannel();
        ChannelSftp sftp = new ChannelSftp();
        String ftpHost =Constants.getProperty("sftp.ip.address");
        int ftpPort =Integer.valueOf(Constants.getProperty("sftp.port"));
        String ftpUserName = Constants.getProperty("sftp.user");
        String ftpPassword =Constants.getProperty("sftp.pwd");
        int timeout =Integer.valueOf(Constants.getProperty("sftp.timeout"));
        try {
            sftp = cl.getChannel(ftpHost, ftpPort, ftpUserName, ftpPassword, timeout);
            sftp.put(src,dest,ChannelSftp.OVERWRITE);
            sftp.quit();
            cl.closeChannel();
        } catch (Exception e) {
            sftp.quit();
            e.printStackTrace();
            try {
                cl.closeChannel();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
    public  void delete(String dest) {
        SFTPChannel cl = new SFTPChannel();
        ChannelSftp sftp = new ChannelSftp();
        String ftpHost =Constants.getProperty("sftp.ip.address");
        int ftpPort =Integer.valueOf(Constants.getProperty("sftp.port"));
        String ftpUserName = Constants.getProperty("sftp.user");
        String ftpPassword =Constants.getProperty("sftp.pwd");
        int timeout =Integer.valueOf(Constants.getProperty("sftp.timeout"));
        try {
            sftp = cl.getChannel(ftpHost, ftpPort, ftpUserName, ftpPassword, timeout);
            sftp.rm(dest);
            sftp.quit();
            cl.closeChannel();
        } catch (Exception e) {
            sftp.quit();
            try {
                cl.closeChannel();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
    public void get(String dest,OutputStream out) {
        SFTPChannel cl = new SFTPChannel();
        ChannelSftp sftp = new ChannelSftp();
        String ftpHost =Constants.getProperty("sftp.ip.address");
        int ftpPort =Integer.valueOf(Constants.getProperty("sftp.port"));
        String ftpUserName = Constants.getProperty("sftp.user");
        String ftpPassword =Constants.getProperty("sftp.pwd");
        int timeout =Integer.valueOf(Constants.getProperty("sftp.timeout"));
        try {
            sftp = cl.getChannel(ftpHost, ftpPort, ftpUserName, ftpPassword, timeout);
            sftp.get(dest, out);
            sftp.quit();
            cl.closeChannel();
        } catch (Exception e) {
            sftp.quit();
            try {
                cl.closeChannel();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
//    	FTPUtil ftpUtil=new FTPUtil();
//    	File f = new File("D:\\AA.txt");
//    	OutputStream out;
//		try {
//			out = new FileOutputStream(f);
//			ftpUtil.get(FTPUtil.FTP_BASE+File.separator+"4807af1cd1d147969233881a41f76e79.jpg",out);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
    	double fileSizeAsDouble = Double.parseDouble("198265");
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println(df.format(fileSizeAsDouble/1024/1024));
	}
}
