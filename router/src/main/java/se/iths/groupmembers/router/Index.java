package se.iths.groupmembers.router;

import se.iths.groupmembers.spi.Page;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Index implements Page {
    private final String path;

    public Index() {
        path = "";
    }

    @Override
    public void load(Socket socket) {
        File file = new File(new File("router/src/main/resources/static/index.html").getAbsoluteFile().toString());
        LoadHandler.load(socket, file, "text/html");
    }

    @Override
    public String getPath() {
        return path;
    }
}
