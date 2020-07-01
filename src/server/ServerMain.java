package server;

import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        ClientCommunicator cc = new ClientCommunicator(4000);
        AcceptClient ac = new AcceptClient(cc);
        ac.start();
        //RoomStatusUpdation rsu = new RoomStatusUpdation();
        //rsu.start();

    }
}
