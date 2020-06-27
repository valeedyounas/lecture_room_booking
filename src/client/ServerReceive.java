package client;

public class ServerReceive {
    ServerCommunicator tempy;

    public ServerReceive(ServerCommunicator a) {
        // TODO Auto-generated constructor stub
        tempy = a;
    }

    public void run() {
        System.out.println("Server receive thread started");
        try {
            tempy.receiveFrom_server();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
