package com.example.ftpapplication;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;


@Route
public class MainView extends VerticalLayout {

    Socket socket = null;

    BufferedReader reader = null;

    BufferedWriter writer = null;

    boolean DEBUG = false;

    public MainView () throws IOException {


        //frontend
        TextField hostField = new TextField();
        TextField userField = new TextField();
        TextField passField = new TextField();

        hostField.setLabel("Host");
        userField.setLabel("Username");
        passField.setLabel("Password");

        Button findButton = new Button("Начать");
        findButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout hl = new HorizontalLayout(hostField,userField,passField, findButton);
        hl.setAlignItems(Alignment.END);

        VerticalLayout vr = new VerticalLayout(new H2("Ftp Client Web App"), hl);
        vr.setAlignItems(Alignment.CENTER);
        add(vr);

        SimpleFTP simpleFTP = new SimpleFTP(hostField.getValue(),21, userField.getValue(), passField.getValue());


    }
}
