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

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


@Route
public class MainView extends VerticalLayout {

    public MainView () throws IOException {

        add(new H1("Ftp Web App"));
        add(new H3("Starting server..."));

        try{

            String ftpUrl = "ftp://%s:%s@%s/";
            ftpUrl = String.format(ftpUrl, "testftp_guest", "12345", "91.222.128.11");
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            System.out.println("--- START ---");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("--- END ---");

//            inputStream.close();
//            OutputStream outputStream = conn.getOutputStream();

//            conn.connect();
//            TextArea tx = new TextArea();
//            tx.setValue(is.toString());
//            add(tx);
//            System.out.println(is.toString());
//            System.out.println(conn.getInputStream().toString());
//            System.out.println(conn.getContent().toString());










//            FtpClient ftpClient1 = new FtpClient();
//            add(new Span(String.valueOf(ftpClient1.totalSize)));
//
//            TextArea tx = new TextArea();
//            tx.setValue(ftpClient1.gfiles);
//            add(tx);






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
