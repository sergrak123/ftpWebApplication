package com.example.ftpapplication;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;


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

//        FTPClient ftpClient = new FTPClient();

        try{
            ftpClient.connect(host, port);
            ftpClient.login(user, pass);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new IOException("Exception in connecting to FTP Server");
            }
            //Поиск всех файлов
            getFiles("");
        }
        catch (Exception e){
            e.printStackTrace();
        }

//        ftpClient.connect(hostname, port);
//        ftpClient.login(username, password);
//        System.out.print(ftpClient.getReplyString());


    }
    //Передаем ""изначально

    public void getFiles(String path) throws IOException {

        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        for (FTPFile file : ftpFiles){

            if(file.isDirectory()){

                getFiles(path + "/" + file.getName());
                gfiles+= file.getName() + "\n";
            }
            else {
                totalSize += file.getSize();
                gfiles+= file.getName() + "\n";
            }
        }
    }


}
