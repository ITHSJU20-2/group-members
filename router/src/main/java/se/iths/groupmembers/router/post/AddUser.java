package se.iths.groupmembers.router.post;

import se.iths.db.UserDAOWithJPAImpl;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;
import java.util.HashMap;
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
        Map<String, String> map = new HashMap<>();
        body = body.replaceAll("\\{", "").replaceAll(",", "").replaceAll("\"", "").replaceAll("}", "").replaceAll("\t", "").replaceAll(" ", "");
        String[] arr = body.split("\\W");
        System.out.println(arr.length);
        if (arr.length != 5) {
            return;
        }
        System.out.println("yay");
        map.put(arr[1], arr[2]);
        map.put(arr[3], arr[4]);
        dao.add(map.get("firstName"), map.get("lastName"));
    }

    @Override
    public String getPath() {
        return path;
    }
}
