package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.UserDAOWithJPAImpl;
import se.iths.groupmembers.spi.Page;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

public class DeleteUserByFirstName implements Page {

    private final String path;

    public DeleteUserByFirstName(){
        path="deleteuserbyfirstname";
    }

    @Override
    public void load(Socket socket) {
        load(socket, "");
    }

    @Override
    public void load(Socket socket, String body) {
        UserDAOWithJPAImpl dao = new UserDAOWithJPAImpl();
        Map<String, String> map = new Gson().fromJson(body, Map.class);
        dao.removeByFirstName(map.get("firstName"));

        try {
            String whatEver = "{success:  ok}";
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            printStream.println("HTTP/1.1 200 OK");
            printStream.println("Content-Length: " + (whatEver.length()));
            printStream.println("Content-Type: application/json");
            printStream.println();
            printStream.println(whatEver);
        }
        catch (IOException ignored){}
    }



    /*UserDAOWithJPAImpl dao = new UserDAOWithJPAImpl();
    Map<String, String> map = new HashMap<>();
    body = body.replaceAll("\\{", "").replaceAll(",", "").replaceAll("\"", "").replaceAll("}", "").replaceAll("\t", "").replaceAll(" ", "");
    String[] arr = body.split("\\W");
        System.out.println(arr.length);
        if (arr.length != 5) {
        return;
    }
        System.out.println("yay");
        map.put(arr[1], arr[2]);
        map.put(arr[3], arr[4]);
        dao.add(map.get("firstName"), map.get("lastName"));
        */

    @Override
    public String getPath() {
        return path;
    }
}
