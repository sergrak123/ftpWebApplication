package com.example.ftpapplication;


import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.IOException;


@Route
public class MainView extends VerticalLayout {

    public MainView () throws IOException {
        add(new H2("Apps"));
        FtpClient ftpClient = new FtpClient();

    }
}
