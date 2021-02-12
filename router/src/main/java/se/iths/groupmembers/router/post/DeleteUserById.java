package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.JPA;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

public class DeleteUserById implements Page {

    private final String path;

    public DeleteUserById(){
        path="deleteuserbyid";
    }

    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        doPost(socket, "", head, printStream, gson, dao);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        Map<String, String> map = gson.fromJson(body, Map.class);
        dao.removeById(Integer.parseInt(map.get("id")));
        byte[] output = "{\n\"success\":\"ok\"\n}".getBytes();

        LoadHandler.print(printStream, output, Status.OK, "application/json", head);
    }

    @Override
    public String getPath() {
        return path;
    }
}