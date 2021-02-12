package se.iths.groupmembers.router.get.html;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class Person implements Page {
    private final String path;

    public Person() {
        this.path = "person";
    }

    @Override
    public void doGet(Socket socket, boolean head) {
        LoadHandler.load(socket, path, Status.OK, head);
    }

    @Override
    public void doPost(Socket socket, String body, boolean head) {
        doGet(socket, head);
    }

    @Override
    public String getPath() {
        return path;
    }
}
