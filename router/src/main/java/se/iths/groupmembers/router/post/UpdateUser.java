package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.UserDAOWithJPAImpl;
import se.iths.groupmembers.spi.Page;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

public class UpdateUser implements Page {

    private final String path;

    public UpdateUser() {
        path = "updateuser";
    }

    @Override
    public void doGet(Socket socket, boolean head, Gson gson, JPA dao) {
        doPost(socket, "", head);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head) {
        UserDAOWithJPAImpl dao = new UserDAOWithJPAImpl();
        Map<String, String> map = new Gson().fromJson(body, Map.class);
        dao.updateByFirstLast(
                map.get("firstName"),
                map.get("lastName"),
                map.get("newFirstName"),
                map.get("newLastName")
        );
        try {
            String statusString = "{\n\"success\":\"ok\"\n}";
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            printStream.println("HTTP/1.1 200 OK");
            printStream.println("Content-Type: application/json");
            printStream.println("Content-Length: " + (statusString.length()));
            if (!head) {
                printStream.println();
                printStream.println(statusString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPath() {
        return path;
    }

}
