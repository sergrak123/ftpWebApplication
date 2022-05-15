package com.example.ftpapplication;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.*;
import java.net.Socket;


@Route
public class MainView extends VerticalLayout {


    int totalSize = 0;

    public MainView() throws IOException {


        //frontend
        TextField hostField = new TextField();
        TextField userField = new TextField();
        TextField passField = new TextField();

        hostField.setValue("91.222.128.11");
        userField.setValue("testftp_guest");
        passField.setValue("12345");

        hostField.setLabel("Host");
        userField.setLabel("Username");
        passField.setLabel("Password");


        Button findButton = new Button("Начать");
        findButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout hl = new HorizontalLayout(hostField, userField, passField, findButton);
        hl.setAlignItems(Alignment.END);



        //Вывод ссылок

        UnorderedList content = new UnorderedList();
        UnorderedList content2 = new UnorderedList();

        Details details = new Details();
        details.setWidth("450px");
        details.setMinHeight("400px");
        details.setOpened(true);
        details.addThemeVariants(DetailsVariant.FILLED);
        details.setSummaryText("Все файлы: ");

        Details details2 = new Details();
        details2.setWidth("450px");
        details.setMinHeight("400px");
        details2.setOpened(true);
        details2.addThemeVariants(DetailsVariant.FILLED);
        details2.setSummaryText("Расширение файла / Суммарный размер: ");

        HorizontalLayout hl2 = new HorizontalLayout(details, details2);
        hl2.setPadding(true);


        VerticalLayout vr = new VerticalLayout(new H2("Ftp Client web app"), hl, new H3(""), hl2);
        vr.setAlignItems(Alignment.CENTER);
        add(vr);

        Scroller scroller = new Scroller(content);
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);

        Scroller scroller2 = new Scroller(content2);
        scroller2.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        findButton.addClickShortcut(Key.ENTER);

        findButton.addClickListener(clickEvent -> {
            try {
                SimpleFTP simpleFTP = new SimpleFTP(hostField.getValue(), 21, userField.getValue(), passField.getValue());
                simpleFTP.getAllFiles("");
                content.removeAll();
                content2.removeAll();


                for (String name : simpleFTP.fileList.keySet()) {

                    int fileSize = simpleFTP.fileList.get(name);
                    String formatFileSize = SimpleFTP.formatFileSize(fileSize);
                    content.add(new ListItem(name + " : " + formatFileSize));
                }
                scroller.setContent(content);
                details.setContent(scroller);
                details.setSummaryText("Все файлы: " + simpleFTP.fileList.size());


                for (String type : simpleFTP.typeList.keySet()) {

                    int typeSize = simpleFTP.typeList.get(type);
                    String formatTypeSize = SimpleFTP.formatFileSize(typeSize);
                    totalSize += typeSize;
                    System.out.println(type + " " + formatTypeSize);
                    content2.add(new ListItem("Type: " + type + " Size: " + formatTypeSize));
                }
                scroller2.setContent(content2);
                details2.setContent(scroller2);
                details2.addContent(new H5("Total size: " + SimpleFTP.formatFileSize(totalSize)));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
