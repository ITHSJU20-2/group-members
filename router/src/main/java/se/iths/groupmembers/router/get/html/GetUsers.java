package se.iths.groupmembers.router.get.html;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.iths.db.User;
import se.iths.db.UserDAOWithJPAImpl;
import se.iths.groupmembers.spi.Page;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class GetUsers implements Page {

    private final String path;

    public GetUsers() {
        path = "getusers";
    }

    @Override
    public void load(Socket socket, boolean head) {
        try {

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

            int contentLength = json.toString().length();

            /*
             * You might need to uncomment the line below for this route to work properly (dunno why) ¯\_(ツ)_/¯
             */
//             contentLength += 3;

            PrintStream printStream = new PrintStream(socket.getOutputStream());

            printStream.println("HTTP/1.1 200 OK");
            printStream.println("Content-Type: application/json");
            printStream.println("Content-Length: " + contentLength);
            if (!head) {
                printStream.println();
                printStream.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(Socket socket, String body, boolean head) {
        load(socket, head);
    }

    @Override
    public String getPath() {
        return path;
    }
}
