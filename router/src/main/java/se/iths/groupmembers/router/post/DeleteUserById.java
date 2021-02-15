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

@Path(path = "deleteuserbyid")
public class DeleteUserById implements Page {

    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        doPost(socket, "", head, printStream, gson, dao);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        Map<String, String> map = gson.fromJson(body, Map.class);
        User u = dao.removeById(Integer.parseInt(map.get("id")));
        byte[] output;
        if(u == null) {
            output = "{\n\"MassiveFail\":\"nope\"\n}".getBytes();
        }else {
            output = gson.toJson(u, User.class).getBytes();
        }


        LoadHandler.print(printStream, output, Status.OK, "application/json", head);
    }
}