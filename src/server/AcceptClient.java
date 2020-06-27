package server;

import java.net.Socket;

public class AcceptClient extends Thread {
    ClientCommunicator any;
    Socket connectSocket;
    public void run() {

        while (true)
        {
            try {
                System.out.println("Waiting for client to connect!");
                connectSocket = any.acceptFrom_OtherClient();
                System.out.println("client Connected" + connectSocket.getLocalPort());
            }
            catch (Exception e) { e.printStackTrace(); }

            if (connectSocket!=null)
            {

                DisplayReceiveThread t1 = new DisplayReceiveThread(connectSocket,any,msgs,list);
                t1.start();
            }

        }
    }
    public AcceptClientThread(Client_communicator x, ObservableList<String> h,ArrayList<ActiveUser> l) {
        // TODO Auto-generated constructor stub
        any = x;
        connectSocket = null;
        msgs = h;
        list = l;
    }

    public Socket getConnectSocket() {
        return connectSocket;
    }
}
