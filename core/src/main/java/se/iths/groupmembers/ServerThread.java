package se.iths.groupmembers;

import se.iths.groupmembers.spi.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ServerThread extends Thread {

    private final Socket socket;

    private final ServiceLoader<Page> pages;
    private final List<String> pageList;

    public ServerThread(Socket socket) {
        this.socket = socket;

        this.pages = ServiceLoader.load(Page.class);
        this.pageList = getPageList();
    }

    @Override
    public void run() {
        try {
            /*
             * Creates a BufferedReader and reads the first header line so that we can get the request method and the
             * path
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                    get(header[1]);
                    break;
                case POST:
                    String req = readBody(bufferedReader);
                    post(header[1], req);
                    break;
                case HEAD:
                    head();
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        pages.forEach(page -> routes.add(page.getPath()));
        return routes;
    }

    /*
     * Method for handling all incoming GET requests
     */
    public void get(String fullPath) {
        String[] path = fullPath.split("\\?", 2);
        String page = path[0].substring(1);
        if (pageList.contains(page)) {
            pages.stream().filter(reqPage -> reqPage.get().getPath().equals(page)).collect(Collectors.toList()).get(0).get().load(socket);
            return;
        }

        /*
         * We need to create another class in the SPI module for objects that aren't pages per say.
         * E.g. we can return database data as a GET method.
         */
        List<String> queries = new ArrayList<>();
        if (path.length > 1) {
            queries = Arrays.asList(path[1].split("&"));
        }

        // This is supposed to stay at the very bottom as a way to catch anything slipping through when nothing matches
        // so it will fallback to the error page.
        // Until we figure out how to properly setup a fallback error page this will have to do.
        pages.stream().filter(reqPage -> reqPage.get().getPath().equals("error")).collect(Collectors.toList()).get(0).get().load(socket);
    }

    /*
     * Method for handling all incoming POST requests
     */
    public void post(String fullPath, String body) {
        String[] path = fullPath.split("\\?", 2);
        String page = path[0].substring(1);
        if (pageList.contains(page)) {
            pages.stream().filter(reqPage -> reqPage.get().getPath().equals(page)).collect(Collectors.toList()).get(0).get().load(socket, body);
            return;
        }

        // This is supposed to stay at the very bottom as a way to catch anything slipping through when nothing matches
        // so it will fallback to the error page.
        // Until we figure out how to properly setup a fallback error page this will have to do.
        pages.stream().filter(reqPage -> reqPage.get().getPath().equals("error")).collect(Collectors.toList()).get(0).get().load(socket);
    }

    /*
     * Method for handling all incoming HEAD requests
     */
    private void head() {
    }

}
