package se.iths.groupmembers.router.get.html;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class Person implements Page {
    private final String path;

    public Person() {
        this.path = "person";
    }

    @Override
    public void load(Socket socket) {
        LoadHandler.load(socket, path, "text/html");
    }

    @Override
    public String getPath() {
        return path;
    }
}
