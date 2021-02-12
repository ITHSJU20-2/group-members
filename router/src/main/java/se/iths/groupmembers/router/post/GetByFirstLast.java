package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.JPA;
import se.iths.db.User;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;
import se.iths.groupmembers.spi.Path;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

@Path(path = "getbyfirstlast")
public class GetByFirstLast implements Page {

    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        doPost(socket, "", head, printStream, gson, dao);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        System.out.println(body);
        Map<String, String> map = gson.fromJson(body, Map.class);
        User user = dao.getByFirstLast(map.get("firstName"), map.get("lastName"));

        byte[] output = gson.toJson(user).getBytes();

        LoadHandler.print(printStream, output, Status.OK, "application/json", head);
    }
}
