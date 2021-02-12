package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.JPA;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;


import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

public class GetUserByFirstName implements Page {

    private final String path;

    public GetUserByFirstName() {
        path = "getuserbyfirstname";
    }


    @Override
    public void doGet(Socket socket, boolean head, PrintStream printStream, Gson gson, JPA dao) {

    }

    @Override
    public void doPost(Socket socket, String body, boolean head, PrintStream printStream, Gson gson, JPA dao) {
        Map<String, String> map = new Gson().fromJson(body,Map.class);
        dao.getByFirstName(map.get("firstName"));
        byte[] output = "{\n\"success\":\"ok\"\n}".getBytes();

        LoadHandler.print(printStream, output, Status.OK, "application/json", head);


    }

    @Override
    public String getPath() {
        return path;
    }
}

