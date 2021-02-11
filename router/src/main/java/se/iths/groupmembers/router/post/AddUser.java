package se.iths.groupmembers.router.post;

import com.google.gson.Gson;
import se.iths.db.UserDAOWithJPAImpl;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;
import java.util.Map;

public class AddUser implements Page {

    private final String path;

    public AddUser() {
        path = "adduser";
    }

    @Override
    public void load(Socket socket) {
        load(socket, "");
    }

    @Override
    public void load(Socket socket, String body) {
        UserDAOWithJPAImpl dao = new UserDAOWithJPAImpl();
        Map<String, String> map = new Gson().fromJson(body, Map.class);
        dao.add(map.get("firstName"), map.get("lastName"));
    }

    @Override
    public String getPath() {
        return path;
    }
}
