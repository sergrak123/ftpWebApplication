package com.example.ftpapplication;

import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SimpleFTP {

    Socket socket = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    String response;

    HashMap<String, Integer> fileList = new HashMap<>();
    HashMap<String, Integer> typeList = new HashMap<>();
    ArrayList<String> dirList = new ArrayList<>();

    String host, user, pass;
    int port;

    boolean DEBUG = false;

    public SimpleFTP(String host, int port, String user, String pass) throws IOException {

        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;

        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        response = readLine();
        if (!response.startsWith("220 ")) {
            throw new IOException(
                    "SimpleFTP received an unknown response when connecting to the FTP server: "
                            + response);
        }


        sendLine("USER " + user);
        response = readLine();
        if (!response.startsWith("331 ")) {
            throw new IOException(
                    "SimpleFTP received an unknown response after sending the user: "
                            + response);
        }


        sendLine("PASS " + pass);
        response = readLine();
        if (!response.startsWith("230 ")) {
            throw new IOException(
                    "SimpleFTP was unable to log in with the supplied password: "
                            + response);
        }

        //DIRECTORIES AND FILES
        // getAllFiles("");
    }


    private void sendLine(String line) throws IOException {
        if (socket == null) {
            throw new IOException("FTP is not connected.");
        }
        try {
            writer.write(line + "\r\n");
            writer.flush();
            if (DEBUG) {
                System.out.println("> " + line);
            }
        } catch (IOException e) {
            socket = null;
            throw e;
        }
    }

    private String readLine() throws IOException {
        String line = reader.readLine();
        if (DEBUG) {
            System.out.println("< " + line);
        }
        return line;
    }

    //Возвращает массив строк с информацией (права, размер, группа, название..) обо всех файлах в папке
    private String[] dataResponse(Socket dataSocket) throws IOException {

        InputStream is = dataSocket.getInputStream();
        byte[] buffer = new byte[10000];
        int length = is.read(buffer);
        String[] lines;

        //Проверяем, что возвращаемый список не пустой => папка не пустая
        if (length != -1)
        {
            String data = new String(buffer, 0, length);
            lines = data.split("\n");
        }
        else
            lines = null;
        is.close();

        return lines;
    }

    //Подключаемся к отдельному сокету для передачи списка файлов
    public Socket dataConnection(String ip, int dataPort) throws IOException {

        Socket dataSocket = new Socket(ip, dataPort);
        BufferedWriter dateWriter = new BufferedWriter(new OutputStreamWriter(dataSocket.getOutputStream()));

        dateWriter.write("USER "+ user + "\r\n");
        dateWriter.write("PASS " + user + "\r\n");
        return dataSocket;
    }

    // Получаем список файлов по заданному пути в пассивном режиме
    public String[] getFiles(String path) throws IOException {

        sendLine("PASV");
        response = readLine();

        Socket dataSocket = dataConnection(host,getPort(response));
        sendLine("LIST " + path);
        response = readLine();

        //List
        String [] listFiles = dataResponse(dataSocket);
        response = readLine();

        return listFiles;
    }

    //Получаем список всех файлов на сервере
    public void getAllFiles(String path) throws IOException {

        String[] files = getFiles(path);

        //Проверка на пустую папку
        if (files != null)
        {
            for (String file:files)
            {
                String name = getName(file);
                if(isFile(file))
                {
                    int size = getSize(path + "/" + name);
                    fileList.put(name , size);
                    calcSizeOfEachType(name, size);
                }
                else
                {
//                    System.out.println("DIRECTORY: "+ name);
                    getAllFiles(path + "/" +name);
                }
            }
        }
        else
            System.out.println("Directory is Empty: " + path);

    }

    //Получение размера файла
    private int getSize(String path) throws IOException {
        sendLine("SIZE " + path);
        response = readLine();
        int size = Integer.parseInt(response.substring(4));//b
        return size;
    }

    private String getName(String line) {
        return  line.substring(56, line.length()-1);
    }

    //Парсинг порта для передачи списка файлов
    private int getPort(String response){

        String ip = null;
        int dataPort = -1;
        int opening = response.indexOf('(');
        int closing = response.indexOf(')', opening + 1);
        String dataLink = response.substring(opening + 1, closing);
        StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");

        ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken();
        dataPort = Integer.parseInt(tokenizer.nextToken()) * 256 + Integer.parseInt(tokenizer.nextToken());

        return dataPort;
    }

    private boolean isDir(String line){
        return line.startsWith("d");
    }
    private boolean isFile(String line){
        return line.startsWith("-");
    }

    //Форматированный вывод размера в B/KB/MB
    public static String formatFileSize(double size) {

        String formatSize = null;
        double b = size;
        double k = size/1024.0;
        double m = ((size/1024.0)/1024.0);
        double g = (((size/1024.0)/1024.0)/1024.0);

        DecimalFormat dec = new DecimalFormat("0.0");

        if ( g>1 ) {
            formatSize = dec.format(g).concat(" GB");
        } else if ( m>1 ) {
            formatSize = dec.format(m).concat(" MB");
        } else if ( k>1 ) {
            formatSize = dec.format(k).concat(" KB");
        } else {
            formatSize = dec.format(b).concat(" Bytes");
        }

        return formatSize;
    }

    //Подсчет общего размера файлов по их расширениям
    public void calcSizeOfEachType(String name, int size){

        String extension = name.substring(name.lastIndexOf("."));
        if(typeList.containsKey(extension)){
            int newSize = typeList.get(extension) + size;
            typeList.put(extension, newSize);
        }
        else{
            typeList.put(extension, size);
        }
    }

}
