package com.movitech.mbox.modules.util;

import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Properties;

public class SFTPChannel {
    Session session = null;
    Channel channel = null;

    private static final Logger LOG = Logger.getLogger(SFTPChannel.class.getName());

    public ChannelSftp getChannel(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, int timeout) throws JSchException {
        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        LOG.debug("Session created.");
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        LOG.debug("Session connected.");

        LOG.debug("Opening Channel.");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        LOG.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                + ", returning: " + channel);
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
    
    public static void main(String[] args) {
    	SFTPChannel sFTPChannel = new SFTPChannel();
    	try {
    		ChannelSftp channelSftp =sFTPChannel.getChannel("172.19.50.154", 22, "admin", "admin", 1000000000);
			System.out.println(channelSftp);
			System.out.println(File.separator);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
