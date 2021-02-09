package se.iths.groupmembers.router.get.css;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class NormalizeCSS implements Page {
    private final String path;

    public NormalizeCSS() {
        path = "css/normalize.css";
    }

    @Override
    public void load(Socket socket) {
        LoadHandler.load(socket, path, Status.OK);
    }

    @Override
    public String getPath() {
        return path;
    }
}
