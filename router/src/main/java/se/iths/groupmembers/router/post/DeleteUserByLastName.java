package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.JPA;
import se.iths.db.User;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.spi.Page;
import se.iths.groupmembers.spi.Path;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

@Path(path = "deleteuserbylastname")
public class DeleteUserByLastName implements Page {

    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        doPost(socket, "", head, printStream, gson, dao);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        Map<String, String> map = gson.fromJson(body, Map.class);
        User u = dao.removeByLastName((map.get("lastName")));
        LoadHandler.printJson(u, gson, printStream, head);
    }
}