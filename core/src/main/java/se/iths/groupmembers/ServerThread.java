package se.iths.groupmembers;

import se.iths.groupmembers.spi.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
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
             *  path
             */
            // TODO: Retrieve the body of the incoming request and store for later (POST requests)
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // This will throw an exception after some time in the console
            // Don't know how to fix
            String[] header = input.readLine().split(" ");

            /*
             * With the request method run the correct code
             */
            RequestMethod requestMethod = RequestMethod.valueOf(header[0]);
            switch (requestMethod) {
                case GET:
                    get(header[1]);
                    break;
                case POST:
                    post();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String path = fullPath.substring(1, fullPath.indexOf("?"));
        System.out.println(path);
        if (pageList.contains(path)) {
            pages.stream().filter(reqPage -> reqPage.get().getPath().equals(path)).collect(Collectors.toList()).get(0).get().load(socket);
            return;
        }
        /*
         * We need to create another class in the SPI module for objects that aren't pages per say.
         * E.g. we can return database data as a GET method.
         */
        List<String> queries = new ArrayList<>();
//        if (fullPathArr.length > 1) {
//            queries = Arrays.stream(fullPathArr[1].split("\\?")).collect(Collectors.toList());
//        }

        // This is supposed to stay at the very bottom as a way to catch anything slipping through when nothing matches
        // so it will fallback to the error page.
        // Until we figure out how to properly setup a fallback error page this will have to do.
        pages.stream().filter(reqPage -> reqPage.get().getPath().equals("error")).collect(Collectors.toList()).get(0).get().load(socket);
    }

    /*
     * Method for handling all incoming POST requests
     */
    // TODO: Figure out a way to retrieve the body of a POST request
    public void post() {
        // Do stuff here
    }

}
