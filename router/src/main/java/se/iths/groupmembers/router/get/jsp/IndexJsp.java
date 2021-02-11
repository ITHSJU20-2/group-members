package se.iths.groupmembers.router.get.jsp;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class IndexJsp implements Page {
    private final String path;

    public IndexJsp() {
        path = "jsp/index.jsp";
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

