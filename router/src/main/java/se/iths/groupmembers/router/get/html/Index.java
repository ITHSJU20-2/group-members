package se.iths.groupmembers.router.get.html;

import se.iths.groupmembers.router.ContentType;
import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class Index implements Page {
    private final String path;

    public Index() {
        path = "";
    }

    @Override
    public void load(Socket socket) {
        LoadHandler.load(socket, path, ContentType.HTML, Status.OK);
    }

    @Override
    public String getPath() {
        return path;
    }
}
