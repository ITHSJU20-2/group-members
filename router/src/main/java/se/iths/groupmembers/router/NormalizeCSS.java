package se.iths.groupmembers.router;

import se.iths.groupmembers.spi.Page;

import java.io.File;
import java.net.Socket;

public class NormalizeCSS implements Page {
    private final String path;

    public NormalizeCSS() {
        path = "css/normalize.css";
    }

    @Override
    public void load(Socket socket) {
        File file = new File(new File("router/src/main/resources/static/normalize.css").getAbsoluteFile().toString());
        LoadHandler.load(socket, file, "text/css");
    }

    @Override
    public String getPath() {
        return path;
    }
}
