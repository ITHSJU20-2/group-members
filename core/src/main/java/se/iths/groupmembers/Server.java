package se.iths.groupmembers;

import com.google.gson.Gson;
import se.iths.db.JPA;
import se.iths.groupmembers.spi.Page;
import se.iths.groupmembers.spi.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server {

    private ServerSocket serverSocket;
    private int port = 8080;
    private Gson gson;
    private JPA jpa;

    private final ServiceLoader<Page> pages;
    private final List<String> pageList;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public Server() {
        this.pages = ServiceLoader.load(Page.class);
        this.pageList = getPageList();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening on http://localhost:" + port);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> handleConnection(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleConnection(Socket socket) {
        try {
            /*
             * Creates a BufferedReader and reads the first header line so that we can get the request method and the
             * path
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            String headerLine = bufferedReader.readLine();
            if (headerLine == null) {
                return;
            }

            String[] header = headerLine.split(" ");

            /*
             * With the request method run the correct code
             */
            RequestMethod requestMethod = RequestMethod.valueOf(header[0]);
            switch (requestMethod) {
                case GET:
                    get(socket, header[1], false, printStream);
                    break;
                case POST:
                    String req = readBody(bufferedReader);
                    post(socket, header[1], req, printStream);
                    break;
                case HEAD:
                    get(socket, header[1], true, printStream);
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public JPA getJpa() {
        return jpa;
    }

    public void setJpa(JPA jpa) {
        this.jpa = jpa;
    }

    private String readBody(BufferedReader bufferedReader) throws IOException {
        boolean check = false;
        char[] charArr = new char[1024];
        while (true) {
            if (check) {
                bufferedReader.read(charArr);
                break;
            }
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith("Content-Length:")) {
                charArr = new char[Integer.parseInt(line.split(" ")[1])];
            }
            if (line.isEmpty()) {
                check = true;
            }
        }
        StringBuilder req = new StringBuilder();
        for (char c : charArr) {
            req.append(c);
        }
        return req.toString();
    }

    private List<String> getPageList() {
        List<String> routes = new ArrayList<>();
        pages.forEach(page -> routes.add(page.getClass().getAnnotation(Path.class).path()));
        return routes;
    }

    /*
     * Method for handling all incoming GET requests
     */
    public void get(Socket socket, String fullPath, boolean head, PrintStream printStream) {
        String[] path = fullPath.split("\\?", 2);
        String page = path[0].substring(1);
        if (pageList.contains(page)) {
            pages.stream().filter(reqPage -> reqPage.get().getClass().getAnnotation(Path.class).path().equals(page)).collect(Collectors.toList()).get(0).get().doGet(socket, head, printStream, gson, jpa);
            return;
        }

        // This is supposed to stay at the very bottom as a way to catch anything slipping through when nothing matches
        // so it will fallback to the error page.
        // Until we figure out how to properly setup a fallback error page this will have to do.
        pages.stream().filter(reqPage -> reqPage.get().getClass().getAnnotation(Path.class).path().equals("error")).collect(Collectors.toList()).get(0).get().doGet(socket, head, printStream, gson, jpa);
    }

    /*
     * Method for handling all incoming POST requests
     */
    public void post(Socket socket, String fullPath, String body, PrintStream printStream) {
        if (!body.startsWith("{") && !body.endsWith("}")) {
            body = queryStringToJson(body);
        }
        String[] path = fullPath.split("\\?", 2);
        String page = path[0].substring(1);
        if (pageList.contains(page)) {
            pages.stream().filter(reqPage -> reqPage.get().getClass().getAnnotation(Path.class).path().equals(page)).collect(Collectors.toList()).get(0).get().doPost(socket, body, false, printStream, gson, jpa);
            return;
        }

        // This is supposed to stay at the very bottom as a way to catch anything slipping through when nothing matches
        // so it will fallback to the error page.
        // Until we figure out how to properly setup a fallback error page this will have to do.
        pages.stream().filter(reqPage -> reqPage.get().getClass().getAnnotation(Path.class).path().equals("error")).collect(Collectors.toList()).get(0).get().doGet(socket, false, printStream, gson, jpa);
    }

    private String queryStringToJson(String query) {
        query = query.replaceAll("=", "\":\"").replaceAll("&", "\",\"");
        return "{\"" + query + "\"}";
    }
}
