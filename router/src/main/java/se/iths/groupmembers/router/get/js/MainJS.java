package se.iths.groupmembers.router.get.js;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class MainJS implements Page {

    private final String path;

    public MainJS() {
        path = "js/main.js";
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
