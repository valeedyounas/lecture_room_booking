package server;

import java.net.Socket;

public class AcceptClient extends Thread {
    ClientCommunicator any;
    Socket connectSocket;
    public void run() {
        System.out.println("Server started, waiting for client...");
        try {
            while(true)
            {
                connectSocket =any.acceptFrom_OtherClient();
                System.out.println("Client connected "+ connectSocket.getPort());
                if(connectSocket!= null)
                {
                    ReceiveThread t1 = new ReceiveThread(connectSocket,any);
                    t1.start();
                }
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
