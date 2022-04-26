package com.example.ftpapplication;


import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;


@Route
public class MainView extends VerticalLayout {

    public MainView () throws IOException {
        add(new H1("Ftp Web App"));
//        FtpClient ftpClient = new FtpClient();

        FTPClient ftpClient = new FTPClient();

        try{
            add(new H3("Starting server..."));

            ftpClient.connect("91.222.128.11", 21);
            ftpClient.login("testftp_guest", "12345");

            int replyCode = ftpClient.getReplyCode();
            add(new H3(String.valueOf(replyCode)));


            if (!FTPReply.isPositiveCompletion(replyCode)) {
                add(new H2("!!!"));
            }


//            ftpClient.logout();
        }
        catch (Exception exception){

        }


    }
}
