package se.iths.groupmembers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;
    private int port = 8080;

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening on http://localhost:" + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start(int port) {
        this.port = port;
        start();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }
}
