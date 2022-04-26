package com.example.ftpapplication;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;


@Route
public class MainView extends VerticalLayout {

    public MainView () throws IOException {

        add(new H1("Ftp Web App"));

        FTPClient ftpClient = new FTPClient();
        try{
            add(new H3("Starting server..."));

            ftpClient.connect("91.222.128.11", 21);
            ftpClient.login("testftp_guest", "12345");

            
            int replyCode = ftpClient.getReplyCode();
            add(new H3(String.valueOf(replyCode)));

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                add(new H2("!!!"));///
            }
            
            FtpClient ftpClient1 = new FtpClient();
            add(new Span(String.valueOf(ftpClient1.totalSize)));

            FTPFile[] ftpFiles = ftpClient.listFiles("");
            String info = "File number:" + ftpFiles.length + "\n"
                    + ftpFiles[0].getName() + "\n"
                    + ftpFiles[0].getGroup()+ "\n"
                    + ftpFiles[0].getLink()+ "\n"
                    + ftpFiles[0].getSize()+ "\n"
                    + ftpFiles[0].getType()+ "\n"
                    + ftpFiles[0].isDirectory()+ "\n"
                    + ftpFiles[0].isFile()+ "\n";


            TextArea tx = new TextArea();
            tx.setValue(ftpClient1.gfiles);
            add(tx);
//            return Arrays.stream(files)
//                    .map(FTPFile::getName)
//                    .collect(Collectors.toList());
            //            ftpClient.changeWorkingDirectory("/htdocs/123");

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    public void getAllFiles(){

    }
}
