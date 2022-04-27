package com.example.ftpapplication;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class FtpClient {

    FTPClient ftpClient = new FTPClient();
    //Необходимые структуры как хранить общ
    public int totalSize = 0;
    public String gfiles = "";
//    public String path = "";

    public FtpClient() throws IOException {

        String host = "91.222.128.11";
        int port = 21;
        String user = "testftp_guest";
        String pass = "12345";
        ftpClient.setControlEncoding("UTF-8");

//        FTPClient ftpClient = new FTPClient();

        try{

            ftpClient.connect(host, port);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new IOException("Exception in connecting to FTP Server");
            }

            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                throw new IOException("Exception in login to FTP Server");
            }
            //Поиск всех файлов
            getFiles("");





        }
        catch (IOException e){
            e.printStackTrace();
        }

//        ftpClient.connect(hostname, port);
//        ftpClient.login(username, password);
//        System.out.print(ftpClient.getReplyString());


    }

    //Передаем "" изначально

    public void getFiles(String path) throws IOException {

        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        for (FTPFile file : ftpFiles){

            if(file.isDirectory()){

                gfiles+= file.getName() + "\n";
                getFiles(path + "/" + file.getName());
            }
            else {
                totalSize += file.getSize();
                gfiles+= file.getName() + " " + file.getSize() + "\n";

            }
        }
    }

    private void showServerReply(FTPClient ftpClient) {

        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String reply : replies) {
                System.out.println("SERVER: " + reply);
            }
        }
    }


}
