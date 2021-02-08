package se.iths.groupmembers.router.get.html;

import se.iths.groupmembers.router.ContentType;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class Error implements Page {

    private final String path;

    public Error() {
        path = "error";
    }
    @Override
    public void load(Socket socket) {
        LoadHandler.load(socket, path, ContentType.HTML, Status.NOT_FOUND);
    }

    @Override
    public String getPath() {
        return path;
    }
}
