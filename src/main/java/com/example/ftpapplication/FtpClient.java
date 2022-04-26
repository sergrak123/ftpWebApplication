package com.example.ftpapplication;

//import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


import java.io.IOException;
import java.util.logging.Logger;


public class FtpClient {
    private static Logger log = Logger.getLogger(FtpClient.class.getName());


    public FtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();

        try{
            log.info("Some message11111111111");
            ftpClient.connect("91.222.128.11", 21);
            ftpClient.login("testftp_guest", "12345");
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                log.info("Some message");
            }


//            ftpClient.logout();
        }
        catch (Exception exception){

        }

//        ftpClient.connect(hostname, port);
//        ftpClient.login(username, password);
//        System.out.print(ftpClient.getReplyString());

//        String server = "www.yourserver.net";
//        int port = 21;
//        String user = "username";
//        String pass = "password";
    }


}
