package client;

import application.Main;
import application.mainFactory;
import server.AcceptClient;
import server.ClientCommunicator;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Demo {
    private int port_toRunon;
    private int forThread = -1;
    public static ClientCommunicator tempx;
    private ServerCommunicator tempy;
    Socket connectSocket;
    Main main = mainFactory.createMain();

    public String getIp() {
        return tempx.get_myIp();
    }


    public Demo() {
        Scanner cin = new Scanner(System.in);
        String port = cin.nextLine();
        port_toRunon = Integer.parseInt(port);
        tempx = new ClientCommunicator(port_toRunon);
        tempy = new ServerCommunicator();
    }

    public int getPortToRunOn() {
        return port_toRunon;
    }

    public Socket getConnectSocket() {
        return connectSocket;
    }

    public Socket start_AcceptThread() {

        AcceptClient t = null;
        try {
            //0 indicates which friend im talking to

            t = new AcceptClient(tempx);
            t.start();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return connectSocket;
    }


    //server communicating
    public void startServer_communication() {
        Socket s = tempy.sendConnectRequest();
    }

    public void send_toServer(Object o) {
        try {
            tempy.sendTo_server(o);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Object receive_fromServer() {
        Object o = null;
        try {
            o = tempy.receiveFrom_server();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return o;


    }

    public void close_connection() {
        try {
            tempy.closeConnection_server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
