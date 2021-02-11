package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.UserDAOWithJPAImpl;
import se.iths.groupmembers.spi.Page;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

public class DeleteUserByLastName implements Page {

    private final String path;

    public DeleteUserByLastName(){
        path="deleteuserbylastname";
    }

    @Override
    public void load(Socket socket) {
        load(socket, "");
    }

    @Override
    public void load(Socket socket, String body) {
        UserDAOWithJPAImpl dao = new UserDAOWithJPAImpl();
        Map<String, String> map = new Gson().fromJson(body, Map.class);
        dao.removeByLastName((map.get("lastName")));
        try {
            String statusString = "{\n\"success\":\"ok\"\n}";
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            printStream.println("HTTP/1.1 200 OK");
            printStream.println("Content-Length: " + (statusString.length()));
            printStream.println("Content-Type: application/json");
            printStream.println();
            printStream.println(statusString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPath() {
        return path;
    }
}