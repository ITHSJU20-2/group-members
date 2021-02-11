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
    public void load(Socket socket) {
        LoadHandler.load(socket, path, Status.OK);
    }

    @Override
    public void load(Socket socket, String body) {

    }

    @Override
    public String getPath() {
        return path;
    }
}
