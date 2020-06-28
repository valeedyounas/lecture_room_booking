package client;

import server.ClientCommunicator;

import java.net.Socket;

public class ServerReceive extends Thread {
    private Socket connectSocket;
    private ClientCommunicator tempy;
    private Object response;
    public Object getResponse() {
        return response;
    }
    public ServerReceive(Socket a ,ClientCommunicator b) {
        // TODO Auto-generated constructor stub
        connectSocket = a;
        tempy = b;
        response = null;
    }

    public void run() {
        System.out.println("Server receive thread started");
        while (true) {
            try {
                response = tempy.receiveFrom_server(connectSocket);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
