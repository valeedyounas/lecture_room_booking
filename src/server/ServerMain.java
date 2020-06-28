package server;

public class ServerMain {
    public static void main(String[] args) {
        ClientCommunicator cc = new ClientCommunicator(4000);
        AcceptClient ac = new AcceptClient(cc);
        ac.start();
        ReceiveThread rt = new ReceiveThread(ac.getConnectSocket(),cc);
        rt.start();

    }
}
