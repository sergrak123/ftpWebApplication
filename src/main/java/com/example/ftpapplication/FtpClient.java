package com.example.ftpapplication;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

public class FtpClient {

    public FtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();

        ftpClient.connect("91.222.128.11", 21);
        ftpClient.login("testftp_guest", "12345");
//        ftpClient.connect(hostname, port);
//        ftpClient.login(username, password);
//        System.out.print(ftpClient.getReplyString());
    }


}
