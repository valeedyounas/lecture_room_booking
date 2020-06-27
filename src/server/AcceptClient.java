package server;

import java.net.Socket;

public class AcceptClient extends Thread {
    ClientCommunicator any;
    Socket connectSocket;
    public void run() {
        try {
            while(true)
            {
                connectSocket =any.acceptFrom_OtherClient();
                System.out.println("Client connected"+ connectSocket.getPort());
            }
        }
        catch (Exception e) {
            e.printStackTrace(); }

    }
    public AcceptClient(ClientCommunicator x) {
        // TODO Auto-generated constructor stub
        any = x;

    }


    public Socket getConnectSocket() {
        return connectSocket;
    }
}
