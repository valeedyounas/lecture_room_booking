package client;

public class ServerReceive extends Thread {
    ServerCommunicator tempy;
    private Object response;
    public ServerReceive(ServerCommunicator a) {
        // TODO Auto-generated constructor stub
        tempy = a;
    }

    public void run() {
        System.out.println("Server receive thread started");
        try {
            response = tempy.receiveFrom_server();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Object getResponse() {
        return response;
    }
}
