package se.iths.groupmembers.router.get.img;

import se.iths.groupmembers.router.LoadHandler;
import se.iths.groupmembers.router.Status;
import se.iths.groupmembers.spi.Page;

import java.net.Socket;

public class CatIMG implements Page {

    private final String path;

    public CatIMG() {
        path = "img/cat.png";
    }

    @Override
    public void load(Socket socket, boolean head) {
        LoadHandler.load(socket, path, Status.OK, head);
    }

    @Override
    public void load(Socket socket, String body, boolean head) {
        load(socket, head);
    }

    @Override
    public String getPath() {
        return path;
    }
}
