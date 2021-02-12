package se.iths.groupmembers.router.get.html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.iths.db.User;
import se.iths.db.UserDAOWithJPAImpl;
import se.iths.groupmembers.spi.Page;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

// TODO: Custom path annotation
public class GetUsers implements Page {

    private final String path;

    public GetUsers() {
        path = "getusers";
    }

    @Override
    public void doGet(Socket socket, boolean head) {
        try {

            // TODO: Refactor json to bytearray
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            StringBuilder json = new StringBuilder("[");

            UserDAOWithJPAImpl dao = new UserDAOWithJPAImpl();
            List<User> userList = dao.getAll();




            for (User user : userList) {
                json.append(gson.toJson(user)).append(",");
            }
            json.deleteCharAt(json.lastIndexOf(",")).append("]");

            //CODE TO START DEFEATING THE MALEVOLENT 3...
            byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);
            String contents = new String(bytes, StandardCharsets.UTF_8);


            PrintStream printStream = new PrintStream(socket.getOutputStream());

            printStream.println("HTTP/1.1 200 OK");
            printStream.println("Content-Type: application/json");
            printStream.println("Content-Length: " + bytes.length);
            if (!head) {
                printStream.println();
                printStream.println(contents);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Socket socket, String body, boolean head) {
        doGet(socket, head);
    }

    @Override
    public String getPath() {
        return path;
    }
}
