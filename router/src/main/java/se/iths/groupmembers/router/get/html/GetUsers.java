package se.iths.groupmembers.router.get.html;

import com.google.gson.Gson;
import se.iths.db.JPA;
import se.iths.db.User;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;
import se.iths.groupmembers.spi.Path;

import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Path(path = "getusers")
public class GetUsers implements Page {


    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        StringBuilder json = new StringBuilder("[");

        List<User> userList = dao.getAll();

        for (User user : userList) {
            json.append(gson.toJson(user)).append(",");
        }
        json.deleteCharAt(json.lastIndexOf(",")).append("]");

        //CODE TO START DEFEATING THE MALEVOLENT 3...
        byte[] output = json.toString().getBytes(StandardCharsets.UTF_8);

        LoadHandler.print(printStream, output, Status.OK, "application/json", head);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        doGet(socket, head, printStream, gson, dao);
    }
}
