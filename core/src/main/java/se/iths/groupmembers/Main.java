package se.iths.groupmembers;

import se.iths.groupmembers.spi.Page;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {
    private static final int PORT = 5050;
    private static final ServiceLoader<Page> loader = ServiceLoader.load(Page.class);
    private static List<String> routes = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Starting server...");
        routes = getRoutes();

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
            /*
             * Creates a BufferedReader and reads the first header line so that we can get the request method and the
             *  path
             */
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String headerLine = input.readLine();

            /*
             * With the request method run the correct code
             */
            if (headerLine.startsWith("GET")) {
                String path = headerLine.split(" ")[1].substring(1);
                Page page;
                /*
                 * Check if the current path is a acceptable route and load the specified page otherwise load the
                 * error page
                 */
                if (routes.contains(path)) {
                    page = loader.stream().filter(reqPage -> reqPage.get().getPath().equals(path)).collect(Collectors.toList()).get(0).get();
                } else {
                    page = loader.stream().filter(reqPage -> reqPage.get().getPath().equals("error")).collect(Collectors.toList()).get(0).get();
                }
                page.load(socket);

            } else if (headerLine.startsWith("POST")) {
                // handle the post requests
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getRoutes() {
        List<String> routes = new ArrayList<>();
        loader.forEach(page -> routes.add(page.getPath()));
        return routes;
    }
}
