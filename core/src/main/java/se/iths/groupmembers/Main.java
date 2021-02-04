package se.iths.groupmembers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 5050;

    public static void main(String[] args) {
        System.out.println("Starting server...");

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started!\nListening on http://localhost:" + PORT);

            while (true) {
                Socket socket = serverSocket.accept();

                executorService.execute(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String line = input.readLine();
                System.out.println(line);
                if (line.isEmpty()) {
                    break;
                }
            }
            PrintWriter output = new PrintWriter(socket.getOutputStream());

            String page = "<html><head><title>Demo page</title></head><body><h1>Hello World</h1></body></html>";

            writeHTML(output, page);
            output.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeHTML(PrintWriter writer, String html) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Length: " + html.getBytes().length);
        writer.println("Content-Type: text/html");
        writer.println("");
        writer.println(html);
    }
}
