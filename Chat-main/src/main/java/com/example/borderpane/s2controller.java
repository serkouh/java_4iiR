package com.example.borderpane;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class s2controller {

    @FXML
    private TextField MymsgID;
    @FXML
    private TextField HostID;

    @FXML
    private TextField PortID;
    @FXML
    private TextField adresse;
    PrintWriter pw;

    @FXML
    private ListView testView;

    @FXML
    protected void onconnect() throws Exception{
        String host=HostID.getText();
        int port = Integer.parseInt(PortID.getText());
        Socket s =new Socket(host,port);
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = s.getOutputStream();
        String Ip = s.getRemoteSocketAddress().toString();
        pw = new PrintWriter(os,true);
        new Thread(()->{
            while(true){
                try {
                    String response = br.readLine();
                    Platform.runLater(()->{
                        testView.getItems().add(response);

                    });
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
    @FXML
    public void onsubmit(){
        String message=MymsgID.getText();
        pw.println(message);
    }
}
