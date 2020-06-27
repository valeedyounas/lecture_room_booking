package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientCommunicator {
    private InetAddress thisClient_ip;
    private static int thisClient_port = 5000;

    private String toSend_ip;
    private int toSend_port;

    private ServerSocket thisClient_socket;
    private Socket otherClient_socket;


    //private DataOutputStream thisClient_sendingStream;
    private ObjectInputStream thisClient_receivingStream;

    private ObjectOutputStream otherClient_sendingStream;
    //private DataInputStream otherClient_receivingStream;

    public String get_myIp() {
        return thisClient_ip.getHostAddress();
    }

    public int get_myPort() {
        return thisClient_port;
    }

    public String get_toSendIP() {
        return toSend_ip;
    }

    public int get_toSendPort() {
        return toSend_port;
    }

    public ClientCommunicator(int port) {
        // TODO Auto-generated constructor stub
        thisClient_port = port;
        otherClient_socket = null;
        try {
            thisClient_ip = InetAddress.getLocalHost();
            thisClient_socket = new ServerSocket(thisClient_port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket acceptFrom_OtherClient() throws Exception {
        return thisClient_socket.accept();
    }

    public Object receiveFrom_otherClient(Socket connectSocket) throws Exception {
        thisClient_receivingStream = new ObjectInputStream(connectSocket.getInputStream());
        return thisClient_receivingStream.readObject();
		/*ClientThread t = new ClientThread(connectSocket);
		t.start();
		t.join();
		return t.getToFetch();*/
    }

    public Object receiveFrom_server(Socket connectSocket) throws Exception {
        thisClient_receivingStream = new ObjectInputStream(connectSocket.getInputStream());
        return thisClient_receivingStream.readObject();
		/*ClientThread t = new ClientThread(connectSocket);
		t.start();
		t.join();
		return t.getToFetch();*/
    }

    public Socket openConnection(String ip, int port) throws Exception {

        toSend_ip = ip;
        toSend_port = port;
        otherClient_socket = new Socket(toSend_ip, toSend_port);
        otherClient_socket.setKeepAlive(true);
        System.out.println("otherclient connected: " + otherClient_socket.getPort());

        return otherClient_socket;
    }

    public boolean isConnectionClosed() {
        boolean return_val;
        if (otherClient_socket == null || otherClient_socket.isClosed())
            return_val = true;
        else
            return_val = false;
        return return_val;

    }

    public void sendTo_OtherClient(Object o) throws Exception {
        otherClient_sendingStream = new ObjectOutputStream(otherClient_socket.getOutputStream());
        otherClient_sendingStream.writeObject(o);
        otherClient_sendingStream.flush();
    }

    public void setToSend_ip(String toSend_ip) {
        this.toSend_ip = toSend_ip;
    }


    public void setToSend_port(int toSend_port) {
        this.toSend_port = toSend_port;
    }
}
