package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerCommunicator {
    private static final int server_PORT = 4000;
    private static final String server_ip_address = "127.0.0.1";

    private Socket thisClient_socket;


    private ObjectInputStream thisClient_receivingStream;

    private ObjectOutputStream server_sendingStream;

    public ServerCommunicator() {
        // TODO Auto-generated constructor stub

    }

    public Socket sendConnectRequest()  {
        try {
            thisClient_socket = new Socket(server_ip_address,server_PORT);
            thisClient_socket.setKeepAlive(true);
            System.out.println("Connection achieved: "+ thisClient_socket.getPort());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return thisClient_socket;

    }

    public void sendTo_server(Object o) throws IOException {
        server_sendingStream = new ObjectOutputStream(thisClient_socket.getOutputStream());
        server_sendingStream.writeObject(o);
        server_sendingStream.flush();
    }

    public Object receiveFrom_server() throws Exception {
        thisClient_receivingStream = new ObjectInputStream(thisClient_socket.getInputStream());
        return thisClient_receivingStream.readObject();
		/*ClientThread t = new ClientThread(connectSocket);
		t.start();
		t.join();
		return t.getToFetch();*/
    }
}
