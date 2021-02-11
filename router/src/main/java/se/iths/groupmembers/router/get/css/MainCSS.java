package se.iths.groupmembers.router.get.css;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class MainCSS implements Page {
    private final String path;

    public MainCSS() {
        path = "css/main.css";
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
